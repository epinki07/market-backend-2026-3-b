package mx.edu.tecdesoftware.market_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI marketBackendOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Market Backend API")
                        .version("1.0.0")
                        .description("REST API for market products and purchases."));
    }
}
