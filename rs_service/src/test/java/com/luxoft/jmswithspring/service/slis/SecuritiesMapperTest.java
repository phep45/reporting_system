package com.luxoft.jmswithspring.service.slis;

import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.model.Security;
import com.luxoft.jmswithspring.service.slis.SecurityMapper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SecuritiesMapperTest {

    private SecurityMapper securityMapper;
    private List<Security> expectedSecurities = Arrays.asList(
            Security.builder().withLotId(130).withPrice(BigDecimal.valueOf(1233.002).setScale(5, BigDecimal.ROUND_HALF_UP)).withAmount(2).withDate("02/12/2015").withProductId(122).build(),
            Security.builder().withLotId(140).withPrice(BigDecimal.valueOf(1033.002).setScale(5, BigDecimal.ROUND_HALF_UP)).withAmount(15).withDate("02/12/2015").withProductId(9500).build()
    );

    private static final String INPUT = "0000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500";
    private static final String CORRUPTED_INPUT = "0000000130000001233.00200000202/12/20150012BAAAA000140000001033.00200001502/12/201509500";
    private static final String TOO_SHORT_INPUT = "0000000130000001233.0";

    @Before
    public void setUp() {
        securityMapper = new SecurityMapper();
    }

    @Test
    public void shouldReturnSecurities() throws CorruptedDataException {
        List<Security> result = securityMapper.map(INPUT);
        assertEquals(expectedSecurities, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowTooShortInput() throws CorruptedDataException {
        securityMapper.map(TOO_SHORT_INPUT);
    }

    @Test(expected = CorruptedDataException.class)
    public void shouldThrowException() throws CorruptedDataException {
        securityMapper.map(CORRUPTED_INPUT);
    }
}
