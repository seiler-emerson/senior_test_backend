package com.senior.backend.enuns;

public enum Type {

	UNDEFINED(0, "UNDEFINED"), PRODUCT(1, "Product"), SERVICE(2, "Service");

	private Integer cod;
	private String description;

	private Type(Integer cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}

	public static Type toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Type x: Type.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid type! "+ cod);
	}
}
