package ru.drugsdomain.MasterParser.coldstart;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DrugInfoJsonDto {

    @JsonAlias("nameregcertificate")
    private String prodCompany;

    private String country;
    private String formrelease;

    @JsonAlias("normativedocumentation")
    private String nameContainer;

    @JsonAlias("pharmacotherapeuticgroup")
    private String purpose;
}
