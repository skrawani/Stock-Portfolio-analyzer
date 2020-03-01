
package com.crio.warmup.stock;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.dto.TotalReturnsDto;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;






@RestController
public class PortfolioManagerApplication {
  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule()); 
    File file = resolveFileFromResources(args[0]);
    PortfolioTrade[] trades = objectMapper.readValue(file, PortfolioTrade[].class);
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


  public static List<String> debugOutputs() {
    String valueOfArgument0 = "trades.json";
    String resultOfResolveFilePathArgs0 = 
        "/home/crio-user/workspace/sachin-storm-sr-ME_QMONEY/qmoney/bin/main/trades.json";
    String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@2e8e8225";
    String functionNameFromTestFileInStackTrace = "mainReadFile";
    String lineNumberFromTestFileInStackTrace = "22";
    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace });
  }


  public static PortfolioTrade[] mainReadQuotes1(String[] args)
      throws IOException, URISyntaxException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule()); 
    File file = resolveFileFromResources(args[0]);
    PortfolioTrade[] trades = objectMapper.readValue(file, PortfolioTrade[].class);
    return (trades);
  }

  public static ArrayList<TotalReturnsDto> mainReadQuotes2(PortfolioTrade[] trades, String[] args)
      throws IOException, URISyntaxException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());  
    RestTemplate restTemplate = new RestTemplate();
    ArrayList<TotalReturnsDto> xyz = new ArrayList<>();  
    for (int i = 0; i < trades.length; i++) {
      String uri = "https://api.tiingo.com/tiingo/daily/" + trades[i].getSymbol() + "/prices?startDate=" + trades[i].getPurchaseDate() + "&endDate=" + args[1] + "&token=387cdf98e65bc82744766b37024857017767c929";
      String result = restTemplate.getForObject(uri, String.class);
      List<TiingoCandle> collection = mapper.readValue(result, 
              new TypeReference<ArrayList<TiingoCandle>>() {
        });
      xyz.add(new TotalReturnsDto(trades[i].getSymbol(),
          collection.get(collection.size() - 1).getClose()));
    }
    return (xyz);
  }

  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    PortfolioTrade[] trades = mainReadQuotes1(args);
    ArrayList<TotalReturnsDto> xyz = mainReadQuotes2(trades, args);
    Collections.sort(xyz);  
    List<String> res = new ArrayList<String>();  
    for (int i = 0; i < xyz.size(); i++) {
      res.add(xyz.get(i).getSymbol());
    }
    return (res);
  }




  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Copy the relevant code from #mainReadQuotes to parse the Json into PortfolioTrade list and
  //  Get the latest quotes from TIingo.
  //  Now That you have the list of PortfolioTrade And their data,
  //  With this data, Calculate annualized returns for the stocks provided in the Json
  //  Below are the values to be considered for calculations.
  //  buy_price = open_price on purchase_date and sell_value = close_price on end_date
  //  startDate and endDate are already calculated in module2
  //  using the function you just wrote #calculateAnnualizedReturns
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.
  //  use gralde command like below to test your code
  //  ./gradlew run --args="trades.json 2020-01-01"
  //  ./gradlew run --args="trades.json 2019-07-01"
  //  ./gradlew run --args="trades.json 2019-12-03"
  //  where trades.json is your json file


  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
    
    PortfolioTrade[] trades = mainReadQuotes1(args);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());  
    RestTemplate restTemplate = new RestTemplate();
    ArrayList<AnnualizedReturn> xyz = new ArrayList<>();  
    for (int i = 0; i < trades.length; i++) {
      String uri = "https://api.tiingo.com/tiingo/daily/" + trades[i].getSymbol() + "/prices?startDate=" + trades[i].getPurchaseDate() + "&endDate=" + args[1] + "&token=387cdf98e65bc82744766b37024857017767c929";
      String result = restTemplate.getForObject(uri, String.class);
      List<TiingoCandle> collection = mapper.readValue(result, 
              new TypeReference<ArrayList<TiingoCandle>>() {
        });
        
      xyz.add(calculateAnnualizedReturns(LocalDate.parse(args[1]), trades[i],
          collection.get(0).getOpen(), collection.get(collection.size() - 1).getClose()));
    }
    Collections.sort(xyz); 
    return xyz;
  }

  //  CRIO_TASK_MODULE_CALCULATIONS
  //  annualized returns should be calculated in two steps -
  //  1. Calculate totalReturn = (sell_value - buy_value) / buy_value
  //  Store the same as totalReturns
  //  2. calculate extrapolated annualized returns by scaling the same in years span. The formula is
  //  annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //  Store the same as annualized_returns
  //  return the populated list of AnnualizedReturn for all stocks,
  //  Test the same using below specified command. The build should be successful
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {
    Double totalReturn = (sellPrice - buyPrice) / buyPrice;
    Double tnd =  (double) ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);
    Double tny = 365 / tnd;
    Double annualizedReturns = (double) (Math.pow(1 + totalReturn, tny) - 1);
    AnnualizedReturn xyz = new AnnualizedReturn(trade.getSymbol(),annualizedReturns, totalReturn);
    return xyz;
  }

  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());



    printJsonObject(mainCalculateSingleReturn(args));

  }

}
