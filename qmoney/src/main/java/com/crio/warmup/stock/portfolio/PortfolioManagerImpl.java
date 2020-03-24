
package com.crio.warmup.stock.portfolio;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {




  private RestTemplate restTemplate;

  // Caution: Do not delete or modify the constructor, or else your build will
  // break!
  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  //TODO: CRIO_TASK_MODULE_REFACTOR
  // Now we want to convert our code into a module, so we will not call it from main anymore.
  // Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  // into #calculateAnnualizedReturn function here and make sure that it
  // follows the method signature.
  // Logic to read Json file and convert them into Objects will not be required further as our
  // clients will take care of it, going forward.
  // Test your code using Junits provided.
  // Make sure that all of the tests inside PortfolioManagerTest using command below -
  // ./gradlew test --tests PortfolioManagerTest
  // This will guard you against any regressions.
  // run ./gradlew build in order to test yout code, and make sure that
  // the tests and static code quality pass.

  //CHECKSTYLE:OFF

  public static AnnualizedReturn logicCARR(LocalDate endDate,
    PortfolioTrade trade, Double buyPrice, Double sellPrice) {
    Double totalReturn = (sellPrice - buyPrice) / buyPrice;
    Double tnd =  (double) ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);
    Double tny = 365 / tnd;
    Double annualizedReturns = (double) (Math.pow(1 + totalReturn, tny) - 1);
    AnnualizedReturn xyz = new AnnualizedReturn(trade.getSymbol(),annualizedReturns, totalReturn);
    return xyz;
  }

  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades,
      LocalDate endDate) {
      if( portfolioTrades.size() <= 0) return null;
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());  

      ArrayList<AnnualizedReturn> xyz = new ArrayList<>();  
      for (int i = 0; i < portfolioTrades.size(); i++) {


        try {
          List<TiingoCandle> collection = getStockQuote(portfolioTrades.get(i).getSymbol(), portfolioTrades.get(i).getPurchaseDate(), endDate );
          if( collection.size() <= 0) return null;
          xyz.add(logicCARR(endDate, portfolioTrades.get(i),
          collection.get(0).getOpen(), collection.get(collection.size() - 1).getClose()));

        } catch (JsonMappingException e) {
          e.printStackTrace();
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }  

      }
      if(xyz.size() <= 0 ) return null;
      Collections.sort(xyz); 
      return xyz;
  }

  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo thirdparty APIs to a separate function.
  //  It should be split into fto parts.
  //  Part#1 - Prepare the Url to call Tiingo based on a template constant,
  //  by replacing the placeholders.
  //  Constant should look like
  //  https://api.tiingo.com/tiingo/daily/<ticker>/prices?startDate=?&endDate=?&token=?
  //  Where ? are replaced with something similar to <ticker> and then actual url produced by
  //  replacing the placeholders with actual parameters.


  public List<TiingoCandle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());  
    // RestTemplate restTemplate = new RestTemplate();
    String uri = buildUri(symbol, from, to);
    String result = restTemplate.getForObject(uri, String.class);
    List<TiingoCandle> collection = mapper.readValue(result, 
    new TypeReference<ArrayList<TiingoCandle>>() {  
        });
    return collection;
  }
  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
       String uriTemplate = "https://api.tiingo.com/tiingo/daily/"+ symbol + "/prices?"
            + "startDate=" + startDate + "&endDate="+ endDate +"&token=387cdf98e65bc82744766b37024857017767c929";
            return uriTemplate;
  }
}
