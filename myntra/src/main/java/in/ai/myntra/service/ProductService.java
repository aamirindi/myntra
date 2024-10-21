package in.ai.myntra.service;

import in.ai.myntra.model.Product;
import in.ai.myntra.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${import.csv.path}")
    private String csvFilePath;

    public void importProductsFromCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> records = reader.readAll();
            for (String[] record : records) {
                if (records.indexOf(record) == 0) {
                    // Skip the header row
                    continue;
                }

                Product product = new Product();
                product.setId(Long.parseLong(record[0]));
                product.setName(record[1]);
                product.setProducts(record[2]);
                product.setPrice(Double.parseDouble(record[3]));
                product.setColour(record[4]);
                product.setBrand(record[5]);
                product.setImg(record[6]);

                // Parsing ratingCount as Double
                product.setRatingCount(Double.parseDouble(record[7]));

                product.setAvgRating(Double.parseDouble(record[8]));
                product.setDescription(record[9]);
                product.setAttributes(record[10]);

                productRepository.save(product);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public long getProductCount() {
        return productRepository.count();
    }
}
