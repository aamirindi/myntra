package in.ai.myntra.controller;

import in.ai.myntra.model.Product;
import in.ai.myntra.model.User;
import in.ai.myntra.model.WishList;
import in.ai.myntra.repo.ProductRepository;
import in.ai.myntra.repo.UserRepository;
import in.ai.myntra.repo.WishListRepository;
import in.ai.myntra.service.BagService;
import in.ai.myntra.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private BagService bagService;


    @PostMapping("/add")
    public WishList addProductToWishlist(@RequestParam Long userId, @RequestParam Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));

        // Find the first wishlist of the user or create a new one
        Optional<WishList> optionalWishList = wishListRepository.findByUserUserId(userId);
        WishList wishList = optionalWishList.orElse(new WishList());

        if (!wishList.getProducts().contains(product)) {
            wishList.getProducts().add(product);
        }

        wishList.setUser(user);
        return wishListRepository.save(wishList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<WishList> getWishlistByUser(@PathVariable Long userId) {
        Optional<WishList> wishList = wishListService.getWishlistByUser(userId);
        return wishList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/remove")
    public void removeProductFromWishlist(@RequestParam Long wishlistId, @RequestParam Long productId) {
        wishListService.removeProductFromWishlist(wishlistId, productId);
    }

    @PostMapping("/moveToBag")
    public ResponseEntity<String> moveProductToBag(@RequestParam Long userId, @RequestParam Long productId) {
        try {
            bagService.moveProductFromWishlistToBag(userId, productId);
            return ResponseEntity.ok("Product moved to bag successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to move product to bag: " + e.getMessage());
        }
    }
}
