package ru.drugsdomain.MasterParser.service.drug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrugRepository extends JpaRepository<DrugEntity, Long> {

    @Query(nativeQuery = true, value = "" +
            "SELECT count(*) from drug;"
    )
    int countAll();
}
