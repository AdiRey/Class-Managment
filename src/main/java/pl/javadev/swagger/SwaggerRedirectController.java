package pl.javadev.swagger;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("dev")
@Controller
public class SwaggerRedirectController {
    @GetMapping("")
    public String swaggerMapping() {
        String swagger = "redirect:/swagger-ui.html";
        return swagger;
    }
}
