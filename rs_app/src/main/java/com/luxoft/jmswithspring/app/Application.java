package com.luxoft.jmswithspring.app;

import com.luxoft.jmswithspring.config.OperationsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

//        "000000000200001     Stiven Meckalov   BUYUS0009020020000000130000001233.00200000202/12/2015001220000000140000001033.00200001502/12/201509500\n" +
//        "000000000400021            Lu Cheng  SELLGB0001122220000000170000000887.01200000402/13/2015002560000000180000001033.00200001502/13/2015094400000000190000001033.00200001502/13/201509500\n" +
//        "000000000500001     Stiven MeckalovMOVE_OUS0001122220000005050000000020.00000000202/13/201500123\n" +
//        "000000000800301     Adam Nowak     CANCELEU0002224420000005050000000330.00005400206/23/201600901";
//

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OperationsConfig.class);

        //do nothing until 'q' is pressed...
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                if ("q".equals(scanner.nextLine()))
                    break;
            }
        }

        context.close();
    }

}
