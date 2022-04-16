package com.example.ecommercebackend.repository;

import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
//    public List<PurchaseHistory> findAllByUser(UserModel userModel);
    public List<PurchaseHistory> findAllByBuyer(Buyer buyer);
}
