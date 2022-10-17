package ru.drugsdomain.MasterParser.service.drug;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class DrugQueryService {

    private final DrugRepository drugRepository;

    public int getCountAll() {
        return drugRepository.countAll();
    }

}
