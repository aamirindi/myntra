package in.ai.myntra;

import in.ai.myntra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Value("${import.csv.path}")
    private String filePath;

    @Override
    public void run(String... args) throws Exception {
        // Check if products already exist in the database
        if (productService.getProductCount() == 0) {
            // Only import products if the database is empty
            productService.importProductsFromCSV(filePath);
        } else {
            System.out.println("Products already exist in the database. Skipping import.");
        }
    }
}
