package in.ai.myntra.service;

import in.ai.myntra.model.Bag;
import in.ai.myntra.model.Product;
import in.ai.myntra.model.WishList;
import in.ai.myntra.repo.BagRepository;
import in.ai.myntra.repo.ProductRepository;
import in.ai.myntra.repo.UserRepository;
import in.ai.myntra.repo.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BagService {

    @Autowired
    private BagRepository bagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishListRepository wishListRepository;

    public Bag addProductToBag(Long userId, Long productId) {
        System.out.println("Adding product to bag: userId=" + userId + ", productId=" + productId);

        Bag bag = bagRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    System.out.println("Creating new bag for userId=" + userId);
                    return new Bag();
                });
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!bag.getProducts().contains(product)) {
            bag.getProducts().add(product);
        }

        bag.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));

        Bag savedBag = bagRepository.save(bag);
        System.out.println("Bag saved: " + savedBag);
        return savedBag;
    }


    public List<Product> getProductsInBag(Long userId) {
        return bagRepository.findByUser_UserId(userId)  // Updated method call
                .orElseThrow(() -> new RuntimeException("Bag not found"))
                .getProducts();
    }

    public void removeProductFromBag(Long userId, Long productId) {
        Bag bag = bagRepository.findByUser_UserId(userId)  // Updated method call
                .orElseThrow(() -> new RuntimeException("Bag not found"));

        bag.getProducts().removeIf(product -> product.getId().equals(productId));

        bagRepository.save(bag);
    }

    public double calculateTotalPrice(List<Long> productIds) {
        return productRepository.findAllById(productIds).stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public void moveProductFromWishlistToBag(Long userId, Long productId) {
        // Step 1: Remove product from the user's wishlist
        WishList wishList = wishListRepository.findByUserUserId(userId)  // Updated method call
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (wishList.getProducts().contains(product)) {
            wishList.getProducts().remove(product);
            wishListRepository.save(wishList);
        }

        // Step 2: Add product to the user's bag
        addProductToBag(userId, productId);
    }
}
