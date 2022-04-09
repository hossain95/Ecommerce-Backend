package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.CategoryDto;
import com.example.ecommercebackend.dto.dtoConverter.DtoConverter;
import com.example.ecommercebackend.model.Category;
import com.example.ecommercebackend.model.UserModel;
import com.example.ecommercebackend.repository.CategoryRepository;
import com.example.ecommercebackend.repository.UserRepository;
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
    private UserRepository userRepository;

    public GetRequestResponse<Category> listCategory(){
        return new GetRequestResponse<>(ResponseStatus.succeed, categoryRepository.findAll());
    }

    public ResponseEntity<CommonResponse> categoryCreate(CategoryDto categoryDto){
        Long userId = categoryDto.getUserId();
        Category category = new DtoConverter().categoryDtoToCategory(categoryDto);

        UserModel userModel = userRepository.findById(userId).get();
//        System.out.println("user status: "+ userModel.getUserStatus());
        if (Objects.isNull(userModel)){
            return new ResponseEntity<>(new CommonResponse("account does not exist", ResponseStatus.failed), HttpStatus.FORBIDDEN);
        }
        if (userModel.getEnabled() == false){
            return new ResponseEntity<>(new CommonResponse("your account does not activate yet!", ResponseStatus.failed), HttpStatus.FORBIDDEN);
        }
        if (userModel.getUserStatus().equals("buyer")){
            return new ResponseEntity<>(new CommonResponse("buyer can not add category", ResponseStatus.failed), HttpStatus.FORBIDDEN);
        }

        category.setCategoryName(category.getCategoryName().toLowerCase());

        Category cat = categoryRepository.findCategoryByCategoryName(category.getCategoryName());
        if (Objects.nonNull(cat)){
            return new ResponseEntity<>(new CommonResponse(category.getCategoryName()+ " is already exist!", ResponseStatus.failed), HttpStatus.BAD_REQUEST);
        }
//        categoryRepository.save(category);
        System.out.println("Success : "+ category.getCategoryName());
        categoryRepository.save(category);

        return new ResponseEntity<>(new CommonResponse("category is created", ResponseStatus.succeed), HttpStatus.CREATED);
    }
}
