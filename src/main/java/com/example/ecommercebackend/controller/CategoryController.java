package com.example.ecommercebackend.controller;
import com.example.ecommercebackend.dto.CategoryDto;
import com.example.ecommercebackend.model.Category;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@Tag(name = "Category")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public GetRequestResponse<Category> listCategory(){
        return categoryService.listCategory();
    }

    @PostMapping("/create")
    public CommonResponse categoryCreate(@RequestBody CategoryDto categoryDto){
        return categoryService.categoryCreate(categoryDto);
    }
}
