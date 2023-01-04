package ru.drugsdomain.MasterParser.coldstart.file_read;

import java.net.URL;
import java.util.List;

public interface IDrugsNamesReader {
    List<String> readDataFrom(URL fullName);
}
