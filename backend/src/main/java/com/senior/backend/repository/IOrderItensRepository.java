package com.senior.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senior.backend.model.OrderItens;

@Repository
@EnableJpaRepositories
public interface IOrderItensRepository extends JpaRepository<OrderItens, UUID> {

	@Query("FROM OrderItens WHERE id =:id")
	OrderItens returnById(@Param("id") UUID id);

	@Query("FROM OrderItens WHERE order_item_id =:order_item_id")
	List<OrderItens> returnIdOrder(@Param("order_item_id") UUID id);
}
