package com.slava.soap.webservices.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.slava.soap.webservices.soap.bean.Course;
import com.slava.soap.webservices.soap.service.CourseDetailsService;
import com.slava.soap.webservices.soap.service.CourseDetailsService.Status;

import slava.courses.CourseDetails;
import slava.courses.DeleteCourseRequest;
import slava.courses.DeleteCourseResponse;
import slava.courses.GetAllCourseDetailsRequest;
import slava.courses.GetAllCourseDetailsResponse;
import slava.courses.GetCourseDetailsRequest;
import slava.courses.GetCourseDetailsResponse;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService service;

	@PayloadRoot(namespace = "http://slava/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		Course courseById = service.getCourseById(request.getId());

		GetCourseDetailsResponse response = mapCourse(courseById);
		return response;
	}

	@PayloadRoot(namespace = "http://slava/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {
		List<Course> courses = service.getAllCourses();

		GetAllCourseDetailsResponse response = mapCourses(courses);
		return response;
	}

	@PayloadRoot(namespace = "http://slava/courses", localPart = "DeleteCourseRequest")
	@ResponsePayload
	public DeleteCourseResponse processAllCourseDetailsRequest(@RequestPayload DeleteCourseRequest request) {
		Status result = service.deleteCourseById(request.getId());

		DeleteCourseResponse response = new DeleteCourseResponse();
		response.setStatus(mapStatus(result));
		return response;
	}

	private slava.courses.Status mapStatus(Status status) {
		if (status == Status.FAILURE) {
			return slava.courses.Status.FAILURE;
		}
		return slava.courses.Status.SUCCESS;
	}

	private GetCourseDetailsResponse mapCourse(Course courseById) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		CourseDetails courseDetails = mapCourseDetails(courseById);
		response.setCourseDetails(courseDetails);
		return response;
	}

	private GetAllCourseDetailsResponse mapCourses(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails courseDetails = mapCourseDetails(course);
			response.getCourseDetails().add(courseDetails);
		}
		return response;
	}

	private CourseDetails mapCourseDetails(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		courseDetails.setId(course.getId());
		courseDetails.setName(course.getName());
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}
}
