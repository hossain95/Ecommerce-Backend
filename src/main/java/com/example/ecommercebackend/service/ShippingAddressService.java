package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.ShippingAddressDto;
import com.example.ecommercebackend.dto.dtoConverter.DtoConverter;
import com.example.ecommercebackend.model.ShippingAddress;
import com.example.ecommercebackend.model.UserModel;
import com.example.ecommercebackend.repository.ShippingAddressRepository;
import com.example.ecommercebackend.repository.UserRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.status.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressService {
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<CommonResponse> shippingAddressCreate(ShippingAddressDto shippingAddressDto){
        UserModel userModel = userRepository.findById(shippingAddressDto.getUserId()).get();
        ShippingAddress shippingAddress = new DtoConverter().shippingAddressDtoToShippingAddress(shippingAddressDto, userModel);
        shippingAddressRepository.save(shippingAddress);
        return new ResponseEntity<>(new CommonResponse("shipping address created", ResponseStatus.succeed), HttpStatus.CREATED);
    }
}
