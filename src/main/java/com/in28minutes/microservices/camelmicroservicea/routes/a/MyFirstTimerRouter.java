package com.in28minutes.microservices.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;
	
	@Override
	public void configure() throws Exception {
		//timer
		//transformation
		//log
		//Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:first-timer")
		.log("${body}")
		.transform().constant("My Constant Message")
		.log("${body}")
	//	.transform().constant("My Constant Message")
	//	.transform().constant("Time now is" + LocalDateTime.now())
		//.bean(getClass())
		.bean(getCurrentTimeBean)
		.log("${body}")
		.bean(loggingComponent)
		.to("log:first-timer");
		
		
		
	}
	
}

@Component
class GetCurrentTimeBean {
	
	public String getCurrentTime() {
		return "Time now is" + LocalDateTime.now();
	}
}


@Component
class SimpleLoggingProcessingComponent {
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}
}