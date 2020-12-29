package de.froesche.nz.datasources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.froesche.nz.action.FroescheAction;
import de.froesche.nz.database.DatabaseConnector;
import de.froesche.nz.database.StaticDatabaseConnector;
import de.froesche.nz.database.resultwrapper.MongoDBResultWrapper;
import de.froesche.nz.database.resultwrapper.ResultWrapper;
import de.froesche.nz.querybuilder.AbstractQueryBuilder;

import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class AbstractDBDatasource<T extends FroescheAction/*, S extends DatabaseConnector*/> {

    protected Logger logger = Logger.getLogger(String.valueOf(getClass()));
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected AbstractQueryBuilder queryBuilder;
    protected DatabaseConnector databaseConnector;

    public AbstractDBDatasource(){
        databaseConnector = StaticDatabaseConnector.getDatabaseConnector();
    }

    protected void openConnection() throws SQLException {
        databaseConnector.openConenction();
    };

    protected void closeConnection(){
        databaseConnector.closeConnection();
    };

    protected void fillDataInAction(T action){
        ResultWrapper resultWrapper = getQueryResult();
        if (resultWrapper instanceof MongoDBResultWrapper){
            getMongoDBResult(action, (MongoDBResultWrapper) resultWrapper);
        }
    };

    protected abstract void getMongoDBResult(T action, MongoDBResultWrapper mongoDBResultWrapper);

    protected ResultWrapper getQueryResult(){
        return databaseConnector.executeQuery(queryBuilder);
    }

    protected void execute(T action) throws SQLException {
        openConnection();
        fillDataInAction(action);
        closeConnection();
    }
}
