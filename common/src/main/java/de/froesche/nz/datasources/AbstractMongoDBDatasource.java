package de.froesche.nz.datasources;

import com.mongodb.DBObject;
import de.froesche.nz.action.FroescheAction;
import de.froesche.nz.database.DatabaseConnector;
import de.froesche.nz.database.MongoDBConnector;
import de.froesche.nz.database.Query;
import de.froesche.nz.database.StaticDatabaseConnector;
import de.froesche.nz.database.resultwrapper.MongoDBResultWrapper;
import de.froesche.nz.database.resultwrapper.ResultWrapper;

public abstract class AbstractMongoDBDatasource<T extends FroescheAction> extends AbstractDBDatasource<T/*, MongoDBConnector*/> {

    public AbstractMongoDBDatasource(){
        //databaseConnector = MongoDBConnector.getConnection();
    }

   /* @Override
    protected MongoDBResultWrapper getQueryResult(){
        return databaseConnector.executeQuery(getQuery());
    }

    @Override
    protected ResultWrapper getQueryResult(){
        return databaseConnector.executeQuery(getQuery());
    }*/
}
