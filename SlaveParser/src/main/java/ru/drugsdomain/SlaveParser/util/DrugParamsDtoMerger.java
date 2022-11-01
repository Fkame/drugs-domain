package ru.drugsdomain.SlaveParser.util;

import org.springframework.stereotype.Component;
import ru.drugsdomain.SlaveParser.DrugParamsDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class DrugParamsDtoMerger {
    public DrugParamsDto mergeByName(DrugParamsDto original, DrugParamsDto duplicate) {
        String drugName = original.getDrugName();
        String manualRef = original.getManualRef();

        String prodCountry = original.getProdCountry();
        if (prodCountry == null && duplicate.getProdCountry() != null) {
            prodCountry = duplicate.getProdCountry();
        }

        String prodCompany = original.getProdCompany();
        if (prodCompany == null && duplicate.getProdCompany() != null) {
            prodCompany = duplicate.getProdCompany();
        }

        Boolean needRecipe = original.getNeedRecipe();
        if (needRecipe == null && duplicate.getNeedRecipe() != null) {
            needRecipe = duplicate.getNeedRecipe();
        }

        List<String> releaseForm = new ArrayList<>(original.getReleaseForm());
        releaseForm.addAll(duplicate.getReleaseForm());
        releaseForm = releaseForm.stream().distinct().toList();

        List<String> purposes = new ArrayList<>(original.getPurposes());
        purposes.addAll(duplicate.getPurposes());
        purposes = purposes.stream().distinct().toList();

        List<String> activeSubstances = new ArrayList<>(original.getActiveSubstances());
        activeSubstances.addAll(duplicate.getActiveSubstances());
        activeSubstances = activeSubstances.stream().distinct().toList();

        DrugParamsDto dto = new DrugParamsDto();
        dto.setDrugName(drugName);
        dto.setManualRef(manualRef);
        dto.setProdCompany(prodCompany);
        dto.setProdCountry(prodCountry);
        dto.setReleaseForm(releaseForm);
        dto.setActiveSubstances(activeSubstances);
        dto.setPurposes(purposes);
        dto.setNeedRecipe(needRecipe);
        return dto;
    }

}
