package li.poi.services.docx.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: Iwan Paolucci
 * Date: 04.05.14
 * Copyright © 2014 by Iwan Paolucci (see LICENSE)
 */
@XmlRootElement
public class VersionInfo {
    private String version;

    public VersionInfo() {
    }

    public VersionInfo(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
