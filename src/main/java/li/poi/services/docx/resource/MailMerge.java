package li.poi.services.docx.resource;

import li.poi.services.docx.control.MailMergeController;
import li.poi.services.docx.model.MailMergeRequest;
import li.poi.services.docx.model.MailMergeResponse;
import li.poi.services.docx.model.MergedMail;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import javax.ws.rs.*;
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
 * Date: 15.04.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
@Path("/mailmerge")
public class MailMerge {
    @Path("/test")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public MergedMail test() throws Docx4JException {
        WordprocessingMLPackage wp = Docx4J.load(new File("/Users/Iwan/Developer/docxservice/src/main/templates/confirmationletter.docx"));
        MergedMail mail = new MergedMail();
        mail.setName("success");
        return mail;
    }

    @Path("/merge")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public MailMergeResponse merge(MailMergeRequest request) {
        MailMergeResponse response = new MailMergeResponse();
        File template = null;
        File output = null;
        try {
            MailMergeController controller = new MailMergeController();
            template = File.createTempFile("template", ".docx");
            FileUtils.writeByteArrayToFile(template, request.getTemplate());
            output = controller.getMergedDocument(template, request.getValues());
            response.setDocument(Base64.encodeBase64String(IOUtils.toByteArray(new FileInputStream(output))));
        } catch (IOException e) {
            response.setError(e.getMessage());
        } finally {
            // delete the files
            if (template != null) {
                boolean deleted = template.delete();
                if (!deleted) {
                    System.out.println("file not deleted " + template.getAbsolutePath());
                }
            }
            if (output != null) {
                boolean deleted = output.delete();
                if (!deleted) {
                    System.out.println("file not deleted " + output.getAbsolutePath());
                }
            }
        }
        return response;
    }

    @Path("/doc")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public MergedMail doc() throws IOException {
        MailMergeController controller = new MailMergeController();

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("Vorname", "Plutext");
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
        MergedMail merge = new MergedMail();
        merge.setName("test");
        merge.setDocument(IOUtils.toByteArray(new FileInputStream(output.getAbsoluteFile())));

        return merge;
    }
}
