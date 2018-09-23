package com.slava.soap.webservices.soap.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.slava.soap.webservices.soap.bean.Course;

@Component
public class CourseDetailsService {

	public enum Status {
		SUCCESS, FAILURE
	}
	
	private static List<Course> courses = new ArrayList<>();
	static {
		courses.add(new Course(1, "Course 1", "Details 1"));
		courses.add(new Course(2, "Course 2", "Details 2"));
		courses.add(new Course(3, "Course 3", "Details 3"));
	}

	public Course getCourseById(int id) {
		return courses.stream()
				.filter(c -> c.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public List<Course> getAllCourses() {
		return courses;
	}
	
	public Status deleteCourseById(int id) {
		Course course = getCourseById(id);
		return courses.remove(course) ? Status.SUCCESS : Status.FAILURE;
	}

}
