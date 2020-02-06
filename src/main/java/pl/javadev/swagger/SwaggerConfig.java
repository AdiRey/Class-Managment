package pl.javadev.swagger;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Profile("dev")
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String NO_ERROR_REGEX = "(?!.*error).*$";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Classes")
                .description("Rest for this app")
                .version("0.0.1")
                .contact(new Contact("Adrian Kowal", "https://github.com/AdiRey", "kadrian13@o2.pl"))
                .build();
    }

    private Predicate<String> paths() {
        return regex(NO_ERROR_REGEX);
    }
}
