package com.mikhailau.poi;

public class Test {
	public static void main(String[] args) {
		Parent child = new Child();
		new Test().test(child);
		String path = "ui/tutorials/1";
		String s = path.replaceAll("[^/]*/", "");
		System.out.println(s);

	}

	public void test(Parent parent) {
		System.out.println("Parent");
	}

	public void test(SubParent subParent) {
		System.out.println("Subparent");
	}

	public void test(Child child) {
		System.out.println("Child");
	}

	static class Parent {

	}

	static class SubParent extends Parent {

	}

	static class Child extends Parent {

	}
}
