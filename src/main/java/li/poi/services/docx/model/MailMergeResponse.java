package li.poi.services.docx.model;

/**
 * User: Iwan Paolucci
 * Date: 15.04.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
public class MailMergeResponse {
    private String documentName;
    private String document;
    private String error;

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
