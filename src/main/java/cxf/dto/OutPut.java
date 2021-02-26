package cxf.dto;

import javax.xml.datatype.XMLGregorianCalendar;

public class OutPut {

    private XMLGregorianCalendar data;

    private long id;

    private String name;

    @Override
    public String toString() {
        return "OutPut{" +
                "data=" + data +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XMLGregorianCalendar getData() {
        return data;
    }

    public void setData(XMLGregorianCalendar data) {
        this.data = data;
    }
}
