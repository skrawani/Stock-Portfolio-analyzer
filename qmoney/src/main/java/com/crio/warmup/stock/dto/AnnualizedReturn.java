
package com.crio.warmup.stock.dto;

public class AnnualizedReturn implements Comparable<AnnualizedReturn> {

  private final String symbol;
  private final Double annualizedReturn;
  private final Double totalReturns;

  public AnnualizedReturn(String symbol, Double annualizedReturn, Double totalReturns) {
    this.symbol = symbol;
    this.annualizedReturn = annualizedReturn;
    this.totalReturns = totalReturns;
  }

  public String getSymbol() {
    return symbol;
  }

  public Double getAnnualizedReturn() {
    return annualizedReturn;
  }

  public Double getTotalReturns() {
    return totalReturns;
  }

  @Override 
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    } 
    if (object == null || getClass() != object.getClass()) {
      return false;
    } 
    if (!super.equals(object)) {
      return false;
    } 
    AnnualizedReturn that = (AnnualizedReturn) object;
    return java.util.Objects.equals(this.getAnnualizedReturn(), that.getAnnualizedReturn());
  }

  @Override  
  public int hashCode() {
    return java.util.Objects.hash(super.hashCode(), this.getAnnualizedReturn());
  }

  // @Override     
  public int compareTo(AnnualizedReturn candidate) {    
    return (this.getAnnualizedReturn() > candidate.getAnnualizedReturn() ? -1 : 
              (this.getAnnualizedReturn().equals(candidate.getAnnualizedReturn())  ? 0 : 1));     
  }  


}
