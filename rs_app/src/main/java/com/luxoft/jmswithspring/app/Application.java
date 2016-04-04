package com.luxoft.jmswithspring.app;

import com.luxoft.jmswithspring.config.OperationsConfig;
import com.luxoft.jmswithspring.exceptions.CorruptedDataException;
import com.luxoft.jmswithspring.manager.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Queue;

@Component
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private DataManager dataManager;

    @Autowired
    @Resource(name = "slisQueue")
    private Queue<String> slisQueue;

    @Autowired
    @Resource(name = "xlisQueue")
    private Queue<String> xlisQueue;

    public void run() throws InterruptedException {
//        String slis = "000000000200001     Stiven Meckalov   BUYUS0009020020000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500\n" +
//                "000000000400021            Lu Cheng  SELLGB0001122220000000170000000887.01200000402/13/2015002560000000180000001033.00200001502/13/2015094400000000190000001033.00200001502/13/201509500\n" +
//                "000000000500001     Stiven MeckalovMOVE_OUS0001122220000005050000000020.00000000202/13/201500123\n" +
//                "000000000800301     Adam Nowak     CANCELEU0002224420000005050000000330.00005400206/23/201600901";


        dataManager.createDatabase();
        log.info("Tables created");

        while(true) {
            String slis = slisQueue.poll();
            if(slis!=null) {
                log.info("SLIS: {}", slis);
                dataManager.processSLIS(slis);
            }
            String xlis = xlisQueue.poll();
            if(xlis!=null) {
                log.info("Xlis received");
                dataManager.processXLIS(xlis);
            }
        }
    }

    public static void main(String[] args) throws IOException, CorruptedDataException, InterruptedException {
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OperationsConfig.class);

        Application application = (Application) applicationContext.getBean("application");
        application.run();

        applicationContext.close();
    }

}
