package ru.drugsdomain.MasterParser.core.drug;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class DrugQueryService {

    private final DrugRepository drugRepository;

    public int getCountAll() {
        return drugRepository.countAll();
    }

    public Collection<Drug> getByIds(Collection<Long> ids) {
        return drugRepository.findAllById(ids);
    }

}
