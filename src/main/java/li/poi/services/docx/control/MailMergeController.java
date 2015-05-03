package li.poi.services.docx.control;

import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Iwan Paolucci
 * Date: 15.04.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
public class MailMergeController {

    public File getMergedDocument(File template, List<Map<String, String>> data) {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(template);
            List<Map<DataFieldName, String>> replaceVars = convertReplaceVariables(data);

            WordprocessingMLPackage output = MailMerger.getConsolidatedResultCrude(wordMLPackage, replaceVars, true);

            File outputFile = File.createTempFile("document", ".docx");
            output.save(outputFile);
            return outputFile;
        } catch (Docx4JException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Map<DataFieldName, String>> convertReplaceVariables(List<Map<String, String>> data) {
        List<Map<DataFieldName, String>> replaceVars = new ArrayList<Map<DataFieldName, String>>();
        for (Map<String, String> d : data) {
            Map<DataFieldName, String> map = new HashMap<DataFieldName, String>();
            for (String s : d.keySet()) {
                map.put(new DataFieldName(s), d.get(s));
            }
            replaceVars.add(map);
        }
        return replaceVars;
    }

}
