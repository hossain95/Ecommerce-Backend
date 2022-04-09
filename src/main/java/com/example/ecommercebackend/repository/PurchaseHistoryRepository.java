package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.PurchaseHistory;
import com.example.ecommercebackend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    public List<PurchaseHistory> findAllByUser(UserModel userModel);
}
