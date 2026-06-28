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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/socios")
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
    public ResponseEntity<socioModel> obtenerPorId(
            @Parameter(description = "ID del socio", example = "1")
            @PathVariable Long id
    ) {
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
    public ResponseEntity<socioModel> obtenerPorRut(
            @Parameter(description = "RUT del socio", example = "12345678-9")
            @PathVariable String rut
    ) {
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
            @ApiResponse(
                    responseCode = "200",
                    description = "Socio encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Socio no encontrado"
            )
    })
    public ResponseEntity<socioModel> obtenerPorEstado(
            @Parameter(description = "Estado del socio", example = "ACTIVO")
            @PathVariable String estado
    ) {
        return service.obtenerSocioPorEstado(estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<socioModel> obtenerPorEdad(
            @Parameter(description = "Edad del socio", example = "18")
            @PathVariable Integer edad
    ) {
        return service.obtenerSocioPorEdad(edad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<socioModel> crearSocio(
            @Valid @RequestBody socioDTO dto
    ) {
        socioModel nuevoSocio = service.crearSocio(dto);
        return ResponseEntity.ok(nuevoSocio);
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
    public ResponseEntity<socioModel> actualizarSocio(
            @PathVariable Long id,
            @Valid @RequestBody socioDTO dto
    ) {
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
    public ResponseEntity<socioModel> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
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