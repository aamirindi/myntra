package in.ai.myntra.controller;

import in.ai.myntra.model.Bag;
import in.ai.myntra.model.Product;
import in.ai.myntra.service.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bag")
public class BagController {

    @Autowired
    private BagService bagService;

    @PostMapping("/add")
    public Bag addProductToBag(@RequestParam Long userId, @RequestParam Long productId) {
        return bagService.addProductToBag(userId, productId);
    }

    @GetMapping("/user/{userId}")
    public List<Product> getProductsInBag(@PathVariable Long userId) {
        return bagService.getProductsInBag(userId);
    }

    @DeleteMapping("/remove")
    public void removeProductFromBag(@RequestParam Long userId, @RequestParam Long productId) {
        bagService.removeProductFromBag(userId, productId);
    }

    @PostMapping("/move-from-wishlist")
    public Bag moveProductFromWishlistToBag(@RequestParam Long userId, @RequestParam Long productId) {
        bagService.moveProductFromWishlistToBag(userId, productId);
        return bagService.addProductToBag(userId, productId);
    }

    @GetMapping("/calculate-total")
    public double calculateTotalPrice(@RequestParam List<Long> productIds) {
        return bagService.calculateTotalPrice(productIds);
    }
}
