package ru.drugsdomain.MasterParser.coldstart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ColdStartInfoDto {

    private String drugName;
    private String prodCountry;
    private String prodCompany;
    private List<String> releaseForm;
    private List<String> purposes;
    private Boolean needRecipe;
    private String manualRef;
    private List<String> activeSubstances;
}
