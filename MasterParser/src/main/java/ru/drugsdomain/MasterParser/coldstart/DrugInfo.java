package ru.drugsdomain.MasterParser.coldstart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrugInfo {

    private String name;
    private String prodCountry;
    private String prodCompany;
    private String purpose;
    private String releaseForm;
}
