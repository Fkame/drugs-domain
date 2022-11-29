package ru.drugsdomain.SlaveParser.medical_dict.parser;

import org.springframework.stereotype.Component;
import ru.drugsdomain.SlaveParser.DrugParamsDto;

import java.util.List;

@Component
public class ParsedDataWrapMapper {

    public DrugParamsDto map(ParsedDataWrap wrap) {

        DrugParamsDto dto = new DrugParamsDto();
        dto.setDrugName(wrap.getDrugName());
        dto.setManualRef(wrap.getManualRef());
        dto.setProdCompany(wrap.getProdCompany());
        dto.setProdCountry(wrap.getProdCountry());
        dto.setReleaseForm(List.of(wrap.getReleaseForm()));
        dto.setActiveSubstances(wrap.getActiveSubstances());
        dto.setPurposes(wrap.getPurposes());
        dto.setNeedRecipe(wrap.getNeedRecipe());
        return dto;
    }

    public ParsedDataWrap mapNullToEmpty(ParsedDataWrap wrap) {
        return ParsedDataWrap.builder()
                .drugName(wrap.getDrugName() == null ? "" : wrap.getDrugName())
                .prodCountry(wrap.getProdCountry() == null ? "" : wrap.getProdCountry())
                .prodCompany(wrap.getProdCompany() == null ? "" : wrap.getProdCompany())
                .releaseForm(wrap.getReleaseForm() == null ? "" : wrap.getReleaseForm())
                .purposes(wrap.getPurposes() == null ? List.of() : wrap.getPurposes())
                .activeSubstances(wrap.getActiveSubstances() == null ? List.of() : wrap.getActiveSubstances())
                .manualRef((wrap.getManualRef() == null ? "" : wrap.getManualRef()))
                .needRecipe(wrap.getNeedRecipe())
                .build();
    }
}
