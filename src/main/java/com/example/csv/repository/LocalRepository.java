package com.example.csv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.csv.entity.LocalEntity;

@Repository
public interface LocalRepository extends JpaRepository < LocalEntity, Long > {
	//
	@Query(value = "select * from local where not exists(select * from cmdb where cmdb.name = local.name)", nativeQuery = true)
	List<LocalEntity> findAllDiffLocalEntities();
}