package com.example.springbootWithPostgresql;

import com.example.springbootWithPostgresql.interceptor.AuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableFeignClients
public class SpringbootWithPostgresqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWithPostgresqlApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthInterceptor> authFilter(AuthInterceptor authInterceptor){
		FilterRegistrationBean<AuthInterceptor> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(authInterceptor);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
