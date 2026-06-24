package cl.duoc.api_torniquete.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class openApiConfig {
    @Bean
    public OpenAPI scooterOpenAPi(){
        return new OpenAPI()
                .info(new Info()
                        .title ("API ironfit(toriniquete) - Servicio de ironfit")
                        .description ("Gestiona el acceso de los torniquetes - CRUD Completo")
                        .version ("1.0")
                        .contact(new Contact()
                                .name("DoucUC")
                                .email("soporte@duoc.cl"))
                        .license(new License()
                                .name("Uso academico"))
                );
    }
}