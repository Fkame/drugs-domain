package ru.drugsdomain.MasterParser.drug;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "drug")
@NoArgsConstructor
public class DrugEntity {

    @Id
    @Setter(AccessLevel.NONE)
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
}
