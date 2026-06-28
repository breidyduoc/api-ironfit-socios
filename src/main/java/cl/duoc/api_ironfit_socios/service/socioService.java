package cl.duoc.api_ironfit_socios.service;

import cl.duoc.api_ironfit_socios.dto.socioDTO;
import cl.duoc.api_ironfit_socios.model.socioModel;
import cl.duoc.api_ironfit_socios.repository.socioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class socioService {

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

    public Optional<socioModel> obtenerSocioPorEstado(String estado){
        return repository.findByEstado(estado);
    }

    public Optional<socioModel> obtenerSocioPorEdad(Integer edad){
        return repository.findByEdad(edad);
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
}