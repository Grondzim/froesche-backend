package de.froesche.nz.database;

public class StaticDatabaseConnector {

    private static DatabaseConnector databaseConnector;

    private StaticDatabaseConnector(){}

    public static DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    public static void setDatabaseConnector(DatabaseConnector databaseConnector) {
        StaticDatabaseConnector.databaseConnector = databaseConnector;
    }
}
