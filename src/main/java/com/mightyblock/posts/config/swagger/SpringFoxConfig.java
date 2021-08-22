package com.mightyblock.posts.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Swagger configuration
 */
@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    final List<Response> globalResponses = Arrays.asList(
            new ResponseBuilder()
                    .code("200")
                    .description("OK")
                    .build(),
            new ResponseBuilder()
                    .code("400")
                    .description("Bad Request")
                    .build(),
            new ResponseBuilder()
                    .code("403")
                    .description("Forbidden")
                    .build(),
            new ResponseBuilder()
                    .code("500")
                    .description("Internal Error")
                    .build());

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalResponses)
                .globalResponses(HttpMethod.POST, globalResponses)
                .globalResponses(HttpMethod.DELETE, globalResponses)
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.mightyblock.posts" ) )
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Posts REST API",
                null,
                "1.0",
                null,
                new Contact(
                        "Pizzio Dario",
                        null,
                        "pizziodario@gmail.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}