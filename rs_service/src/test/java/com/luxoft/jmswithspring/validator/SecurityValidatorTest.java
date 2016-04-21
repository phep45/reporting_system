package com.luxoft.jmswithspring.validator;


import com.luxoft.jmswithspring.model.Security;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ValidatorTestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class SecurityValidatorTest {

    @Autowired
    private Validator<Security> securityValidator;

    @Test
    public void shouldAllow() {
        Security allowedSecurity = allowedSecurity();

        assertTrue(securityValidator.validate(allowedSecurity));
    }

    @Test
    public void shouldNotAllow() {
        Security forbiddenSecurity = forbiddenSecurity();

        assertFalse(securityValidator.validate(forbiddenSecurity));
    }

    private Security forbiddenSecurity() {
        return Security.builder()
                .withId(2)
                .withSecurityId(500)
                .withSecurityId(600)
                .withSecurityId(700)
                .withSecurityId(800)
                .build();
    }

    private Security allowedSecurity() {
        return Security.builder()
                .withId(1)
                .withSecurityId(100)
                .withSecurityId(200)
                .withSecurityId(300)
                .withSecurityId(400)
                .build();
    }

}
