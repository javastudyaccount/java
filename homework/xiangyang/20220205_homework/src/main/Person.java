package main;

public class Person {
	String name;
	int age;

	public Person(String n, int a) {
		name = n;
		age = a;
	}

	@Override
	public String toString() {
		return String.format("{name=%s, age=%d}", name, age);
	}
}
