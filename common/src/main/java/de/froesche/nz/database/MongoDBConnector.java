package de.froesche.nz.database;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.froesche.nz.database.resultwrapper.MongoDBResultWrapper;
import de.froesche.nz.querybuilder.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class MongoDBConnector implements DatabaseConnector<MongoDBResultWrapper> {
    Logger mongoLogger = Logger.getLogger("org.mongodb.driver");

    private MongoDBConnector(){
        mongoLogger.setLevel(Level.SEVERE);
    }

    public static MongoDBConnector getConnection(){
        return new MongoDBConnector();
    }

    MongoClient mongoClient;
    String user =""; // the user name
    String database="local"; // the name of the database in which the user is defined
    char[] password={}; // the password as a character array

    @Override
    public void openConenction() throws SQLException {

        //MongoCredential credential = MongoCredential.createCredential(user, database, password);

      /*  MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToSslSettings(builder -> builder.enabled(true))
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                .build();*/

        mongoClient = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false");
    }

    @Override
    public void closeConnection() {
        mongoClient.close();
    }


    public MongoDBResultWrapper executeQuery(QueryBuilder query) {
        MongoDatabase database = mongoClient.getDatabase("froesche");
        Document filter = new Document();
        MongoCollection<Document> col = null;

        if (query instanceof SelectQueryBuilder) {
            SelectQueryBuilder selectQueryBuilder = (SelectQueryBuilder)query;
            col = database.getCollection(selectQueryBuilder.getFromBuilder().getValue().keySet().stream().findFirst().get());

            IsCondition isCondition = selectQueryBuilder.getConditions();

            if (isCondition instanceof QueryBuilder.EQUALS){
                QueryBuilder.EQUALS equals = (QueryBuilder.EQUALS) isCondition;
                filter.append(equals.getColumn(), equals.getValue());
            }

        } else if (query instanceof InsertQueryBuilder) {
            InsertQueryBuilder insertQueryBuilder = (InsertQueryBuilder)query;

        } else if (query instanceof DeleteQueryBuilder){
            DeleteQueryBuilder deleteQueryBuilder = (DeleteQueryBuilder)query;

        }

        List<Document> result = new ArrayList<>();

        if (!Objects.isNull(col)){
            if(!filter.isEmpty()){
                col.find(filter).forEach((Consumer<? super Document>) document -> {
                    result.add(document);
                });
            }else {
                col.find().forEach((Consumer<? super Document>) document -> {
                    result.add(document);
                });
            }
        }
        //Iterable<Bson> filter = query.getQueryCondition().stream().map(fi -> new Document(fi.getColumn(),fi.getWhereClausal())).collect(Collectors.toList());

        return new MongoDBResultWrapper(result);
    }


}
