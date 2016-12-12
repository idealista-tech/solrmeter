package com.idealista.solrmeter.view;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class HeadlessUtils {

    public static String fileNameFromTitle(String title) {
        String filename = title.replace(" ", "_").toLowerCase();

        return filename;
    }

    public static File getOutputFile(String titleKey, String baseOutputDir) {
        String title = I18n.get(titleKey);
        File outFile = new File(FilenameUtils.concat(baseOutputDir, fileNameFromTitle(title)));

        if(!outFile.exists()) {
            try {
                outFile.createNewFile();
            }
            catch(IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }

        return outFile;
    }

    public static void outputData(String panelTitleKey, String baseOutputDir, Collection<?> lines) {
        File outFile = getOutputFile(panelTitleKey, baseOutputDir);
        try {
            FileUtils.writeLines(outFile, lines);
        }
        catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
