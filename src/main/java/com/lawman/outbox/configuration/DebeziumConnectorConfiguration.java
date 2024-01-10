package com.lawman.outbox.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

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
    File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");

    return io.debezium.config.Configuration.create()
          // engine properties
          .with("name", "sbd-mongodb")
          .with("connector.class", "io.debezium.connector.mongodb.MongoDbConnector")
//          .with("offset.storage", "io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore")
//          .with("offset.storage.jdbc.url", postgresUrl)
//          .with("offset.storage.jdbc.user", postgresUsername)
//          .with("offset.storage.jdbc.password", postgresPassword)
          .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
          .with("offset.storage.file.filename", "/tmp/offsets.dat")
          .with("offset.flush.interval.ms", "60000")
          // connector specific properties
          .with("mongodb.hosts", "dbrs/172.168.200.202:20000")
          .with("mongodb.connection.string", mongoDbUri)
          .with("topic.prefix", "sbd-mongodb-connector")
          .with("database.include.list", databaseIncludeList)
          .with("collection.include.list", collectionIncludeList)
          .with("snapshot.delay.ms", "100")
          .with("errors.log.include.messages", "true")
          .build();

  }
}
