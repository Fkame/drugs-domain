package ru.drugsdomain.MasterParser.coldstart.file_read;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DrugInfoJsonDto {
    @JsonAlias("normativedocumentation")
    private String nameContainer;
}
