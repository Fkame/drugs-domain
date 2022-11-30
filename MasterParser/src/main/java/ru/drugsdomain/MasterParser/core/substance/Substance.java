package ru.drugsdomain.MasterParser.core.substance;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.drugsdomain.MasterParser.core.active_substance.ActiveSubstance;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "substance")
@NoArgsConstructor
@Data
public class Substance {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String substanceName;

    @OneToMany(mappedBy = "substance", fetch = FetchType.LAZY)
    private List<ActiveSubstance> activeSubstances;
}
