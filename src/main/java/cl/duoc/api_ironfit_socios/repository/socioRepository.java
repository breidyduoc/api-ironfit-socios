package cl.duoc.api_ironfit_socios.repository;

import cl.duoc.api_ironfit_socios.model.socioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
@Repository
public interface socioRepository extends JpaRepository<socioModel, Long> {

    Optional<socioModel> findByRut(String rut);
    List<socioModel> findByEstado(String estado);
    List<socioModel> findByEdad(Integer edad);
    List<socioModel> findBySucursal(String sucursal);
    List<socioModel> findByUltimoAccesoBefore(LocalDate fecha);

}