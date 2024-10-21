package in.ai.myntra.controller;

import in.ai.myntra.model.Product;
import in.ai.myntra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public @ResponseBody List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/import-products")
    public String importProducts(@RequestParam String filePath) {
        productService.importProductsFromCSV(filePath);
        return "Products imported successfully!";
    }
}
