package com.example.ecommercebackend.controller;
import com.example.ecommercebackend.dto.BuyerInfo;
import com.example.ecommercebackend.dto.SellerInfo;
import com.example.ecommercebackend.model.Buyer;
import com.example.ecommercebackend.model.PurchaseHistory;
import com.example.ecommercebackend.model.Seller;
import com.example.ecommercebackend.model.ShippingAddress;
import com.example.ecommercebackend.pdf.UserPurchaseHistoryPDFExporter;
import com.example.ecommercebackend.repository.SellerRepository;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.response.status.GetResponseSingle;
import com.example.ecommercebackend.service.BuyerService;
import com.example.ecommercebackend.service.SellerService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
//@Tag(name = "User")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;



    @PostMapping("/buyer/registration")
    public CommonResponse buyerRegistration(@RequestBody Buyer user){
        return buyerService.buyerRegistration(user);
    }

    @GetMapping("/buyer/list")
    public GetRequestResponse buyerList(){
        return buyerService.buyerList();
    }

    @GetMapping("/buyer/{buyerId}/purchase/history")
    public GetRequestResponse purchaseHistory(@PathVariable("buyerId") Long buyerId){
        System.out.println("Purchase History: "+ buyerId);
        return buyerService.purchaseHistoryById(buyerId);
    }

    @GetMapping("/buyer/{buyerId}/purchase/histroy/download")
    public void exportToPDF(HttpServletResponse response, @PathVariable("buyerId") Long userId) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=purchase_history_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<PurchaseHistory> purchaseHistories = buyerService.purchaseHistoryDownload(userId);

        UserPurchaseHistoryPDFExporter exporter = new UserPurchaseHistoryPDFExporter(purchaseHistories);
        exporter.export(response);

    }


    @PostMapping("/seller/registration")
    public CommonResponse sellerRegistration(@RequestBody Seller user){
        return sellerService.sellerRegistration(user);
    }

    @PostMapping("/buyer/update")
    public CommonResponse buyerInfoUpdate(@RequestBody BuyerInfo buyerInfo){
        return buyerService.buyerInfoUpdate(buyerInfo);
    }

    @GetMapping("/seller/list")
    public ResponseEntity<GetRequestResponse> sellerList(){
        return sellerService.sellList();
    }

    @GetMapping("/buyer/info/{token}")
    public BuyerInfo getBuyer(@PathVariable("token") String token){

        return buyerService.getBuyer(token);
    }

    @GetMapping("/seller/info/{token}")
    public SellerInfo getSeller(@PathVariable("token") String token){

        return sellerService.getSeller(token);
    }

    @GetMapping("/buyer/{buyerId}/shipping-address")
    public GetResponseSingle<ShippingAddress> shippingAddress(@PathVariable("buyerId") Long buyerId){

        return buyerService.shippingAddress(buyerId);
    }

    @PostMapping("/buyer/{buyerId}/shipping-address/update")
    public CommonResponse shippingAddressUpdate(@RequestBody ShippingAddress shippingAddress, @PathVariable("buyerId") Long buyerId){

        return buyerService.shippingAddressUpdate(shippingAddress, buyerId);
    }
}
