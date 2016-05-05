package com.luxoft.jmswithspring.camel.internalid;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InternalIdTestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class IntIdControllerTest {

    @Autowired
    private IntIDController idController;

    @Ignore
    @Test
    public void shouldIncrementEveryFiveSeconds() throws IOException, InterruptedException {
        idController.updateId();


        Thread.sleep(6000);

        idController.updateId();
        idController.updateId();
        idController.updateId();

        Thread.sleep(6000);

        System.out.println(FileUtils.readFileToString(new File("src/main/resources/internal_id")));
        System.out.println(idController.checkCurrentId());
    }

}
