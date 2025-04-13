package com.workshop.ecommerce.config;

import com.workshop.ecommerce.model.Category;
import com.workshop.ecommerce.model.Product;
import com.workshop.ecommerce.repository.CategoryRepository;
import com.workshop.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Profile("dev") // Only runs in dev profile
public class DataInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Create categories
            Category electronics = new Category();
            electronics.setName("Electronics");
            electronics.setDescription("Electronic devices and gadgets");
            
            Category clothing = new Category();
            clothing.setName("Clothing");
            clothing.setDescription("Fashion items and apparel");
            
            Category books = new Category();
            books.setName("Books");
            books.setDescription("Books, ebooks and audiobooks");
            
            // Save categories
            List<Category> categories = categoryRepository.saveAll(List.of(electronics, clothing, books));
            
            // Create products
            Product laptop = new Product();
            laptop.setName("MacBook Pro");
            laptop.setDescription("Latest MacBook Pro with M2 chip");
            laptop.setPrice(new BigDecimal("1299.99"));
            laptop.setStockQuantity(50);
            laptop.setImageUrl("https://example.com/macbook.jpg");
            laptop.setCategory(electronics);
            
            Product smartphone = new Product();
            smartphone.setName("iPhone 15");
            smartphone.setDescription("Latest iPhone with advanced features");
            smartphone.setPrice(new BigDecimal("999.99"));
            smartphone.setStockQuantity(100);
            smartphone.setImageUrl("https://example.com/iphone.jpg");
            smartphone.setCategory(electronics);
            
            Product tshirt = new Product();
            tshirt.setName("Cotton T-Shirt");
            tshirt.setDescription("Comfortable cotton t-shirt");
            tshirt.setPrice(new BigDecimal("19.99"));
            tshirt.setStockQuantity(200);
            tshirt.setImageUrl("https://example.com/tshirt.jpg");
            tshirt.setCategory(clothing);
            
            Product jeans = new Product();
            jeans.setName("Slim Fit Jeans");
            jeans.setDescription("Modern slim fit jeans");
            jeans.setPrice(new BigDecimal("49.99"));
            jeans.setStockQuantity(150);
            jeans.setImageUrl("https://example.com/jeans.jpg");
            jeans.setCategory(clothing);
            
            Product novel = new Product();
            novel.setName("The Great Novel");
            novel.setDescription("Bestselling fiction novel");
            novel.setPrice(new BigDecimal("12.99"));
            novel.setStockQuantity(300);
            novel.setImageUrl("https://example.com/novel.jpg");
            novel.setCategory(books);
            
            // Save products
            productRepository.saveAll(List.of(laptop, smartphone, tshirt, jeans, novel));
            
            System.out.println("Data initialization completed!");
        };
    }
}

