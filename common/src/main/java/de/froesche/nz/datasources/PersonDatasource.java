package de.froesche.nz.datasources;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.froesche.nz.action.UserAction;
import de.froesche.nz.database.dto.Person;
import de.froesche.nz.database.resultwrapper.MongoDBResultWrapper;
import de.froesche.nz.querybuilder.QueryBuilder;
import de.froesche.nz.querybuilder.SelectQueryBuilder;
import org.bson.Document;

import java.sql.SQLException;
import java.util.*;

public class PersonDatasource extends AbstractDBDatasource<UserAction> {

    private static final Map<String,String> TABLENAMES = Map.of("person","");

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
        queryBuilder = SelectQueryBuilder.query().select("SELECT_ALL").from(TABLENAMES).where(null);
        execute(action);
    }

    public void getPersonByUsername(UserAction action, String username) throws SQLException {
        queryBuilder = SelectQueryBuilder.query().select("SELECT_ALL").from(TABLENAMES).where(QueryBuilder.equals("name", username));
        execute(action);
    }
}
