package de.froesche.nz.database.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.mongodb.lang.Nullable;

@JsonAutoDetect
public class Person {

    private Object _id;
    private String name;
    private String vorname;

    public Object get_id() {
        return _id;
    }

    public void set_id(Object _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
}
