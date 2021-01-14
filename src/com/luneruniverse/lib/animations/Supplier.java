package com.luneruniverse.lib.animations;

class Supplier<T> {
	
	private T value;
	
	public Supplier(T value) {
		this.value = value;
	}
	
	public void set(T value) {
		this.value = value;
	}
	
	public T get() {
		return value;
	}
	
}
