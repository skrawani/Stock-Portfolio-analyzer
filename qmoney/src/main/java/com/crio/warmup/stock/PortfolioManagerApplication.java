
package com.crio.warmup.stock;


// import com.crio.warmup.stock.dto.AnnualizedReturn;
// import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.Stock;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
// import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
// import java.nio.file.Files;
import java.nio.file.Paths;
// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Collections;
// import java.util.Comparator;

import java.util.List;
import java.util.UUID;
// import java.util.logging.Level;
import java.util.logging.Logger;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

import org.apache.logging.log4j.ThreadContext;
// import org.springframework.web.client.RestTemplate;

public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Read the json file provided in the argument[0]. The file will be avilable in the classpath.
  //  1. Use #resolveFileFromResources to get actual file from classpath.
  //  2. parse the json file using ObjectMapper provided with #getObjectMapper,
  //  and extract symbols provided in every trade.
  //  return the list of all symbols in the same order as provided in json.
  //  Test the function using gradle commands below
  //   ./gradlew run --args="trades.json"
  //  Make sure that it prints below String on the console -
  //  ["AAPL","MSFT","GOOGL"]
  //  Now, run
  //  ./gradlew build and make sure that the build passes successfully
  //  There can be few unused imports, you will need to fix them to make the build pass.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = resolveFileFromResources(args[0]);
    Stock[] trades = objectMapper.readValue(file, Stock[].class);
    List<String> lst = new ArrayList<String>();        
    for (int i = 0; i < trades.length; i++) {
      lst.add(trades[i].getSymbol());
    }
    return lst;
  }

  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

  public static List<String> debugOutputs() {
    String valueOfArgument0 = "trades.json";
    String resultOfResolveFilePathArgs0 = 
        "/home/crio-user/workspace/sachin-storm-sr-ME_QMONEY/qmoney/bin/main/trades.json";
    String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@2e8e8225";
    String functionNameFromTestFileInStackTrace = "mainReadFile";
    String lineNumberFromTestFileInStackTrace = "22";
    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }




  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    printJsonObject(mainReadFile(args));
  }
}