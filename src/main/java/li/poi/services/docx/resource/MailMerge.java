package li.poi.services.docx.resource;

import li.poi.services.docx.control.MailMergeController;
import li.poi.services.docx.model.MailMergeRequest;
import li.poi.services.docx.model.MailMergeResponse;
import li.poi.services.docx.model.VersionInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * User: Iwan Paolucci
 * Date: 15.04.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
@Path("/mailmerge")
public class MailMerge {
    private static Logger log = Logger.getLogger(MailMerge.class.getName());

    @Path("/version")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public VersionInfo version() {
        return new VersionInfo("0.1");
    }

    @Path("/test")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public MailMergeResponse test() throws Docx4JException {
        MailMergeResponse mail = new MailMergeResponse();
        File tmp = null;
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
            wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");

            tmp = File.createTempFile("document", "docx");
            wordMLPackage.save(tmp);
            mail.setDocument(Base64.encodeBase64String(IOUtils.toByteArray(new FileInputStream(tmp))));
            mail.setError("success");
            mail.setDocumentName(tmp.getName());
        } catch (IOException e) {
            e.printStackTrace();
            mail.setError("error... " + e.getMessage());
        } finally {
            if (tmp != null) {
                boolean deleted = tmp.delete();
                if (!deleted) {
                    log.warn("file not deleted " + tmp.getAbsolutePath());
                }
            }
        }

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
                    log.warn("file not deleted " + template.getAbsolutePath());
                }
            }
            if (output != null) {
                boolean deleted = output.delete();
                if (!deleted) {
                    log.warn("file not deleted " + output.getAbsolutePath());
                }
            }
        }
        return response;
    }
}
