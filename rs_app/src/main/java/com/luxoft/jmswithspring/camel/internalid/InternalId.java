package com.luxoft.jmswithspring.camel.internalid;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class InternalId {
    private static final Logger log = LoggerFactory.getLogger(InternalId.class);
    private static final File DEFAULT_INTERNAL_ID_FILE = new File("src/main/resources/internal_id");
    private static final int INTERSPACE = 100;

    private final File internalIdFile;
    private int uniqueId;

    public InternalId() throws IOException {
        this.internalIdFile = DEFAULT_INTERNAL_ID_FILE;
        uniqueId = Integer.parseInt(FileUtils.readFileToString(internalIdFile));
    }

    public InternalId(File internalIdFile) throws IOException {
        this.internalIdFile = internalIdFile;
        uniqueId = Integer.parseInt(FileUtils.readFileToString(internalIdFile));
    }

    public void increment() {
        uniqueId++;
        log.info("Internal ID incremented to {}", uniqueId);
        if(interspaceReached()) {
            updateInternalIdFile();
        }
    }

    public void updateInternalIdFile() {
        try {
            FileUtils.write(internalIdFile, String.valueOf(uniqueId));
            log.info("Internal ID file updated.");
        } catch (IOException e) {
            log.error("Cannot write to file: {}", internalIdFile.getPath());
            throw new IllegalStateException(e);
        }
    }

    private boolean interspaceReached() {
        return uniqueId % INTERSPACE == 0;
    }

    public int getUniqueId() {
        return uniqueId;
    }
}
