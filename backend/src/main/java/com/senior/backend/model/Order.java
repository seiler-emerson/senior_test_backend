package com.senior.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_db")
public class Order extends MaturityLevel3Richardson {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
	private UUID id;
	private Float discount;
	private Float amount;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime date;

	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<OrderItens> listItens = new ArrayList<>();

	public Order() {
		super();
		this.setDate(LocalDateTime.now());
	}

	public Order(ArrayList<ItemLevel3> links) {
		super(links);
		this.setDate(LocalDateTime.now());
	}

	public Order(UUID id, Float discount, Float amount, LocalDateTime date, List<OrderItens> listItens) {
		super();
		this.id = id;
		this.discount = discount;
		this.amount = amount;
		this.setDate(LocalDateTime.now());
		this.listItens = listItens;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public List<OrderItens> getListItens() {
		return listItens;
	}

	public void setListItens(List<OrderItens> listItens) {
		this.listItens = listItens;
	}

}
