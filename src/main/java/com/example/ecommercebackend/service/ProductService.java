package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.ProductDto;
import com.example.ecommercebackend.dto.dtoConverter.DtoConverter;
import com.example.ecommercebackend.model.Category;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.UserModel;
import com.example.ecommercebackend.repository.CategoryRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.UserRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    public GetRequestResponse<Product> productList(){
        List<Product> products = productRepository.findAll();

        return new GetRequestResponse<>(ResponseStatus.succeed, products);
    }

    public ResponseEntity<CommonResponse> productCreate(ProductDto productDto){
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());

        if(category.isEmpty()){
            return new ResponseEntity<>(new CommonResponse("category does not found!", ResponseStatus.failed), HttpStatus.BAD_REQUEST);
        }
        UserModel userModel = userRepository.findById(productDto.getUserId()).get();
        if(userModel.getUserStatus().equals("buyer")){
            return new ResponseEntity<>(new CommonResponse("buyer does not add product", ResponseStatus.failed), HttpStatus.FORBIDDEN);
        }

        Product product = new DtoConverter().productDtoToProduct(productDto, category.get());

        try{
            productRepository.save(product);
            return new ResponseEntity<>(new CommonResponse("product is created", ResponseStatus.succeed), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new CommonResponse("product does not created", ResponseStatus.failed), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
