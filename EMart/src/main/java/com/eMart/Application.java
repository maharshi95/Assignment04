package com.eMart;

/**
 * Created by maharshigor on 08/07/16.
 */
import com.eMart.aws.sqs.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@SpringBootApplication
@ComponentScan("com.eMart")
public class Application extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	private RequestInterceptor requestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}