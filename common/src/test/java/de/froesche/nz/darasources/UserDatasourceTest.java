package de.froesche.nz.darasources;

import de.froesche.nz.action.PersonAction;
import de.froesche.nz.database.MongoDBConnector;
import de.froesche.nz.database.StaticDatabaseConnector;
import de.froesche.nz.datasources.PersonDatasource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDatasourceTest {

    @Test
    public void simpleTest() throws Exception {
        StaticDatabaseConnector.setDatabaseConnector(MongoDBConnector.getConnection());
        PersonDatasource datasource = new PersonDatasource();

        PersonAction action = new PersonAction();

        datasource.getAllPerson(action);

        PersonAction action2 = new PersonAction();
        datasource.getPersonByUsername(action2,"ABC");

        Assertions.assertTrue(action.getPersonList().size()==2);
        Assertions.assertTrue(action2.getPersonList().size()==1);
    }

}
