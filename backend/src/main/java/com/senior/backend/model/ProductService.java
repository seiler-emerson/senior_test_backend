package com.senior.backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senior.backend.enuns.Type;
import com.senior.backend.enuns.UnitMeasurement;

@Entity
@Table(name = "product_service_db")
public class ProductService extends MaturityLevel3Richardson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Integer unitMeasurement;
	private Integer quantity;
	private Float price;
	private Integer type;

	@JsonIgnore
	@OneToMany(mappedBy = "itens")
	private List<OrderItens> orderItens = new ArrayList<>();

	public ProductService() {
		super();
		this.setUnitMeasurement(UnitMeasurement.UNDEFINED);
		this.setType(Type.UNDEFINED);
	}

	public ProductService(ArrayList<ItemLevel3> links) {
		super(links);
		// TODO Auto-generated constructor stub
	}

	public ProductService(Integer id, String name, UnitMeasurement unitMeasurement, Integer quantity, Float price, Type type,
			List<OrderItens> orderItens) {
		super();
		this.id = id;
		this.name = name;
		this.unitMeasurement = (unitMeasurement == null) ? 0 : unitMeasurement.getCod();
		this.quantity = quantity;
		this.price = price;
		this.type = (type == null) ? 0 : type.getCod();;
		this.orderItens = orderItens;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public List<OrderItens> getOrderItens() {
		return orderItens;
	}

	public void setOrderItens(List<OrderItens> orderItens) {
		this.orderItens = orderItens;
	}

	public UnitMeasurement getUnitMeasurement() {
	    return UnitMeasurement.toEnum(this.unitMeasurement);
	}

	public void setUnitMeasurement(UnitMeasurement unitMeasurement) {
	    this.unitMeasurement = unitMeasurement.getCod();
	}

	public Type getType() {
	    return Type.toEnum(this.type);
	}

	public void setType(Type type) {
	    this.type = type.getCod();
	}


}
