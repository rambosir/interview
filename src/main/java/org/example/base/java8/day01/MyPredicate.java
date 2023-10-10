package org.example.base.java8.day01;

@FunctionalInterface
public interface MyPredicate<T> {

	public boolean test(T t);
	
}
