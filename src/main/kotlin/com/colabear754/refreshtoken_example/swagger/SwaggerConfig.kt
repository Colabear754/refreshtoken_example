package com.colabear754.refreshtoken_example.swagger

import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod

private const val SECURITY_SCHEME_NAME = "authorization"

@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerApi(): OpenAPI = OpenAPI()
        .components(Components()
            .addSecuritySchemes(SECURITY_SCHEME_NAME, SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")))
        .addSecurityItem(SecurityRequirement().addList(SECURITY_SCHEME_NAME))
        .info(Info()
            .title("Refresh Token 예제")
            .description("기존 스프링시큐리티와 JWT를 이용한 사용자 인증 예제에 리프레시 토큰을 적용한 예제입니다.")
            .version("1.0.0"))

    @Bean
    fun globalHeader() = OperationCustomizer { operation: Operation, _: HandlerMethod ->
        operation.addParametersItem(Parameter()
            .`in`(ParameterIn.HEADER.toString())
            .schema(StringSchema().name("Refresh-Token"))
            .name("Refresh-Token"))
        operation
    }
}