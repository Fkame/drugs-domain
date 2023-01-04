package ru.drugsdomain.MasterParser.core.drug;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class DrugQueryService {

    private final DrugRepository drugRepository;

    public List<Drug> findAll() {
        return drugRepository.findAll();
    }

    public int getCountAll() {
        return drugRepository.countAll();
    }

    public List<Drug> getByIds(List<Long> ids) {
        return drugRepository.findAllById(ids);
    }
}
