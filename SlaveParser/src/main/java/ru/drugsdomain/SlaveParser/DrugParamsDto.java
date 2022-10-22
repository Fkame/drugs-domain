package ru.drugsdomain.SlaveParser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrugParamsDto {

    private String drugName;
    private String prodCountry;
    private String prodCompany;
    private String releaseForm;
    private String purpose;
    private Boolean needRecipe;
    private String manualRef;
    private String activeSubstance;
}
