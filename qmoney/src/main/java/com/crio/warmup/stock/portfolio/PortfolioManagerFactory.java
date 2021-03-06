
package com.crio.warmup.stock.portfolio;

import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerImpl;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerFactory {

  // CRIO_TASK_MODULE_REFACTOR
  // Implement the method in such a way that it will return new Instance of
  // PortfolioManager using RestTemplate provided.
  public static PortfolioManager getPortfolioManager(RestTemplate restTemplate) {
    PortfolioManager pf = new PortfolioManagerImpl(restTemplate);
    return pf;
  }

}
