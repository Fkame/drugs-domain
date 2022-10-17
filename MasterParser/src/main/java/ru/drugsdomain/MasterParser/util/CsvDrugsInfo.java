package ru.drugsdomain.MasterParser.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CsvDrugsInfo {

    private String name;
    private String prodCountry;
    private String prodCompany;
    private String purpose;
}
