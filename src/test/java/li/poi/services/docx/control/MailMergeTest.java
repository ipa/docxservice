package li.poi.services.docx.control;

import li.poi.services.docx.control.MailMergeController;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * User: Iwan Paolucci
 * Date: 15.04.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
public class MailMergeTest {
    @Test
    public void testDoc() throws Exception {
        MailMergeController controller = new MailMergeController();

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();
        map.put( "Vorname", "Plutext");
        map.put("Nachname", "Bourke Street");
        map.put("Adresszeile_1", "Strasse");
        map.put("Postleitzahl", "6003");
        map.put("Ort", "Luzern");
        map.put("Preis", "1500 SFr.");

        data.add(map);

        map = new HashMap<String, String>();
        map.put("Vorname", "Iwan");
        map.put("Nachname", "Paolucci");
        map.put("Adresszeile_1", "Strasse");
        map.put("Postleitzahl", "6003");
        map.put("Ort", "Luzern");
        map.put("Preis", "1500 SFr.");

        data.add(map);

        File template = new File("/Users/Iwan/Downloads/testfile.docx");
        File output = controller.getMergedDocument(template, data);

        assertNotNull(output);
    }
}
