package ru.drugsdomain.MasterParser.core.substance;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class SubstanceCommandService {

    private final SubstanceRepository substanceRepository;

    public Collection<Substance> saveSubstances(Collection<Substance> substances) {
        return substanceRepository.saveAll(substances);
    }
}
