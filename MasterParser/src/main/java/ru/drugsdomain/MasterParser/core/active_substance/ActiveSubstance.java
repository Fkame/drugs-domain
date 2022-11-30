package ru.drugsdomain.MasterParser.core.active_substance;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.drugsdomain.MasterParser.core.drug.Drug;
import ru.drugsdomain.MasterParser.core.substance.Substance;

import javax.persistence.*;

@Entity
@Table(name = "active_substance")
@Data
@NoArgsConstructor
public class ActiveSubstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "drug_id", referencedColumnName = "id")
    private Drug drug;

    @ManyToOne
    @JoinColumn(name = "substance_id", referencedColumnName = "id")
    private Substance substance;
}
