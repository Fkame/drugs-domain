package ru.drugsdomain.MasterParser.core.drug;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class DrugCommandService {

    private final DrugRepository drugRepository;

    public Collection<Drug> saveDrugs(Collection<Drug> drugsData) {
        return drugRepository.saveAll(drugsData);
    }

    public Drug saveDrug(Drug drugData) {
        return drugRepository.save(drugData);
    }
}
