package ru.drugsdomain.SlaveParser.medical_shop;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StoreDrugWrap {
    private String url;
    private String drugName;
    private BigDecimal price;
    private String originName;
}
