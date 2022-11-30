package ru.drugsdomain.MasterParser.core.drug;

import com.sun.istack.NotNull;
import lombok.*;
import ru.drugsdomain.MasterParser.core.active_substance.ActiveSubstance;
import ru.drugsdomain.MasterParser.core.drug_in_store.DrugInStore;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "drug")
@NoArgsConstructor
public class Drug {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String drugName;
    private String prodCountry;
    private String prodCompany;
    private String releaseForm;
    private String purpose;
    private Boolean needRecipe;
    private String manualRef;

    @Setter(AccessLevel.NONE)
    private OffsetDateTime updateTime;

    @OneToMany(mappedBy = "drug", fetch = FetchType.LAZY)
    private List<DrugInStore> drugInStores;

    @OneToMany(mappedBy = "drug", fetch = FetchType.LAZY)
    private List<ActiveSubstance> activeSubstances;
}
