package cl.duoc.api_ironfit_socios.service;

import cl.duoc.api_ironfit_socios.dto.estadoFinancieroDTO;
import cl.duoc.api_ironfit_socios.dto.socioDTO;
import cl.duoc.api_ironfit_socios.model.socioModel;
import cl.duoc.api_ironfit_socios.repository.socioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class socioService {

    private final RestTemplate restTemplate;
    private final socioRepository repository;

    private void mapearDtoAModel(socioDTO dto, socioModel socio) {
        socio.setRut(dto.getRut());
        socio.setNombre(dto.getNombre());
        socio.setApellido(dto.getApellido());
        socio.setEdad(dto.getEdad());
        socio.setEstado(dto.getEstado());
        socio.setUltimoAcceso(dto.getUltimoAcceso());
        socio.setSucursal(dto.getSucursal());
    }

    public List<socioModel> obtenerTodosLosSocios() {
        return repository.findAll();
    }

    public Optional<socioModel> obtenerSocioPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<socioModel> obtenerSocioPorRut(String rut) {
        return repository.findByRut(rut);
    }

    public List<socioModel> obtenerSocioPorEstado(String estado){
        return repository.findByEstado(estado);
    }

    public List<socioModel> obtenerSocioPorEdad(Integer edad){
        return repository.findByEdad(edad);
    }

    public List<socioModel> obtenerSociosPorSucursal(String sucursal){

        List<socioModel> socios = repository.findBySucursal(sucursal);

        if(socios.isEmpty()){
            throw new IllegalStateException(
                    "No existen socios registrados en la sucursal: " + sucursal
            );
        }

        return socios;
    }

    public List<socioModel> obtenerSociosInactivos(LocalDate fecha){

        List<socioModel> socios =
                repository.findByUltimoAccesoBefore(fecha);

        if(socios.isEmpty()){
            throw new IllegalStateException(
                    "No existen socios inactivos desde la fecha indicada"
            );
        }

        return socios;
    }

    @Transactional
    public socioModel crearSocio(socioDTO dto) {
        socioModel socio = new socioModel();
        mapearDtoAModel(dto, socio);

        return repository.save(socio);
    }

    @Transactional
    public Optional<socioModel> actualizaSocio(Long id, socioDTO dto) {
        return repository.findById(id).map(socio -> {
            mapearDtoAModel(dto, socio);
            return repository.save(socio);
        });
    }

    @Transactional
    public Optional<socioModel> actualizaEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(socio -> {
            socio.setEstado(nuevoEstado);
            return repository.save(socio);
        });
    }

    @Transactional
    public boolean borrarSocio(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public estadoFinancieroDTO obtenerEstadoFinanciero(String rut){

        return restTemplate.getForObject(
                "http://localhost:21501/api/v4/pagos/socio/"
                        + rut
                        + "/estado",
                estadoFinancieroDTO.class
        );

    }
}