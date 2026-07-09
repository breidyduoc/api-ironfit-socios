package cl.duoc.api_socios.controllerTest;

import cl.duoc.api_ironfit_socios.dto.socioDTO;
import cl.duoc.api_ironfit_socios.controller.socioController;
import cl.duoc.api_ironfit_socios.model.socioModel;
import cl.duoc.api_ironfit_socios.service.socioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocioControllerTest {

    @Mock
    private socioService service;

    @InjectMocks
    private socioController controller;

    private socioModel socio;
    private socioDTO dto;

    @BeforeEach
    void a() {
        socio = new socioModel();
        socio.setId(1L);
        socio.setRut("12345678-9");
        socio.setNombre("Juan");
        socio.setApellido("Perez");
        socio.setEdad(20);
        socio.setEstado("ACTIVO");
        socio.setUltimoAcceso(LocalDate.now());
        socio.setSucursal("Santiago");

        dto = new socioDTO();
        dto.setRut("12345678-9");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEdad(20);
        dto.setEstado("ACTIVO");
        dto.setUltimoAcceso(LocalDate.now());
        dto.setSucursal("Santiago");
    }

    // 200 OK
    @Test
    @DisplayName("Código 200 - Obtener todos los socios")
    void obtenerTodosOk() {
        when(service.obtenerTodosLosSocios()).thenReturn(List.of(socio));

        ResponseEntity<List<socioModel>> response = controller.obtenerTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Código 200 - Buscar socios por sucursal")
    void obtenerPorSucursalOk(){

        socio.setSucursal("SAN_BERNARDO");

        when(service.obtenerSociosPorSucursal("SAN_BERNARDO"))
                .thenReturn(List.of(socio));

        ResponseEntity<List<socioModel>> response =
                controller.obtenerPorSucursal("SAN_BERNARDO");


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(
                "SAN_BERNARDO",
                response.getBody().getFirst().getSucursal()
        );
    }

    @Test
    @DisplayName("Código 404 - No existen socios en sucursal")
    void obtenerPorSucursalNotFound(){

        when(service.obtenerSociosPorSucursal("VALDIVIA"))
                .thenThrow(new RuntimeException("No existen socios"));


        assertThrows(
                RuntimeException.class,
                () -> controller.obtenerPorSucursal("VALDIVIA")
        );
    }

    @Test
    @DisplayName("Código 200 - Buscar socios inactivos")
    void obtenerInactivosOk(){

        LocalDate fecha = LocalDate.of(2026,6,1);

        socio.setUltimoAcceso(
                LocalDate.of(2026,5,1)
        );


        when(service.obtenerSociosInactivos(fecha))
                .thenReturn(List.of(socio));


        ResponseEntity<List<socioModel>> response =
                controller.obtenerInactivos(fecha);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Código 404 - No existen socios inactivos")
    void obtenerInactivosNotFound(){

        LocalDate fecha = LocalDate.of(2026,6,1);

        when(service.obtenerSociosInactivos(fecha))
                .thenThrow(new RuntimeException("No existen socios inactivos"));


        assertThrows(
                RuntimeException.class,
                () -> controller.obtenerInactivos(fecha)
        );
    }

    // 201 CREATED
    @Test
    @DisplayName("Código 201 - Crear socio correctamente")
    void crearSocioOk() {
        when(service.crearSocio(dto)).thenReturn(socio);

        ResponseEntity<socioModel> response = controller.crearSocio(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("12345678-9", response.getBody().getRut());
    }

    // 400 BAD REQUEST (simulación de error de validación/lógica)
    @Test
    @DisplayName("Código 400 - Error de datos inválidos")
    void crearSocioBadRequest() {
        when(service.crearSocio(dto))
                .thenThrow(new IllegalArgumentException("Datos inválidos"));

        assertThrows(
                IllegalArgumentException.class,
                () -> controller.crearSocio(dto)
        );
    }

    // 404 NOT FOUND
    @Test
    @DisplayName("Código 404 - Socio no encontrado")
    void obtenerSocioNotFound() {
        when(service.obtenerSocioPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<socioModel> response = controller.obtenerPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // 500 INTERNAL SERVER ERROR
    @Test
    @DisplayName("Código 500 - Error interno del servidor")
    void errorInternoServidor() {
        when(service.obtenerTodosLosSocios())
                .thenThrow(new RuntimeException("Error interno"));

        assertThrows(
                RuntimeException.class,
                () -> controller.obtenerTodos()
        );
    }

    // PATCH 200
    @Test
    @DisplayName("Código 200 - Actualizar estado")
    void patchEstadoOk() {
        when(service.actualizaEstado(1L, "INACTIVO"))
                .thenReturn(Optional.of(socio));

        ResponseEntity<socioModel> response =
                controller.actualizarEstado(1L, Map.of("estado", "INACTIVO"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // PATCH 404
    @Test
    @DisplayName("Código 404 - No se puede actualizar estado")
    void patchEstadoNotFound() {
        when(service.actualizaEstado(99L, "INACTIVO"))
                .thenReturn(Optional.empty());

        ResponseEntity<socioModel> response =
                controller.actualizarEstado(99L, Map.of("estado", "INACTIVO"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}