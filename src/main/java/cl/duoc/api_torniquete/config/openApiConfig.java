package cl.duoc.api_torniquete.config;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI scooterOpenAPi(){
        return new OpenAPI()
                .info(new Info()
                        .title ("API Monopatin(Scooter) - Servicio de Monopatin")
                        .description ("Gestiona el inventario. el estado de scooter - CRUD Completo")
                        .version ("1.0")
                        .contact(new Contact()
                                .name("DoucUC")
                                .email("soporte@duoc.cl"))
                        .license(new License()
                                .name("Uso academico"))
                );
    }
}