package li.poi.services.docx.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * User: Iwan Paolucci
 * Date: 15.04.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
@XmlRootElement
public class MergedMail {
    private String name;
    private byte[] document;
    private Map<String, String> values;

    public MergedMail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
