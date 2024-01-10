package com.lawman.outbox.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawman.outbox.product.entity.Product;
import lombok.experimental.UtilityClass;
import org.apache.kafka.connect.data.Struct;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class HandlerUtils {

  public static String getDocumentId(Struct key) {
    String id = key.getString("id");
    Matcher matcher = Pattern.compile("\"\\$oid\":\\s*\"(\\w+)\"").matcher(id);
    return matcher.find() ? matcher.group(1) : null;
  }

  public static String getCollection(Struct sourceRecordValue) {
    Struct source = (Struct) sourceRecordValue.get("source");
    return source.getString("collection");
  }

  public static Product getData(Struct sourceRecordValue) throws IOException {
    if (Objects.nonNull(sourceRecordValue.get("after"))) {
      var source = sourceRecordValue.get("after").toString();
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      return mapper.readValue(source, Product.class);
    }
    return null;
  }

  public static String getOperation(Struct sourceRecordValue) {
    return sourceRecordValue.getString("op");
  }
}
