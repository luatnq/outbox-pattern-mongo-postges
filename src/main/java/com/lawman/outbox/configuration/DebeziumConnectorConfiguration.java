package com.lawman.outbox.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DebeziumConnectorConfiguration {
  @Value("${spring.data.mongodb.uri}")
  private String mongoDbUri;

  @Value("${spring.datasource.url}")
  private String postgresUrl;

  @Value("${spring.datasource.username}")
  private String postgresUsername;

  @Value("${spring.datasource.password}")
  private String postgresPassword;

  @Value("${database.include.list}")
  private String databaseIncludeList;

  @Value("${collection.include.list}")
  private String collectionIncludeList;


  @Bean
  public io.debezium.config.Configuration mongodbConnector() throws IOException {
//
//    Map<String, String> configMap = new HashMap<>();
//
//    //This sets the name of the Debezium connector instance. It’s used for logging and metrics.
//    configMap.put("name", "cdc-mongodb");
//    //This specifies the Java class for the connector. Debezium uses this to create the connector instance.
//    configMap.put("connector.class", "io.debezium.connector.mongodb.MongoDbConnector");
//    //This sets the Java class that Debezium uses to store the progress of the connector.
//    // In this case, it’s using a JDBC-based store, which means it will store the progress in a relational database.
//    configMap.put("offset.storage", "io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore");
//    //This is the JDBC URL for the database where Debezium stores the connector offsets (progress).
//    configMap.put("offset.storage.jdbc.url", postgresUrl);
//    configMap.put("offset.storage.jdbc.user", postgresUsername);
//    configMap.put("offset.storage.jdbc.password", postgresPassword);
//    //This is the MongoDB connection string that Debezium uses to connect to your MongoDB instance
//    configMap.put("mongodb.connection.string", mongoDbUri);
//    //This prefix is added to all Kafka topics that this connector writes to.
//    configMap.put("topic.prefix", "sbd-mongodb-connector");
//    //This is a comma-separated list of MongoDB database names that the connector will monitor for changes.
//    configMap.put("database.include.list", databaseIncludeList);
//    //This is a comma-separated list of MongoDB collection names that the connector will monitor for changes.
//    configMap.put("collection.include.list", collectionIncludeList);
//    //When errors.log.include.messages set to true, then any error messages resulting from failed operations
//    // are also written to the log.
//    configMap.put("errors.log.include.messages", "true");


//
//    configMap.put("offset.storage", "io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore");
//    //This is the JDBC URL for the database where Debezium stores the connector offsets (progress).
//    configMap.put("offset.storage.jdbc.url", postgresUrl);
//    configMap.put("offset.storage.jdbc.user", postgresUsername);
    return io.debezium.config.Configuration.create()
          // engine properties
          .with("name", "sbd-mongodb")
          .with("connector.class", "io.debezium.connector.mongodb.MongoDbConnector")
          .with("offset.storage", "io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore")
          .with("offset.storage.jdbc.url", postgresUrl)
          .with("offset.storage.jdbc.user", postgresUsername)
          .with("offset.storage.jdbc.password", postgresPassword)
          .with("offset.flush.interval.ms", "60000")
          // connector specific properties
          .with("mongodb.hosts", "dbrs/172.168.200.202:20000")
//          .with("mongodb.connection.string", mongoDbUri)
          .with("topic.prefix", "sbd-mongodb-connector")
          .with("database.include.list", databaseIncludeList)
          .with("collection.include.list", collectionIncludeList)
          .with("snapshot.delay.ms", "100")
          .with("errors.log.include.messages", "true")
          .build();

  }
}
