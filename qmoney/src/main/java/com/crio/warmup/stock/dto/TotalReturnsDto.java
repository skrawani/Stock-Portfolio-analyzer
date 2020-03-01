
package com.crio.warmup.stock.dto;

public class TotalReturnsDto implements Comparable<TotalReturnsDto> {

  private String symbol;
  private Double closingPrice;

  public TotalReturnsDto(String symbol, Double closingPrice) {
    this.symbol = symbol;
    this.closingPrice = closingPrice;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Double getClosingPrice() {
    return closingPrice;
  }

  public void setClosingPrice(Double closingPrice) {
    this.closingPrice = closingPrice;
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
    TotalReturnsDto that = (TotalReturnsDto) object;
    return java.util.Objects.equals(this.getClosingPrice(), that.getClosingPrice());
  }

  @Override  
  public int hashCode() {
    return java.util.Objects.hash(super.hashCode(), this.getClosingPrice());
  }

  @Override     
  public int compareTo(TotalReturnsDto candidate) {    
    return (this.getClosingPrice() < candidate.getClosingPrice() ? -1 : 
              (this.getClosingPrice().equals(candidate.getClosingPrice())  ? 0 : 1));     
  }    


}
