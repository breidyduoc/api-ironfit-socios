package cl.duoc.api_ironfit_socios.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class socioDTO {
    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 12, message = "El RUT no puede superar los 12 caracteres")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede superar los 50 caracteres")
    private String apellido;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 15, message = "La edad mínima permitida es 15 años")
    private Integer edad;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20, message = "El estado no puede superar los 20 caracteres")
    @Pattern(regexp = "ACTIVO|INACTIVO|SUSPENDIDO",message = "El estado debe ser ACTIVO,INACTIVO o SUSPENDIDO")
    private String estado;

    private LocalDate ultimoAcceso;

    @Size(max = 50, message = "La sucursal no puede superar los 50 caracteres")
    private String sucursal;
}
