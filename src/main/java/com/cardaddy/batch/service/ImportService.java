package com.cardaddy.batch.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ImportService {

    public InputStream getZipData(ZipInputStream zip, String filename) throws FileNotFoundException, IOException {
        for (ZipEntry e; (e = zip.getNextEntry()) != null;) {
            if (e.getName().equals(filename)) {
                return zip;
            }
        }
        throw new FileNotFoundException("zip://" + filename);
    }

}
