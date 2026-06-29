package cl.duoc.api_ironfit_socios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "SOCIO",schema = "ADMIN")
@Data
public class socioModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOCIO")
    private Long id;

    @Column(name = "RUT", nullable = false, unique = true, length = 12)
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 50)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Column(name = "EDAD", nullable = false)
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 15, message = "La edad mínima permitida es 15 años")
    private Integer edad;

    @Column(name = "ESTADO", nullable = false)
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "ACTIVO|INACTIVO|SUSPENDIDO", message = "El estado debe ser ACTIVO, INACTIVO o SUSPENDIDO.")
    private String estado;

    @Column(name = "ULTIMO_ACCESO")
    private LocalDate ultimoAcceso;

    @Column(name = "SUCURSAL", length = 50)
    private String sucursal;
}