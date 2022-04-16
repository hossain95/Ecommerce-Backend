package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.ProductDto;
import com.example.ecommercebackend.dto.dtoConverter.DtoConverter;
import com.example.ecommercebackend.model.Category;
import com.example.ecommercebackend.model.Product;
import com.example.ecommercebackend.model.Seller;
import com.example.ecommercebackend.repository.CategoryRepository;
import com.example.ecommercebackend.repository.ProductRepository;
import com.example.ecommercebackend.repository.SellerRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;


    public GetRequestResponse<Product> productList(){
        List<Product> products = productRepository.findAll();

        return new GetRequestResponse<>(ResponseStatus.succeed, products);
    }

    public CommonResponse productCreate(ProductDto productDto){
        productDto.setCategoryName(productDto.getCategoryName().toLowerCase());
        Category category = categoryRepository.findCategoryByCategoryName(productDto.getCategoryName());

        if(Objects.isNull(category)){
            return new CommonResponse("category does not found!", HttpStatus.BAD_REQUEST);
        }
        Seller seller = sellerRepository.findSellerByEmail(productDto.getSellerEmail());
        if(Objects.isNull(seller)){
            return new CommonResponse("seller does not exist", HttpStatus.BAD_REQUEST);
        }
        productDto.setName(productDto.getName().toLowerCase());
        Product pro = productRepository.findByName(productDto.getName());

        if (Objects.nonNull(pro)){
            return new CommonResponse(productDto.getName()+ " is already exist!", HttpStatus.BAD_REQUEST);
        }

        Product product = new DtoConverter().productDtoToProduct(productDto, category);

        try{
            productRepository.save(product);
            return new CommonResponse("product is created", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new CommonResponse("product does not created", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Product productDetails(Long productId){

        Product product = productRepository.findById(productId).get();

        return product;
    }
}
