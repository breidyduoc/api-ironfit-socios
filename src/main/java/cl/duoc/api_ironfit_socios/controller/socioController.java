package cl.duoc.api_ironfit_socios.controller;

import cl.duoc.api_ironfit_socios.dto.socioDTO;
import cl.duoc.api_ironfit_socios.model.socioModel;
import cl.duoc.api_ironfit_socios.service.socioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/socios")
public class socioController {

    private final socioService service;

    @GetMapping
    public ResponseEntity<List<socioModel>> obtenerTodos() {
        List<socioModel> socios = service.obtenerTodosLosSocios();

        if(socios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(socios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<socioModel> obtenerPorId(@PathVariable Long id) {
        return service.obtenerSocioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<socioModel> obtenerPorRut(@PathVariable String rut) {
        return service.obtenerSocioPorRut(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<socioModel> obtenerPorEstado(@PathVariable String estado) {
        return service.obtenerSocioPorEstado(estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/edad/{edad}")
    public ResponseEntity<socioModel> obtenerPorEdad(@PathVariable Integer edad) {
        return service.obtenerSocioPorEdad(edad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<socioModel> crearSocio(
            @Valid @RequestBody socioDTO dto
    ) {
        socioModel nuevoSocio = service.crearSocio(dto);
        return ResponseEntity.ok(nuevoSocio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<socioModel> actualizarSocio(
            @PathVariable Long id,
            @Valid @RequestBody socioDTO dto
    ) {
        return service.actualizaSocio(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<socioModel> actualizarEstado(
            @PathVariable Long id,
            @RequestBody socioDTO dto
    ) {
        return service.actualizaEstado(id, dto.getEstado())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Long id) {
        if (service.borrarSocio(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}