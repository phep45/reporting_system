package com.luxoft.jmswithspring.service.slis;

import com.google.common.base.Preconditions;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Lot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Component
public class SecurityMapper {
    private static final Logger log = LoggerFactory.getLogger(SecurityMapper.class);

    private static final int LOT_ID_BEGIN = 0;
    private static final int LOT_ID_END = 10;
    private static final int PRICE_BEGIN = 10;
    private static final int PRICE_END = 25;
    private static final int AMOUNT_BEGIN = 25;
    private static final int AMOUNT_END = 29;
    private static final int DATE_BEGIN = 29;
    private static final int DATE_END = 39;
    private static final int PRODUCT_ID_BEGIN = 40;

    private static final int VALID_LENGTH = 44;

    public List<Lot> map(String securitiesAsString) throws CorruptedDataException {
        Preconditions.checkArgument(securitiesAsString.length() >= VALID_LENGTH, "Invalid input. String should not be shorter then " + VALID_LENGTH + " characters.");
        List<Lot> securities = new LinkedList<>();

        List<String> list = splitInput(securitiesAsString);

        for (String str : list) {
            try {
                int lotId = Integer.parseInt(str.substring(LOT_ID_BEGIN, LOT_ID_END).trim());
                BigDecimal price = new BigDecimal(str.substring(PRICE_BEGIN, PRICE_END).trim());
                int amount = Integer.parseInt(str.substring(AMOUNT_BEGIN, AMOUNT_END).trim());
                String date = str.substring(DATE_BEGIN, DATE_END).trim();
                int productId = Integer.parseInt(str.substring(PRODUCT_ID_BEGIN).trim());

                Lot lot = Lot.builder()
                        .withLotId(lotId)
                        .withPrice(price)
                        .withAmount(amount)
                        .withDate(date)
                        .withSecurityId(productId)
                        .build();

                securities.add(lot);

            } catch (NumberFormatException e) {
                log.info("Data < {} > corrupted.", str);
                throw new CorruptedDataException("Corrupted data: " + str, e);
            }


        }

        if(securities.isEmpty())
            throw new CorruptedDataException("No securities found.");

        return securities;
    }

    private List<String> splitInput(String securitiesAsString) throws CorruptedDataException {
        List<String> list = new LinkedList<>();
        String tempString = securitiesAsString;

        while (tempString.length() >= VALID_LENGTH) {
            String str = tempString.substring(0, VALID_LENGTH);
            list.add(str);
            tempString = tempString.replaceAll("(" + str + ")", "");
        }
        if (tempString.length() != 0)
            throw new CorruptedDataException("Corrupted line.", securitiesAsString);
        return list;
    }

}
