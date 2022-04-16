package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.PurchaseHistory;
import com.example.ecommercebackend.repository.PurchaseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseHistoryService {
    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    public List<PurchaseHistory> purchaseHistoryByUser(Buyer user){
        List<PurchaseHistory> purchaseHistories = purchaseHistoryRepository.findAllByBuyer(user);
        return purchaseHistories;
    }
}
