package com.luxoft.reportingsystem.collector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GenericData {

    private String title;
    private String code;
    private Integer value;

    public GenericData() {
        this("","",0);
    }

    public GenericData(String title, String code, Integer value) {
        this.title = title;
        this.code = code;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    @XmlElement
    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    @XmlElement
    public void setValue(Integer value) {
        this.value = value;
    }
}
