package com.senior.backend.model;

import java.util.ArrayList;

public class ItemLevel3 {

	private String ref;
	private String url;
	private ArrayList<String> headers;
	private String body;

	public ItemLevel3() {
		super();
	}

	public ItemLevel3(String ref, String url, ArrayList<String> headers, String body) {
		super();
		this.ref = ref;
		this.url = url;
		this.headers = headers;
		this.body = body;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<String> getHeaders() {
		return headers;
	}

	public void setHeaders(ArrayList<String> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
