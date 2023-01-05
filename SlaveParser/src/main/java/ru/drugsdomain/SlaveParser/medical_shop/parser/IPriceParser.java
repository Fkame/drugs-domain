package ru.drugsdomain.SlaveParser.medical_shop.parser;

import ru.drugsdomain.SlaveParser.medical_shop.StoreDrugWrap;

import java.util.List;

public interface IPriceParser {

    List<StoreDrugWrap> parseByName(String name);
}
