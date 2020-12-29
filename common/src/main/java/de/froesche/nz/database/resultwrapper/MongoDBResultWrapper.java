package de.froesche.nz.database.resultwrapper;

import com.mongodb.DBObject;
import org.bson.Document;

import java.util.List;

public class MongoDBResultWrapper extends ResultWrapper<List<Document>> {

    public MongoDBResultWrapper(List<Document> result) {
        super(result);
    }
}
