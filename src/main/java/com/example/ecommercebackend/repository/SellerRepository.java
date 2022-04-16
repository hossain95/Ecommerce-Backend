package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.dto.SellerInfo;
import com.example.ecommercebackend.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    public Seller findSellerByEmail(String email);

    public Seller findByToken(String token);
}
