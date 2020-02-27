package com.crio.warmup.stock;

class Stock {
  private String symbol;
  private int quantity;
  private String tradeType;

  private String purchaseDate;


  public String getSymbol() {     
    return this.symbol; 
    
  }
    
  public void setSymbol(String brand) {   
    this.symbol = brand;
  }

    
  public int  getQuantity() { 
    return this.quantity; 
  }
    
  public void setQuantity(int doors) { 
        
    this.quantity = doors; 
  }

  public String getTradeType() {     
    return this.tradeType; 
  }

  public void   setTradeType(String tradeType) { 
    this.tradeType = tradeType;
  }

  public String getPurchaseDate() { 
    return this.purchaseDate; 
  }

  public void   setPurchaseDate(String purchaseDate) { 
    this.purchaseDate = purchaseDate;
  }
}