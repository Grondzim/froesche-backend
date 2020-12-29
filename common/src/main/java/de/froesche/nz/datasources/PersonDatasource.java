package de.froesche.nz.datasources;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.froesche.nz.action.PersonAction;
import de.froesche.nz.database.dto.Person;
import de.froesche.nz.database.resultwrapper.MongoDBResultWrapper;
import de.froesche.nz.querybuilder.AbstractQueryBuilder;
import de.froesche.nz.querybuilder.SelectQueryBuilder;
import org.bson.Document;

import java.sql.SQLException;
import java.util.*;

public class PersonDatasource extends AbstractDBDatasource<PersonAction> {

    private static final Map<String,String> TABLENAMES = Map.of("person","");

    @Override
    protected void getMongoDBResult(PersonAction action, MongoDBResultWrapper mongoDBResultWrapper) {
        List<Document> results = mongoDBResultWrapper.getResult();
        ArrayList<Person> persons = new ArrayList<>();
        results.stream().forEach(doc -> {
            try {
                persons.add(objectMapper.readValue(doc.toJson(), Person.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        action.setName(persons);
    }

    public void getAllPerson(PersonAction action) throws SQLException {
        queryBuilder = SelectQueryBuilder.query().select("SELECT_ALL").from(TABLENAMES).where(null);
        execute(action);
    }

    public void getPersonByUsername(PersonAction action, String username) throws SQLException {
        queryBuilder = SelectQueryBuilder.query().select("SELECT_ALL").from(TABLENAMES).where(AbstractQueryBuilder.equals("name", username));
        execute(action);
    }
}
