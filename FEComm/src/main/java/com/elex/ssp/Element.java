package com.elex.ssp;

public class Element {
    private String key = null;
	public Element(String key){
        this.setKey(key);
    }
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}