package com.editors;

public class ValuePair {

	String id;
	String value;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public ValuePair(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	@Override
	public String toString() {

		return this.value;
	}
	
}
