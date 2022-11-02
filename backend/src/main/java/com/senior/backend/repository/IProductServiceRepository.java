package com.senior.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senior.backend.model.ProductService;

@Repository
@EnableJpaRepositories
public interface IProductServiceRepository extends JpaRepository<ProductService, Integer> {

	@Query("FROM ProductService WHERE id =:id")
	ProductService returnById(@Param("id")Integer id);
	
}
