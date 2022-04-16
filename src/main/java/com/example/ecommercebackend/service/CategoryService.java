package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.CategoryDto;
import com.example.ecommercebackend.dto.dtoConverter.DtoConverter;
import com.example.ecommercebackend.model.Category;
import com.example.ecommercebackend.model.Seller;
import com.example.ecommercebackend.repository.CategoryRepository;
import com.example.ecommercebackend.repository.SellerRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;

    public GetRequestResponse<Category> listCategory(){
        return new GetRequestResponse<>(ResponseStatus.succeed, categoryRepository.findAll());
    }

    public CommonResponse categoryCreate(CategoryDto categoryDto){
        String email = categoryDto.getSellerEmail();
        Category category = new DtoConverter().categoryDtoToCategory(categoryDto);

        Seller seller = sellerRepository.findSellerByEmail(email);
//        System.out.println("user status: "+ userModel.getUserStatus());
        if (Objects.isNull(seller)){
            return new CommonResponse("account does not exist", HttpStatus.BAD_REQUEST);
        }
        if (seller.getEnabled() == false){
            return new CommonResponse("your account does not activate yet!", HttpStatus.FORBIDDEN);
        }
//        if (seller.getUserStatus().equals("buyer")){
//            return new ResponseEntity<>(new CommonResponse("buyer can not add category", ResponseStatus.failed), HttpStatus.FORBIDDEN);
//        }

        category.setCategoryName(category.getCategoryName().toLowerCase());

        Category cat = categoryRepository.findCategoryByCategoryName(category.getCategoryName());
        if (Objects.nonNull(cat)){
            return new CommonResponse(category.getCategoryName()+ " is already exist!", HttpStatus.BAD_REQUEST);
        }
//        categoryRepository.save(category);
        System.out.println("Success : "+ category.getCategoryName());
        categoryRepository.save(category);

        return new CommonResponse("category is created", HttpStatus.CREATED);
    }
}
