package com.luxoft.jmswithspring.service;

import com.google.common.base.Preconditions;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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

    public Security map(String securityAsString) throws CorruptedDataException {
        Preconditions.checkArgument(securityAsString.length() == VALID_LENGTH, "Invalid input. String should be "+VALID_LENGTH+" characters long.");

        Security sec = null;

        try {
            BigDecimal lotId = new BigDecimal(securityAsString.substring(LOT_ID_BEGIN, LOT_ID_END).trim());
            BigDecimal price = new BigDecimal(securityAsString.substring(PRICE_BEGIN, PRICE_END).trim());
            BigDecimal amount = new BigDecimal(securityAsString.substring(AMOUNT_BEGIN, AMOUNT_END).trim());
            String date = securityAsString.substring(DATE_BEGIN, DATE_END).trim();
            BigDecimal productId = new BigDecimal(securityAsString.substring(PRODUCT_ID_BEGIN).trim());

            sec = new Security(lotId, price, amount, date, productId);
        } catch (NumberFormatException e) {
            log.info("Data < {} > corrupted.", securityAsString);
            throw new CorruptedDataException("Corrupted data: " + securityAsString, e);
        }

        return sec;
    }

}
