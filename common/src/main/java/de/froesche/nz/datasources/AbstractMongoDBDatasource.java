package de.froesche.nz.datasources;

import de.froesche.nz.action.FroescheAction;

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
