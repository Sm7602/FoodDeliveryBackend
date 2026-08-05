package com.fdb.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fdb.api.dto.deliverypartner.DeliveryPartnerRequest;
import com.fdb.api.dto.deliverypartner.DeliveryPartnerResponse;
import com.fdb.api.dto.deliverypartner.DeliveryPartnerUpdateRequest;
import com.fdb.api.service.DeliveryPartnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/delivery-partners")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @PostMapping
    public  DeliveryPartnerResponse createDeliveryPartner(@Valid @RequestBody DeliveryPartnerRequest request) {
        System.out.println("DeliveryPartnerController.createDeliveryPartner()");
        return deliveryPartnerService.createDeliveryPartner(request);
    }

    @GetMapping("/{id}")
    public  DeliveryPartnerResponse getDeliveryPartnerById(@PathVariable Long id) {
        System.out.println("DeliveryPartnerController.getDeliveryPartnerById()");
        return deliveryPartnerService.getDeliveryPartnerById(id);
    }

    @GetMapping
    public List< DeliveryPartnerResponse> getAllDeliveryPartners() {
        System.out.println("DeliveryPartnerController.getAllDeliveryPartners()");
        return deliveryPartnerService.getAllDeliveryPartners();
    }

    @PutMapping("/{id}")
    public  DeliveryPartnerResponse updateDeliveryPartner(@PathVariable Long id,@Valid @RequestBody DeliveryPartnerUpdateRequest request) {
        System.out.println("DeliveryPartnerController.updateDeliveryPartner()");
        return deliveryPartnerService.updateDeliveryPartner(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteDeliveryPartner(@PathVariable Long id) {
        System.out.println("DeliveryPartnerController.deleteDeliveryPartner()");
        deliveryPartnerService.deleteDeliveryPartner(id);
        return "Delivery Partner Deleted Successfully";
    }
}
