package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.CrossOrigin;

// we have to provide this cross origin explicitly, otherwise it will not work ,
// because angular runs on different port and springboot application runs on different port
@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel="productCategory",path="product-category")
// 1st one -> Name of JSON entry, 2nd one -> /product-category
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
