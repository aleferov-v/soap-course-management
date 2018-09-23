package com.slava.soap.webservices.soap;

import java.util.Collections;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;
import javax.xml.bind.annotation.XmlSchema;

import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurationSupport;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
	}

	@Bean(name = "courses")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("CoursePort");
		defaultWsdl11Definition.setTargetNamespace("http://slava/courses");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setSchema(coursesSchema);
		return defaultWsdl11Definition;
	}
	
	@Bean
	public XsdSchema coursesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("courses-schema.xsd"));
	}
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(createInterceptor());
	}

	@Bean
	public XwsSecurityInterceptor createInterceptor() {
		XwsSecurityInterceptor interceptor = new XwsSecurityInterceptor();
		interceptor.setCallbackHandler(getCallbackHandler());
		interceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return interceptor;
	}

	@Bean
	public CallbackHandler getCallbackHandler() {
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		handler.setUsersMap(Collections.singletonMap("user", "password1"));
		return handler;
	}
}
