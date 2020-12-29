package de.froesche.nz.darasources;

import de.froesche.nz.action.UserAction;
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

        UserAction action = new UserAction();

        datasource.getAllPerson(action);

        UserAction action2 = new UserAction();
        datasource.getPersonByUsername(action2,"ABC");

        Assertions.assertTrue(action.getPersonList().size()==2);
        Assertions.assertTrue(action2.getPersonList().size()==1);
    }

}
