package cl.duoc.api_socios.modelTest;

import cl.duoc.api_ironfit_socios.model.socioModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SocioModelTest {
    private static Validator valid;

    @BeforeAll
    static void initValidation() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            valid = factory.getValidator();
        }
    }

    // 200 OK - modelo válido
    @Test
    @DisplayName("Socio válido pasa validación")
    void socioValido() {

        socioModel s = new socioModel();
        s.setRut("12345678-9");
        s.setNombre("Juan");
        s.setApellido("Perez");
        s.setEdad(20);
        s.setEstado("ACTIVO");

        Set<ConstraintViolation<socioModel>> violations = valid.validate(s);

        assertTrue(violations.isEmpty());
    }

    // 400 BAD REQUEST - edad inválida
    @Test
    @DisplayName("Socio inválido falla validación")
    void socioInvalido() {

        socioModel s = new socioModel();
        s.setRut("12345678-9");
        s.setNombre("Juan");
        s.setApellido("Perez");
        s.setEdad(10); // inválido si mínimo es 15
        s.setEstado("ACTIVO");

        Set<ConstraintViolation<socioModel>> violations = valid.validate(s);

        assertFalse(violations.isEmpty());
    }
}