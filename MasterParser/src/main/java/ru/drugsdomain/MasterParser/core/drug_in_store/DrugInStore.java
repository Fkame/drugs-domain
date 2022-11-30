package ru.drugsdomain.MasterParser.core.drug_in_store;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.drugsdomain.MasterParser.core.drug_store.DrugStore;
import ru.drugsdomain.MasterParser.core.drug.Drug;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "drug_in_store")
@Data
@NoArgsConstructor
public class DrugInStore {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "drug_id", referencedColumnName = "id")
    private Drug drug;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private DrugStore drugStore;

    private BigDecimal price;

    private String url;

}
