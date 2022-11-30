package ru.drugsdomain.MasterParser.core.drug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrugRepository extends JpaRepository<Drug, Long> {

    @Query(nativeQuery = true, value = "" +
            "SELECT count(*) from drug;"
    )
    int countAll();
}
