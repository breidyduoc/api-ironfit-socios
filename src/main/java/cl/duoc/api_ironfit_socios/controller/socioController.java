package cl.duoc.api_ironfit_socios.controller;

import cl.duoc.api_ironfit_socios.dto.socioDTO;
import cl.duoc.api_ironfit_socios.model.socioModel;
import cl.duoc.api_ironfit_socios.service.socioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v4/socios")
public class socioController {

    private final socioService service;

    @GetMapping
    @Operation(
            summary = "Obtiene todos los socios",
            description = "Devuelve la lista completa de socios registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        [
                            {
                                "id": 1,
                                "rut": "12345678-9",
                                "nombre": "Juan",
                                "apellido": "Pérez",
                                "edad": 22,
                                "estado": "ACTIVO",
                                "ultimoAcceso": "2026-06-28",
                                "sucursal": "Santiago Centro"
                            }
                        ]
                        """)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No existen socios registrados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<List<socioModel>> obtenerTodos() {
        List<socioModel> socios = service.obtenerTodosLosSocios();

        if(socios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(socios);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar socio por ID",
            description = "Obtiene un socio específico según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio encontrado"),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado")
    })
    public ResponseEntity<socioModel> obtenerPorId(@Parameter(description = "ID del socio", example = "1") @PathVariable Long id) {
        return service.obtenerSocioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/rut/{rut}")
    @Operation(
            summary = "Buscar socio por RUT",
            description = "Obtiene un socio según su RUT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio encontrado"),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado")
    })
    public ResponseEntity<socioModel> obtenerPorRut(@Parameter(description = "RUT del socio", example = "12345678-9") @PathVariable String rut) {
        return service.obtenerSocioPorRut(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/estado/{estado}")
    @Operation(
            summary = "Buscar socio por estado",
            description = "Obtiene socios según su estado actual"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Socio encontrado"),
            @ApiResponse(responseCode = "404",description = "Socio no encontrado")
    })
    public ResponseEntity<List<socioModel>> obtenerPorEstado(@Parameter(description = "Estado del socio", example = "ACTIVO") @PathVariable String estado) {
        List<socioModel> socios = service.obtenerSocioPorEstado(estado);

        if (socios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(socios);
    }


    @GetMapping("/edad/{edad}")
    @Operation(
            summary = "Buscar socio por edad",
            description = "Obtiene socios filtrando por edad"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socios encontrados"),
            @ApiResponse(responseCode = "404", description = "No existen socios con esa edad")
    })
    public ResponseEntity<List<socioModel>> obtenerPorEdad(@Parameter(description = "Edad del socio", example = "18") @PathVariable Integer edad) {
        List<socioModel> socios = service.obtenerSocioPorEdad(edad);

        if (socios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(socios);
    }


    @GetMapping("/sucursal/{sucursal}")
    @Operation(
            summary = "Buscar socios por sucursal",
            description = "Obtiene todos los socios pertenecientes a una sede"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socios encontrados correctamente"),
            @ApiResponse(responseCode = "404", description = "Socios no encontrados ")
    })
    public ResponseEntity<List<socioModel>> obtenerPorSucursal(@Parameter(description = "Sucursal del socio", example = "SAN_BERNARDO")@PathVariable String sucursal){

        return ResponseEntity.ok(
                service.obtenerSociosPorSucursal(sucursal)
        );
    }

    @GetMapping("/inactivos")
    @Operation(
            summary = "Buscar socios inactivos",
            description = "Obtiene socios cuyo último acceso es anterior a una fecha"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socios encontrados correctamente"),
            @ApiResponse(responseCode = "404", description = "Socios no encontrados")
    })
    public ResponseEntity<List<socioModel>> obtenerInactivos(@Parameter(description = "Fecha límite para buscar inactividad",example = "2026-06-01")@RequestParam LocalDate fecha){

        return ResponseEntity.ok(
                service.obtenerSociosInactivos(fecha)
        );
    }

    @PostMapping
    @Operation(
            summary = "Registrar nuevo socio",
            description = "Crea un nuevo socio dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Socio creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<socioModel> crearSocio(@Valid @RequestBody socioDTO dto) {
        socioModel nuevoSocio = service.crearSocio(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevoSocio);
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar socio",
            description = "Actualiza completamente los datos de un socio"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Socio actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<socioModel> actualizarSocio(@PathVariable Long id,@Valid @RequestBody socioDTO dto) {
        return service.actualizaSocio(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}")
    @Operation(
            summary = "Actualizar estado del socio",
            description = "Modifica únicamente el estado del socio"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado"),
            @ApiResponse(responseCode = "400", description = "Estado inválido")
    })
    public ResponseEntity<socioModel> actualizarEstado(@PathVariable Long id,@RequestBody Map<String, String> request) {
        String nuevoEstado = request.get("estado");

        return service.actualizaEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar socio",
            description = "Elimina un socio por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Socio eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Socio no encontrado"),
            @ApiResponse(responseCode = "400", description = "No se puede eliminar por regla de negocio")
    })
    public ResponseEntity<Void> eliminarSocio(@PathVariable Long id) {
        if (service.borrarSocio(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}