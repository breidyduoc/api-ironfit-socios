package cl.duoc.api_socios.serviceTest;

import cl.duoc.api_ironfit_socios.dto.socioDTO;
import cl.duoc.api_ironfit_socios.model.socioModel;
import cl.duoc.api_ironfit_socios.repository.socioRepository;
import cl.duoc.api_ironfit_socios.service.socioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SocioServiceTest {

    @Mock
    private socioRepository repository;

    @InjectMocks
    private socioService service;

    private socioModel socio;

    @BeforeEach
    void setup() {
        socio = new socioModel();
        socio.setId(1L);
        socio.setRut("12345678-9");
        socio.setNombre("Juan");
        socio.setApellido("Perez");
        socio.setEdad(20);
        socio.setEstado("ACTIVO");
    }

    // 200 OK
    @Test
    @DisplayName("Obtener todos los socios")
    void getAllSocios() {

        when(repository.findAll()).thenReturn(List.of(socio));

        List<socioModel> result = service.obtenerTodosLosSocios();

        assertEquals(1, result.size());
        assertEquals("12345678-9", result.getFirst().getRut());

        verify(repository, times(1)).findAll();
    }

    // 200 OK por ID
    @Test
    @DisplayName("Buscar socio por ID encontrado")
    void getByIdOk() {

        when(repository.findById(1L)).thenReturn(Optional.of(socio));

        Optional<socioModel> result = service.obtenerSocioPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
    }

    // 404 lógico (Optional vacío)
    @Test
    @DisplayName("Buscar socio por ID no encontrado")
    void getByIdNotFound() {

        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<socioModel> result = service.obtenerSocioPorId(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Buscar socios por sucursal correctamente")
    void getBySucursalOk(){

        socio.setSucursal("SAN_BERNARDO");

        when(repository.findBySucursal("SAN_BERNARDO"))
                .thenReturn(List.of(socio));

        List<socioModel> result =
                service.obtenerSociosPorSucursal("SAN_BERNARDO");

        assertEquals(1, result.size());
        assertEquals(
                "SAN_BERNARDO",
                result.getFirst().getSucursal()
        );

        verify(repository)
                .findBySucursal("SAN_BERNARDO");
    }

    @Test
    @DisplayName("Buscar sucursal sin socios devuelve error")
    void getBySucursalNotFound(){

        when(repository.findBySucursal("VALDIVIA"))
                .thenReturn(List.of());

        assertThrows(
                IllegalStateException.class,
                () -> service.obtenerSociosPorSucursal("VALDIVIA")
        );
    }

    @Test
    @DisplayName("Buscar socios inactivos correctamente")
    void getSociosInactivosOk(){

        socio.setUltimoAcceso(
                LocalDate.of(2026,5,1)
        );

        when(repository.findByUltimoAccesoBefore(
                LocalDate.of(2026,6,1)
        ))
                .thenReturn(List.of(socio));


        List<socioModel> result =
                service.obtenerSociosInactivos(
                        LocalDate.of(2026,6,1)
                );


        assertEquals(1,result.size());
        assertEquals(
                LocalDate.of(2026,5,1),
                result.getFirst().getUltimoAcceso()
        );
    }

    @Test
    @DisplayName("Sin socios inactivos genera error")
    void getSociosInactivosNotFound(){

        when(repository.findByUltimoAccesoBefore(
                LocalDate.of(2026,6,1)
        ))
                .thenReturn(List.of());


        assertThrows(
                IllegalStateException.class,
                () -> service.obtenerSociosInactivos(
                        LocalDate.of(2026,6,1)
                )
        );
    }

    // 201 CREATE
    @Test
    @DisplayName("Crear socio correctamente")
    void createSocio() {

        socioDTO dto = new socioDTO();

        dto.setRut("12345678-9");
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setEdad(20);
        dto.setEstado("ACTIVO");

        when(repository.save(any(socioModel.class)))
                .thenReturn(socio);

        socioModel result = service.crearSocio(dto);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    // DELETE OK
    @Test
    @DisplayName("Eliminar socio existente")
    void deleteSocioOk() {

        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        boolean result = service.borrarSocio(1L);

        assertTrue(result);
        verify(repository).deleteById(1L);
    }

    // DELETE FAIL
    @Test
    @DisplayName("Eliminar socio inexistente")
    void deleteSocioFail() {

        when(repository.existsById(99L)).thenReturn(false);

        boolean result = service.borrarSocio(99L);

        assertFalse(result);
    }
}