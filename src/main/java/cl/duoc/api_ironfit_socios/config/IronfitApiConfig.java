package cl.duoc.api_ironfit_socios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class IronfitApiConfig {
    @Bean
    public OpenAPI ironfitOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API IronFit (Socios)")
                        .description("Gestiona socios, actividad operacional e integración financiera")
                        .version("4.0")
                        .contact(new Contact()
                                .name("DuocUC")
                                .email("soporte@duoc.cl"))
                        .license(new License()
                                .name("Uso académico")));
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}