package ru.drugsdomain.MasterParser.coldstart;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class JsonDrugsInfoReader implements IDrugsInfoReader {

    public List<DrugInfo> readDataFrom(URL fullName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<DrugInfoJsonDto> drugsInfoDtos = this.readJson(mapper, fullName);

        if (drugsInfoDtos == null) {
            return null;
        }

        List<DrugInfo> drugsInfo = new ArrayList<>();
        for (DrugInfoJsonDto jsonDto : drugsInfoDtos) {
            String name = this.extractDrugName(jsonDto.getNameContainer());
            if (name == null) {
                continue;
            }
            DrugInfo drugInfo = DrugInfo.builder()
                    .name(name)
                    .prodCompany(jsonDto.getProdCompany())
                    .prodCountry(jsonDto.getCountry())
                    .purpose(jsonDto.getPurpose())
                    .releaseForm(jsonDto.getFormrelease())
                    .build();

            drugsInfo.add(drugInfo);
        }
        return drugsInfo;
    }

    private String extractDrugName(String container) {
        if (container == null)  {
            return null;
        }
        String[] containerParts = container.split(",");
        if (containerParts.length < 3)  {
            return null;
        }

        String partWithName = containerParts[2];
        String name = partWithName.split("[,\\.;\\(]")[0];
        return name;
    }

    private List<DrugInfoJsonDto> readJson(ObjectMapper mapper, URL fullName) {
        List<DrugInfoJsonDto> drugsInfoDtos = null;
        try {
            drugsInfoDtos = mapper.readValue(fullName, new TypeReference<>(){});
        } catch (DatabindException ex) {
            log.error("Error binding json to " + DrugInfoJsonDto.class + "\n" + ex.getMessage());
        } catch (StreamReadException ex2) {
            log.error("Error reading file " + fullName + "\n" + ex2.getMessage());
        } catch (IOException ex3) {
            log.error("Error reading file " + fullName + "\n" + ex3.getMessage());
        }
        return drugsInfoDtos;
    }

    public String clearQuotes(String str) {
        return str.replace("\"", "");
    }
}
