package com.slava.soap.webservices.soap.bean;

public class Course {

	private int id;
	private String name;
	private String description;
	
	public Course(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	
	
}
