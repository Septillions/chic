package com.github.chic.common.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Swagger 配置类
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    /**
     * Swagger 配置参数类
     */
    @Resource
    private SwaggerProps swaggerProps;
    /**
     * Knife4j 解析器
     */
    @Resource
    private OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(swaggerProps.getGroupName())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .extensions(openApiExtensionResolver.buildSettingExtensions());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProps.getTitle())
                .description(swaggerProps.getDescription())
                .version(swaggerProps.getVersion())
                .contact(new Contact(swaggerProps.getContact().getName(), swaggerProps.getContact().getUrl(), swaggerProps.getContact().getEmail()))
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey("Token", JwtProps.header, "Header"));
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.any())
                .build());
    }

    private List<SecurityReference> securityReferences() {
        return Collections.singletonList(SecurityReference.builder()
                .reference("Token")
                .scopes(new AuthorizationScope[0])
                .build());
    }
}
