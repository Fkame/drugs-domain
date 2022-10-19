package ru.drugsdomain.SlaveParser.medicaldict;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MedicalDictParseControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MedicalDictParseController()).build();
    }

    private List<String> inputNames = List.of("Депакин Хроно", "Аскорбиновая кислота", "Боярышник");

    @Test
    @SneakyThrows
    public void test() {
        ObjectMapper mapper = new ObjectMapper();
        String inputJson = mapper.writeValueAsString(inputNames);
        mockMvc.perform(post("/drugs-from-dict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().is2xxSuccessful());
    }
}
