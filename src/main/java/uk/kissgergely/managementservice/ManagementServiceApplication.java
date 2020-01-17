package uk.kissgergely.managementservice;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementServiceApplication.class, args);
	}

	@Bean
	public Docket APIRoot() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(paths()).apis(RequestHandlerSelectors.any())
				.build().apiInfo(getApiInfo());
	}

	private Predicate<String> paths() {
		return regex(APIConstants.API_ROOT + APIConstants.REGEX_ALL);
	}

	private Contact getContactDetails() {
		return new Contact(APIConstants.CONTACT_NAME, APIConstants.CONTACT_URL, APIConstants.CONTACT_EMAIL);
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title(APIConstants.MANAGEMENT_SERVICE_TITLE)
				.version(APIConstants.MANAGEMENT_SERVICE_VERSION).contact(getContactDetails()).build();
	}

}
