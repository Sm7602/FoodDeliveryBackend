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
import com.fdb.api.entity.DeliveryPartner;
import com.fdb.api.service.DeliveryPartnerService;

@RestController
@RequestMapping("/api/delivery-partners")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @PostMapping
    public DeliveryPartner createDeliveryPartner( @RequestBody DeliveryPartner deliveryPartner) {
        System.out.println("DeliveryPartnerController.createDeliveryPartner()");
        return deliveryPartnerService.createDeliveryPartner(deliveryPartner);
    }

    @GetMapping("/{id}")
    public DeliveryPartner getDeliveryPartnerById(@PathVariable Long id) {
        System.out.println("DeliveryPartnerController.getDeliveryPartnerById()");
        return deliveryPartnerService.getDeliveryPartnerById(id);
    }

    @GetMapping
    public List<DeliveryPartner> getAllDeliveryPartners() {
        System.out.println("DeliveryPartnerController.getAllDeliveryPartners()");
        return deliveryPartnerService.getAllDeliveryPartners();
    }

    @PutMapping("/{id}")
    public DeliveryPartner updateDeliveryPartner(@PathVariable Long id,@RequestBody DeliveryPartner deliveryPartner) {
        System.out.println("DeliveryPartnerController.updateDeliveryPartner()");
        return deliveryPartnerService.updateDeliveryPartner(id, deliveryPartner);
    }

    @DeleteMapping("/{id}")
    public String deleteDeliveryPartner(@PathVariable Long id) {
        System.out.println("DeliveryPartnerController.deleteDeliveryPartner()");
        deliveryPartnerService.deleteDeliveryPartner(id);
        return "Delivery Partner Deleted Successfully";
    }
}
