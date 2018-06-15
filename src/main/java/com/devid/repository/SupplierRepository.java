package com.devid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.devid.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer>,JpaSpecificationExecutor<Supplier>{
	
}


