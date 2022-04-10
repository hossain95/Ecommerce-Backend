package com.example.ecommercebackend.controller;
import com.example.ecommercebackend.model.PurchaseHistory;
import com.example.ecommercebackend.model.UserModel;
import com.example.ecommercebackend.pdf.UserPurchaseHistoryPDFExporter;
import com.example.ecommercebackend.response.CommonResponse;
import com.example.ecommercebackend.response.GetRequestResponse;
import com.example.ecommercebackend.service.UserService;
import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<CommonResponse> userRegistration(@RequestBody UserModel user){
        return userService.userRegistration(user);
    }

    @GetMapping("/list")
    public ResponseEntity<GetRequestResponse> userList(){
        return userService.userList();
    }

    @GetMapping("/{userId}/purchase/history")
    public ResponseEntity<GetRequestResponse> purchaseHistory(@PathVariable("userId") Long userId){
        return userService.purchaseHistoryById(userId);
    }

    @GetMapping("/{userId}/purchase/histroy/download")
    public void exportToPDF(HttpServletResponse response, @PathVariable("userId") Long userId) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=purchase_history_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<PurchaseHistory> purchaseHistories = userService.purchaseHistoryDownload(userId);

        UserPurchaseHistoryPDFExporter exporter = new UserPurchaseHistoryPDFExporter(purchaseHistories);
        exporter.export(response);

    }
}
