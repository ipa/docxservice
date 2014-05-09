package li.poi.services.docx.examples;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import li.poi.services.docx.model.MailMergeRequest;
import li.poi.services.docx.model.MailMergeResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Iwan Paolucci
 * Date: 09.05.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
public class MergeExample {

    private static String TEMPLATE_BASE64 = "UEsDBBQABgAIAAAAIQAJJIeCgQEAAI4FAAATAAgCW0NvbnRlbnRfVHlwZXNdLnhtbCCiBAIooAACAAAAAAAAAAAAA" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAC0lE1Pg0AQhu8m";

    public static void main(String[] args) throws IOException {
        File file = getTestFileFromService();
        System.out.println("got test file");
        System.out.println("filename: " + file.getAbsoluteFile());

        System.out.println("merge");
        MailMergeRequest request = new MailMergeRequest();
        request.setTemplate(TEMPLATE_BASE64);
        List<Map<String, String>> values = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("Name", "Müller");
        map.put("Vorname", "Max");
        map.put("Adresse", "Hauptstrasse 10");
        map.put("Ort", "Sonstwo");
        map.put("Plz", "1234");
        map.put("Briefanrede", "Sehr geehrter Herr Müller");
        values.add(map);
        request.setValues(values);

        File merge = getMergedFileFromService(request);
        System.out.println("got merged file");
        System.out.println("filename: " + merge.getAbsoluteFile());
    }

    private static File getTestFileFromService() throws IOException {
        Client client = Client.create();
        WebResource webResource = client.resource("http://jbossews-poili.rhcloud.com/mailmerge/test");

        String responseString = webResource
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        MailMergeResponse response = new Gson().fromJson(responseString, MailMergeResponse.class);
        File file = File.createTempFile("document", ".docx");
        file.deleteOnExit();
        FileUtils.writeByteArrayToFile(file, Base64.decodeBase64(response.getDocument()));
        return file;
    }

    private static File getMergedFileFromService(MailMergeRequest request) throws IOException {
        Client client = Client.create();
        WebResource webResource = client.resource("http://jbossews-poili.rhcloud.com/mailmerge/merge");
        String requestJson = new Gson().toJson(request);

        String responseString = webResource
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .put(String.class, requestJson);

        MailMergeResponse response = new Gson().fromJson(responseString, MailMergeResponse.class);
        File file = File.createTempFile("document", ".docx");
        file.deleteOnExit();
        FileUtils.writeByteArrayToFile(file, Base64.decodeBase64(response.getDocument()));
        return file;
    }
}
