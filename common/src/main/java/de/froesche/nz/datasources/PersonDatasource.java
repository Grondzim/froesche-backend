package de.froesche.nz.datasources;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.froesche.nz.action.UserAction;
import de.froesche.nz.database.QueryBuilder;
import de.froesche.nz.database.dto.Person;
import de.froesche.nz.database.resultwrapper.MongoDBResultWrapper;
import org.bson.Document;

import java.sql.SQLException;
import java.util.*;

import static de.froesche.nz.database.DatabaseConnector.TABLE_PERSON;
import static de.froesche.nz.database.QueryBuilder.SELECT_ALL;

public class PersonDatasource extends AbstractDBDatasource<UserAction> {


    @Override
    protected void getMongoDBResult(UserAction action, MongoDBResultWrapper mongoDBResultWrapper) {
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

    public void getAllPerson(UserAction action) throws SQLException {
        queryBuilder = QueryBuilder.createQuery().select(SELECT_ALL).from(TABLE_PERSON).build();
        execute(action);
    }

    public void getPersonByUsername(UserAction action, String username) throws SQLException {
        queryBuilder = QueryBuilder.createQuery().select(SELECT_ALL).from(TABLE_PERSON).where("name", username).build();
        execute(action);
    }
}
