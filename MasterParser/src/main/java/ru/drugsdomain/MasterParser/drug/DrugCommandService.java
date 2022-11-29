package ru.drugsdomain.MasterParser.drug;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drugsdomain.MasterParser.coldstart.ColdStartInfoDto;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DrugCommandService {

    private final DrugRepository drugRepository;

    public void saveDrugs(List<ColdStartInfoDto> drugsData) {

    }
}
