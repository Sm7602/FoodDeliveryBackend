package com.fdb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fdb.api.entity.DeliveryPartner;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Long> {

}
