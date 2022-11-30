package ru.drugsdomain.MasterParser.core.active_substance;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.MasterParser.core.drug.Drug;
import ru.drugsdomain.MasterParser.core.substance.Substance;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class ActiveSubstanceCommandService {

    private final ActiveSubstanceRepository activeSubstanceRepository;

    public Collection<ActiveSubstance> saveActiveSubstances(Drug drug, Collection<Substance> substances) {
        List<ActiveSubstance> activeSubstances = new ArrayList<>();
        for (Substance substance : substances) {
            ActiveSubstance activeSubstance = new ActiveSubstance();
            activeSubstance.setDrug(drug);
            activeSubstance.setSubstance(substance);

            activeSubstances.add(activeSubstanceRepository.save(activeSubstance));
        }
        return activeSubstances;
    }
}
