package ru.drugsdomain.MasterParser.coldstart;

import org.springframework.stereotype.Component;
import ru.drugsdomain.MasterParser.core.drug.Drug;
import ru.drugsdomain.MasterParser.core.substance.Substance;

import java.util.ArrayList;
import java.util.List;

@Component
public class InfoExtractor {

    public Drug extractDrugInfo(ColdStartInfoDto dto) {
        Drug drug = new Drug();
        drug.setDrugName(dto.getDrugName());
        drug.setProdCountry(dto.getProdCountry());
        drug.setProdCompany(dto.getProdCompany());
        String releaseForm = String.join("\n", dto.getReleaseForm());
        drug.setReleaseForm(releaseForm);
        String purposes = String.join("\n", dto.getPurposes());
        drug.setPurpose(purposes);
        drug.setNeedRecipe(dto.getNeedRecipe());
        drug.setManualRef(dto.getManualRef());

        return drug;
    }

    public List<Substance> extractSubstancesInfo(ColdStartInfoDto dto) {
        ArrayList<Substance> substances = new ArrayList();
        for (String substanceStr : dto.getActiveSubstances()) {
            Substance substance = new Substance();
            substance.setSubstanceName(substanceStr);
            substances.add(substance);
        }
        return substances;
    }
}
