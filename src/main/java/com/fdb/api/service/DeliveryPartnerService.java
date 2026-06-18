package com.fdb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.DeliveryPartnerRepository;
import com.fdb.api.entity.DeliveryPartner;

@Service
public class DeliveryPartnerService {

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryPartner createDeliveryPartner(DeliveryPartner deliveryPartner) {
        System.out.println("DeliveryPartnerService.createDeliveryPartner()");
        deliveryPartner.setCreatedAt(LocalDateTime.now());
        deliveryPartner.setUpdatedAt(LocalDateTime.now());
        return deliveryPartnerRepository.save(deliveryPartner);
    }

    public DeliveryPartner getDeliveryPartnerById(Long id) {
        System.out.println("DeliveryPartnerService.getDeliveryPartnerById()");
        return deliveryPartnerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Delivery Partner not found"));
    }

    public List<DeliveryPartner> getAllDeliveryPartners() {
        System.out.println("DeliveryPartnerService.getAllDeliveryPartners()");
        return deliveryPartnerRepository.findAll();
    }

    public DeliveryPartner updateDeliveryPartner(Long id,DeliveryPartner deliveryPartner) {
        System.out.println("DeliveryPartnerService.updateDeliveryPartner()");
        DeliveryPartner existingPartner =getDeliveryPartnerById(id);
        existingPartner.setPartnerName(deliveryPartner.getPartnerName());
        existingPartner.setPhoneNumber(deliveryPartner.getPhoneNumber());
        existingPartner.setVehicleNumber(deliveryPartner.getVehicleNumber());
        existingPartner.setAvailable(deliveryPartner.getAvailable());
        existingPartner.setUpdatedAt(LocalDateTime.now());
        return deliveryPartnerRepository.save(existingPartner);
    }

    public void deleteDeliveryPartner(Long id) {
        System.out.println("DeliveryPartnerService.deleteDeliveryPartner()");
        DeliveryPartner partner =getDeliveryPartnerById(id);
        deliveryPartnerRepository.delete(partner);
    }
}