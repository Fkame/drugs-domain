package ru.drugsdomain.MasterParser.coldstart;

import java.net.URL;
import java.util.List;

public interface IDrugsNameReader {
    List<String> readDataFrom(URL fullName);
}
