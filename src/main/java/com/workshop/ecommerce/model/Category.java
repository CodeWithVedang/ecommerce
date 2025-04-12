package com.workshop.ecommerce.model; 
 
import jakarta.persistence.*; 
import jakarta.validation.constraints.NotBlank; 
import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 
 
import java.time.LocalDateTime; 
import java.util.ArrayList; 
import java.util.List; 
 
@Entity 
@Table(name = "tbl_categories") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Category { 
     
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
     
    @NotBlank(message = "Category name is required") 
    private String name; 
     
    private String description; 
     
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = 
true) 
    private List<Product> products = new ArrayList<>(); 
     
    private LocalDateTime createdAt; 
     
    private LocalDateTime updatedAt; 
     
    @PrePersist 
    protected void onCreate() { 
        this.createdAt = LocalDateTime.now(); 
        this.updatedAt = LocalDateTime.now(); 
    } 
     
    @PreUpdate 
    protected void onUpdate() { 
        this.updatedAt = LocalDateTime.now(); 
    } 
}