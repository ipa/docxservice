package li.poi.services.docx.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

/**
 * User: Iwan Paolucci
 * Date: 15.04.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
@XmlRootElement
public class MailMergeRequest {
    private String template;
    private List<Map<String, String>> values;

    public MailMergeRequest() {
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<Map<String, String>> getValues() {
        return values;
    }

    public void setValues(List<Map<String, String>> values) {
        this.values = values;
    }
}
