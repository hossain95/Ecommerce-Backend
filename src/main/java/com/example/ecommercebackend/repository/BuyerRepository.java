package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    public Buyer findBuyerByEmail(String email);

    public Buyer findByToken(String token);
}
