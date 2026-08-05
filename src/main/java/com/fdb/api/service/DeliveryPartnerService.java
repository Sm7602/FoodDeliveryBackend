package com.fdb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdb.api.dao.DeliveryPartnerRepository;
import com.fdb.api.dto.deliverypartner.DeliveryPartnerRequest;
import com.fdb.api.dto.deliverypartner.DeliveryPartnerResponse;
import com.fdb.api.dto.deliverypartner.DeliveryPartnerUpdateRequest;
import com.fdb.api.entity.DeliveryPartner;
import com.fdb.api.entity.User;

@Service
public class DeliveryPartnerService {

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepository;
    
    private DeliveryPartnerResponse convertToResponse(DeliveryPartner deliveryPartner) {

        return DeliveryPartnerResponse.builder()
                .id(deliveryPartner.getId())
                .firstName(deliveryPartner.getFirstName())
                .lastName(deliveryPartner.getLastName())
                .phoneNumber(deliveryPartner.getPhoneNumber())
                .vehicleNumber(deliveryPartner.getVehicleNumber())
                .drivingLicenseNumber(deliveryPartner.getDrivingLicenseNumber())
                .vehicleType(deliveryPartner.getVehicleType())
                .available(deliveryPartner.getAvailable())
                .createdAt(deliveryPartner.getCreatedAt())
                .updatedAt(deliveryPartner.getUpdatedAt())
                .orders(deliveryPartner.getOrders())
                .userId(deliveryPartner.getUser().getId())
                .build();
    }

    public DeliveryPartnerResponse createDeliveryPartner(DeliveryPartnerRequest request) {
        System.out.println("DeliveryPartnerService.createDeliveryPartner()");
        
     	User user=User.builder()
    			.id(request.getUserId())
    			.build();
    	
     	DeliveryPartner deliveryPartner=DeliveryPartner.builder()
     			.firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .vehicleNumber(request.getVehicleNumber())
                .drivingLicenseNumber(request.getDrivingLicenseNumber())
                .vehicleType(request.getVehicleType())
                .available(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
     			.build();
     	
     	deliveryPartner=deliveryPartnerRepository.save(deliveryPartner);
     	return convertToResponse(deliveryPartner);
     	
    }

    public DeliveryPartnerResponse getDeliveryPartnerById(Long id) {
        System.out.println("DeliveryPartnerService.getDeliveryPartnerById()");
        DeliveryPartner deliveryPartner= deliveryPartnerRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Delivery Partner not found"));
        return convertToResponse(deliveryPartner);
    }

    public List<DeliveryPartnerResponse> getAllDeliveryPartners() {
        System.out.println("DeliveryPartnerService.getAllDeliveryPartners()");
        return deliveryPartnerRepository.findAll()
        		    .stream()
	            .map(this::convertToResponse)
	            .toList();
    }

    public DeliveryPartnerResponse updateDeliveryPartner(Long id,DeliveryPartnerUpdateRequest request) {
        System.out.println("DeliveryPartnerService.updateDeliveryPartner()");
        DeliveryPartner existingPartner =deliveryPartnerRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Delivery Partner not found"));
        
        existingPartner.setFirstName(request.getFirstName());
        existingPartner.setLastName(request.getLastName());
        existingPartner.setPhoneNumber(request.getPhoneNumber());
        existingPartner.setVehicleNumber(request.getVehicleNumber());
        existingPartner.setDrivingLicenseNumber(request.getDrivingLicenseNumber());
        existingPartner.setVehicleType(request.getVehicleType());
        existingPartner.setAvailable(request.getAvailable());
        existingPartner.setUpdatedAt(LocalDateTime.now());
        
        existingPartner=deliveryPartnerRepository.save(existingPartner);
     	return convertToResponse(existingPartner);
    }

    public void deleteDeliveryPartner(Long id) {
        System.out.println("DeliveryPartnerService.deleteDeliveryPartner()");
        DeliveryPartner partner =deliveryPartnerRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Delivery Partner not found"));
        
        deliveryPartnerRepository.delete(partner);
    }
}