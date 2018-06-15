package com.devid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.devid.entity.Log;

public interface LogRepository extends JpaRepository<Log, Integer>,JpaSpecificationExecutor<Log>{

}
