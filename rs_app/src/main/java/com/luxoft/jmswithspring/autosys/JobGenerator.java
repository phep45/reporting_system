package com.luxoft.jmswithspring.autosys;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class JobGenerator {


    private File autosysPropertiesDir;
    private File templatesDir;
    private String outputDir;

    public JobGenerator(String autosysPropertiesDir, String templatesDir, String outputDir) {
        this.autosysPropertiesDir = new File(autosysPropertiesDir);
        this.templatesDir = new File(templatesDir);
        this.outputDir = outputDir;
    }

    public void generate() throws IOException {

        for(File property : autosysPropertiesDir.listFiles()) {
            for(File template : templatesDir.listFiles()) {
                generateJob(property, template);
            }
        }

    }

    public void generateJob(File property, File template) throws IOException {
        Preconditions.checkArgument(property.isFile() && template.isFile());

        String[] properties = FileUtils.readFileToString(property).split("\n");
        String env = "";

        String str = FileUtils.readFileToString(template);

        for(String line : properties) {
            String name = line.split("=")[0];
            String value = line.split("=")[1];

            str = str.replaceAll("\\$\\{" + name + "\\}", value);

            if ("ENV".equals(name)) {
                env = value;
            }
        }

        FileUtils.writeStringToFile(new File(outputDir+"/"+env.toLowerCase()+"/"+template.getName()+env+".jil"), str);

    }

}