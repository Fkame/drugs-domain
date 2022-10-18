package ru.drugsdomain.MasterParser.coldstart;

import java.net.URL;
import java.util.List;

public interface IDrugsInfoReader {
    List<DrugInfo> readDataFrom(URL fullName);
}
