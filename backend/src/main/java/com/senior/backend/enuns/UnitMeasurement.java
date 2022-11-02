package com.senior.backend.enuns;

public enum UnitMeasurement {

	UNDEFINED(0, "UNDEFINED"), KG(1, "kilos"), M(2, "Meters"), UN(3, "Unity"), H(4, "Hour");

	private Integer cod;
	private String description;

	private UnitMeasurement(Integer cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}

	public static UnitMeasurement toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(UnitMeasurement x: UnitMeasurement.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid unit of measure! "+ cod);
	}
}