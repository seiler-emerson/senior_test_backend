package com.senior.backend.model;

import java.util.ArrayList;

public class MaturityLevel3Richardson {

	ArrayList<ItemLevel3> links;

	public MaturityLevel3Richardson() {
		this.links = new ArrayList<>();
	}

	public MaturityLevel3Richardson(ArrayList<ItemLevel3> links) {
		super();
		this.links = links;
	}

	public ArrayList<ItemLevel3> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<ItemLevel3> links) {
		this.links = links;
	}

}
