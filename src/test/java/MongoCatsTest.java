import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import entities.CatEntity;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MongoCatsTest {

    public static final String MONGO_URL = "mongodb://localhost:27017/";

    private static MongoClient client;

    @BeforeAll
    public static void setUp() {
        client = MongoClients.create(MONGO_URL);
    }

    @AfterAll
    public static void tearDown() {
        if (client != null) {
            client.close();
        }
    }

    @Test
    public void canConnect() {
        MongoCollection<Document> collection = client.getDatabase("cats").getCollection("cats-info");
        System.out.println(collection);
    }

    @Test
    public void canGetData() {
        MongoCollection<Document> catsInfo = client.getDatabase("cats").getCollection("cats-info");
        FindIterable<Document> iterDocuments = catsInfo.find();
        iterDocuments.forEach(document -> {
            String name = document.getString("name");
            System.out.println(name);
            Integer age;
            try {
                age = document.getInteger("age");
            } catch (ClassCastException exception) {
                age = 0;
            }
            System.out.println(age);
        });
    }

    @Test
    public void canGetDataList() {
        List<Document> results = new ArrayList<>();
        MongoCollection<Document> catsInfo = client.getDatabase("cats").getCollection("cats-info");
        FindIterable<Document> iterDocuments = catsInfo.find();
        iterDocuments.into(results);
        System.out.println(results);
    }

    @Test
    public void canAddData() {
        MongoCollection<Document> catsInfo = client.getDatabase("cats").getCollection("cats-info");

        Document document = new Document().append("name", "Рыжик").append("age", 5);
        catsInfo.insertOne(document);
    }

    @Test
    public void canAddDataEntity() {
       // List<CatEntity> results = new ArrayList<>();

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoCollection<CatEntity> catsInfo = client
            .getDatabase("cats")
            .withCodecRegistry(pojoCodecRegistry)
            .getCollection("cats-info", CatEntity.class);

        CatEntity catEntity = catsInfo.find().first();
        //catsInfo.find().into(results);
        System.out.println(catEntity);

//        CatEntity catEntity = new CatEntity();
//        catEntity.setAge(15);
//        catEntity.setName("Java");
//        catsInfo.insertOne(catEntity);
    }

    @Test
    public void canUpdateData() {
        MongoCollection<Document> catsInfo = client.getDatabase("cats").getCollection("cats-info");
        Document query = new Document().append("title", "Cool Runnings 2");

        Bson updates = Updates.combine(
            Updates.set("runtime", 99),
            Updates.addToSet("genres", "Sports"),
            Updates.currentTimestamp("lastUpdated"));
        UpdateOptions options = new UpdateOptions().upsert(true);

        catsInfo.updateOne(query, updates, options);
    }


}
