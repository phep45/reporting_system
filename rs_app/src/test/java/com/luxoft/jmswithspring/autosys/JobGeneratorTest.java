package com.luxoft.jmswithspring.autosys;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class JobGeneratorTest {


    private static final String PROPERTY_FILE_NAME = "job/dev";
    private static final String TEMPLATE_FILE_NAME = "job/template";

    private static final String DEV = "VERY_SERIOUS_AND_IMPORTANT_JOB_DEV\n" +
            "\n" +
            "name:VERY_SERIOUS_AND_IMPORTANT_JOB_DEV\n" +
            "property: very_important_property\n" +
            "location: much_secret\n" +
            "machine: 192.168.100.5\n" +
            "task: so_not_obvious\n" +
            "\n";

    @Before
    public void createFiles() throws IOException {
        String devProperties = "ENV=DEV\n" +
                "MACHINE=192.168.100.5\n";

        FileUtils.writeStringToFile(new File("job/dev"), devProperties);

        String template = "VERY_SERIOUS_AND_IMPORTANT_JOB_${ENV}\n" +
                "\n" +
                "name:VERY_SERIOUS_AND_IMPORTANT_JOB_${ENV}\n" +
                "property: very_important_property\n" +
                "location: much_secret\n" +
                "machine: ${MACHINE}\n" +
                "task: so_not_obvious\n" +
                "\n";

        FileUtils.writeStringToFile(new File("job/template"), template);
    }

    @Test
    public void shouldGenerateJob() throws IOException {

        JobGenerator jobGenerator = new JobGenerator(PROPERTY_FILE_NAME, TEMPLATE_FILE_NAME, "job_test");

        jobGenerator.generateJob(new File(PROPERTY_FILE_NAME), new File(TEMPLATE_FILE_NAME));

        File f = new File("job_test/dev/templateDEV.jil");

        String out = FileUtils.readFileToString(f);

        Assert.assertEquals(out, DEV);

    }

    @After
    public void cleanUp() throws IOException {
        FileUtils.deleteDirectory(new File("job"));
        FileUtils.deleteDirectory(new File("job_test"));
    }

}
