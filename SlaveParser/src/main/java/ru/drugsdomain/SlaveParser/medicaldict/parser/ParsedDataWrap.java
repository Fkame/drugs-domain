package ru.drugsdomain.SlaveParser.medicaldict.parser;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ParsedDataWrap {

    private String drugName;
    private String prodCountry;
    private String prodCompany;
    private String releaseForm;
    private List<String> purposes;
    private Boolean needRecipe;
    private String manualRef;
    private List<String> activeSubstances;
}
