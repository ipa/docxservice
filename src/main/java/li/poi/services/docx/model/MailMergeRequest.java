package li.poi.services.docx.model;

import java.util.List;
import java.util.Map;

/**
 * User: Iwan Paolucci
 * Date: 15.04.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
public class MailMergeRequest {
    private byte[] template;
    private List<Map<String, String>> values;

    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public List<Map<String, String>> getValues() {
        return values;
    }

    public void setValues(List<Map<String, String>> values) {
        this.values = values;
    }
}
