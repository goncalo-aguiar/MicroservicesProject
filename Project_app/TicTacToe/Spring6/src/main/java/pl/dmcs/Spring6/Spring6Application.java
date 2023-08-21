package pl.dmcs.Spring6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.dmcs.Spring6.interceptor.AuthInterceptor;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Spring6Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring6Application.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthInterceptor> authFilter(AuthInterceptor authInterceptor){
		FilterRegistrationBean<AuthInterceptor> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(authInterceptor);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

}
