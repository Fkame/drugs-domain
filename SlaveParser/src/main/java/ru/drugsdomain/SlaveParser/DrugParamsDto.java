package ru.drugsdomain.SlaveParser;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DrugParamsDto {

    private String drugName;
    private String prodCountry;
    private String prodCompany;
    private List<String> releaseForm;
    private List<String> purposes;
    private Boolean needRecipe;
    private String manualRef;
    private List<String> activeSubstances;
}
