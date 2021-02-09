package com.kennesaw.rewardsmanagementsystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("edu.kennesaw.matchmakerservice")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket swaggerApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Team 3", "https://teams.microsoft.com/_#/school/conversations/Team%203%20Webservice?threadId=19:52c0021e7aca4a64b8fccd3581d9bed5@thread.tacv2&ctx=channel", "");
        return new ApiInfoBuilder()
                .title("MatchMakerService API")
                .description("SERVICE THAT MANAGES PLAYER STATISTICS, FRIENDS, AND GROUPS")
                .contact(contact)
                .build();
    }

}