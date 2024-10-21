package in.ai.myntra.service;

import in.ai.myntra.model.Product;
import in.ai.myntra.model.User;
import in.ai.myntra.model.WishList;
import in.ai.myntra.repo.ProductRepository;
import in.ai.myntra.repo.UserRepository;
import in.ai.myntra.repo.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public WishList addProductToWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));

        // Find the first wishlist of the user or create a new one
        Optional<WishList> optionalWishList = wishListRepository.findByUserUserId(userId).stream().findFirst();
        WishList wishList = optionalWishList.orElse(new WishList());

        wishList.setUser(user);
        wishList.getProducts().add(product);

        return wishListRepository.save(wishList);
    }

    public Optional<WishList> getWishlistByUser(Long userId) {
        return wishListRepository.findByUserUserId(userId);
    }

    public void removeProductFromWishlist(Long wishListId, Long productId) {
        WishList wishList = wishListRepository.findById(wishListId).orElseThrow(() -> new RuntimeException("Wishlist not found"));
        wishList.getProducts().removeIf(product -> product.getId().equals(productId));
        wishListRepository.save(wishList);
    }

}
