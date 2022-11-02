package com.senior.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.senior.backend.model.OrderItens;

@Repository
@EnableJpaRepositories
public interface IOrderItensRepository extends JpaRepository<OrderItens, Integer>  {
	
	@Query("FROM OrderItens WHERE id =:id")
	OrderItens returnById(@Param("id")Integer id);
	
	@Query("SELECT obj FROM OrderItens obj WHERE obj.order_item_id =:order_item_id")
	List<OrderItens> returnIdOrder(@Param("order_item_id")Integer order_item_id);
}
