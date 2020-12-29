package de.froesche.nz.action;

import de.froesche.nz.database.dto.Person;

import java.util.List;

public class UserAction implements FroescheAction{

    private List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public void setName(List<Person> personList) {
        this.personList = personList;
    }
}
