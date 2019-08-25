package com.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class WechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(WechatApplication.class, args);
	}
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
				.apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.wechat"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("xixi", "https://github.com/onexixi", "");
		return new ApiInfoBuilder().title("Api Documentation").description(" ")
				.termsOfServiceUrl(" ").contact(contact).version("1.0").build();
	}

}
