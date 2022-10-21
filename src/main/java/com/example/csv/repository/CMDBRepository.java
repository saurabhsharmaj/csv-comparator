package com.example.csv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.csv.entity.CMDBEntity;

@Repository
public interface CMDBRepository extends JpaRepository < CMDBEntity, Long > {
	@Query(value = "select * from cmdb where not exists(select * from local where cmdb.name = local.name)", nativeQuery = true)
	List<CMDBEntity> findAllDiffCMDBEntities();
}