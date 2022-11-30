package ru.drugsdomain.MasterParser.core.drug_store;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.drugsdomain.MasterParser.core.drug_in_store.DrugInStore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drugstore")
@NoArgsConstructor
@Data
public class DrugStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String storeName;
    private String url;

    @OneToMany(mappedBy = "drugStore", fetch = FetchType.LAZY)
    private List<DrugInStore> drugsInStore;
}
