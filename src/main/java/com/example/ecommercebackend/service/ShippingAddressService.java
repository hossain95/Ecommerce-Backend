package com.example.ecommercebackend.service;

import com.example.ecommercebackend.dto.ShippingAddressDto;
import com.example.ecommercebackend.dto.dtoConverter.DtoConverter;
import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.ShippingAddress;
import com.example.ecommercebackend.repository.BuyerRepository;
import com.example.ecommercebackend.repository.ShippingAddressRepository;
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
    private BuyerRepository buyerRepository;

    public CommonResponse shippingAddressCreate(ShippingAddressDto shippingAddressDto){
        Buyer buyer = buyerRepository.findById(shippingAddressDto.getUserId()).get();
        ShippingAddress shippingAddress = new DtoConverter().shippingAddressDtoToShippingAddress(shippingAddressDto, buyer);
        shippingAddressRepository.save(shippingAddress);
        return new CommonResponse("shipping address created", HttpStatus.CREATED);
    }
}
