package ru.drugsdomain.SlaveParser.medicaldict.parser;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.drugsdomain.SlaveParser.DrugParamsDto;
import ru.drugsdomain.SlaveParser.util.SeleniumService;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LsgeotarParserTests {

    @MockBean
    private SeleniumService seleniumService;

    @Mock
    private WebElement webElement;

    @Autowired
    private LsgeotarParser lsgeotarParser;

    private String testHtmlPath = "/lsgeotar_depakine.html";

    private List<String> drugsNames = List.of("Depakine", "Valpo", "Billys");
    private List<String> drugsNamesOne = List.of("Depakine");

    @Test
    @SneakyThrows
    public void testParseByNames() {
        List<WebElement> stubWebElements = List.of(webElement, webElement, webElement);

        Mockito.when(seleniumService.findElements(Mockito.anyString()))
                .thenAnswer((event) -> stubWebElements);
        Mockito.when(seleniumService.waitForUiElementsVisible(Mockito.anyString()))
                .thenAnswer((event) -> stubWebElements);

        URL path = LsgeotarParserTests.class.getResource(testHtmlPath);
        String testHtml = String.join("\n", Files.readAllLines(Path.of(path.toURI())));
        Mockito.when(seleniumService.getPageSource()).thenReturn(testHtml);

        List<DrugParamsDto> drugParamsDtos = lsgeotarParser.parseByNames(drugsNamesOne);

        assertThat(drugParamsDtos).isNotNull();
        assertThat(drugParamsDtos.size() > 0).isTrue();
        for (int i = 0; i < drugsNamesOne.size(); i++) {
            assertThat(drugParamsDtos.get(i).getDrugName()).isEqualTo(drugsNamesOne.get(i));
        }
    }
}