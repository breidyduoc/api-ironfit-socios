package cl.duoc.api_ironfit_socios.dto;

import lombok.Data;

@Data
public class estadoFinancieroDTO {
    private String rut;
    private boolean poseeDeuda;
    private String estadoF;
}