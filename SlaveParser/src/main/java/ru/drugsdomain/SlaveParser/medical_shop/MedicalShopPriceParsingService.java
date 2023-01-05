package ru.drugsdomain.SlaveParser.medical_shop;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.drugsdomain.SlaveParser.medical_shop.parser.IPriceParser;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class MedicalShopPriceParsingService {

    //@Autowired
    //@Qualifier("planetaZdorovia")
    //private IPriceParser planetaZdoroviaParser;

    @Autowired
    @Qualifier("gorzdrav")
    private IPriceParser gorzdravParser;


    public List<StoreDrugWrap> parsePrice(String drugName) {
        List<StoreDrugWrap> drugsInStores = new ArrayList<>();
        //drugsInStores.addAll(planetaZdoroviaParser.parseByName(drugName));

        drugsInStores.addAll(gorzdravParser.parseByName(drugName));

        return drugsInStores;
    }
}
