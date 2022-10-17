package ru.drugsdomain.MasterParser.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.drugsdomain.MasterParser.service.ColdStartService;
import ru.drugsdomain.MasterParser.service.drug.DrugQueryService;

import java.util.Arrays;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ColdStartAfterLoad {

    private final ColdStartService coldStartService;
    private final DrugQueryService drugQueryService;

    private final Environment environment;

    public static final String IGNORE_PROFILE = "Test";

    @EventListener
    public void afterContextInitialization(ContextRefreshedEvent event) {
        if (Arrays.stream(environment.getActiveProfiles())
                .toList()
                .contains(IGNORE_PROFILE)) {
            return;
        }
        if (isColdStart()) {
            coldStartService.prepareDataForColdStart();
        }
    }

    private boolean isColdStart() {
        return drugQueryService.getCountAll() == 0;
    }
}
