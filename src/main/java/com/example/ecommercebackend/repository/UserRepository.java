package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.PurchaseHistory;
import com.example.ecommercebackend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    public UserModel findByEmail(String email);
}
