package com.senior.backend.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_itens_db")
public class OrderItens extends MaturityLevel3Richardson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "product_service_id")
	private ProductService itens;

	private Short quantityPurchased;

	@ManyToOne
	@JoinColumn(name = "order_item_id")
	private Order order;

	public OrderItens() {
		super();
	}

	public OrderItens(ArrayList<ItemLevel3> links) {
		super(links);
	}

	public OrderItens(Integer id, ProductService itens, Short quantityPurchased, Order order) {
		super();
		this.id = id;
		this.itens = itens;
		this.quantityPurchased = quantityPurchased;
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProductService getItens() {
		return itens;
	}

	public void setItens(ProductService itens) {
		this.itens = itens;
	}

	public Short getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(Short quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
