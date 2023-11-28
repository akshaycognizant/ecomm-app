package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="product_category")
//@Data - here we will not use data instead of thaat will use getter and setter
@Getter
@Setter
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="category_name")
    private String categoryName;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL) // cascade used to update both entities upon saving any one
    private Set<Product> products;
}
