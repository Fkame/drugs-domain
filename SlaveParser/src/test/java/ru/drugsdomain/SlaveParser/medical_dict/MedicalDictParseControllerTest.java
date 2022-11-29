package ru.drugsdomain.SlaveParser.medical_dict;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.drugsdomain.SlaveParser.DrugParamsDto;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicalDictParseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private SeleniumService seleniumService;

    @AfterEach
    public void endSession() {
        seleniumService.quit();
    }

    @Autowired
    private MedicalDictParseController medicalDictParseController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicalDictParseController).build();
    }

    private List<String> inputNames = List.of("Депакин Хроно", "Кето плюс", "Боярышник");

    @Test
    @SneakyThrows
    public void test() {
        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(inputNames);
        MvcResult result = mockMvc.perform(post("/drugs-from-dict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson)
                        .characterEncoding("utf-8"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<DrugParamsDto> drugs = mapper.readValue(content, new TypeReference<List<DrugParamsDto>>() {});
        assertThat(content).isNotNull();

    }
}
