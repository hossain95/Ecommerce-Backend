package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query("select c from Category c where c.categoryName =?1")
    public Category findCategoryByCategoryName(String categoryName);
}
