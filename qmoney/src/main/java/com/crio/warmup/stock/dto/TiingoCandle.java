
package com.crio.warmup.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TiingoCandle implements Candle {

  private Double open;
  private Double close;
  private Double high;
  private Double low;
  private LocalDate date;

  @Override
  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  @Override
  public Double getClose() {
    return close;
  }

  public void setClose(Double close) {
    this.close = close;
  }

  @Override
  public Double getHigh() {
    return high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  @Override
  public Double getLow() {
    return low;
  }

  public void setLow(Double low) {
    this.low = low;
  }

  @Override
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate timeStamp) {
    this.date = timeStamp;
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
    TiingoCandle that = (TiingoCandle) object;
    return java.util.Objects.equals(this.getClose(), that.getClose());
  }

  @Override  
  public int hashCode() {
    return java.util.Objects.hash(super.hashCode(), this.getClose());
  }

  // @Override     
  public int compareTo(TiingoCandle candidate) {    
    return (this.getClose() < candidate.getClose() ? -1 : 
              (this.getClose().equals(candidate.getClose())  ? 0 : 1));     
  }  

  @Override
  public String toString() {
    return "TiingoCandle{"
            + "open=" + open
            + ", close=" + close
            + ", high=" + high
            + ", low=" + low
            + ", date=" + date
            + '}';
  }
}
