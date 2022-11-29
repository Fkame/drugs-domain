package ru.drugsdomain.MasterParser.slave_parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.drugsdomain.MasterParser.coldstart.ColdStartInfoDto;

import java.util.List;

@Component
@Slf4j
public class SlaveParserClient {

    public List<ColdStartInfoDto> getDrugInfoFromDictionary(String host, String port,
                                                            String resource,
                                                            List<String> drugsNames) {
        if (drugsNames == null) return null;

        try {
            WebClient client = WebClient.create("http://" + host + ":" + port);
            String responseBody = client.post()
                    .uri(resource)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(drugsNames)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            ObjectMapper mapper = new ObjectMapper();
            List<ColdStartInfoDto> drugsData = mapper.readValue(responseBody, new TypeReference<List<ColdStartInfoDto>>() {});
            return drugsData;
        } catch (Exception ex) {
            log.error("Cannot connect to slave parser: host=[" + host +
                    "], port=[" + port +
                    "], resource=[" + resource +
                    "], namesSize=" + drugsNames.size() +
                    "; error_text=" + ex.getMessage()
            );
            return List.of();
        }
    }
}
