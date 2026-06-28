package cl.duoc.api_ironfit_socios.repository;

import cl.duoc.api_ironfit_socios.model.socioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface socioRepository extends JpaRepository<socioModel, Long> {

    Optional<socioModel> findByRut(String rut);
    Optional<socioModel> findByEstado(String estado);
    Optional<socioModel> findByEdad(Integer edad);

}