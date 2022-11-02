package com.senior.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senior.backend.model.Order;

@Repository
@EnableJpaRepositories
public interface IOrderRepository extends JpaRepository<Order, Integer> {

	@Query("FROM Order WHERE id =:id")
	Order returnById(@Param("id")Integer id);
}
