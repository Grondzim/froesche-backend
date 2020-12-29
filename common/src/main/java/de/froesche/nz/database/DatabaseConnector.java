package de.froesche.nz.database;

import de.froesche.nz.database.resultwrapper.ResultWrapper;

import java.sql.SQLException;

public interface DatabaseConnector<T extends ResultWrapper> {

    public static final String TABLE_PERSON= "person";

    public void openConenction() throws SQLException;

    public void closeConnection();

    public T executeQuery(QueryBuilder query);
}