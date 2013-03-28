package com.globant.currencyconverter.convert;

import java.util.Map;

import com.globant.currencyconverter.model.Currency;
import com.globant.currencyconverter.model.CurrencyNames;


/**
 * Convert class. 
 *
 * @author Maximiliano Rico &lt;maximiliano.rico@globant.com&gt;
 * @since 0.1
 */
public abstract class Convert {

  /**
   * 
   * @param from
   * @param to
   * @param value
   * @param currencyValues
   * @param amount
   * @return
   */
  public static double[] convert(final CurrencyNames from, final CurrencyNames to,
      final Map<CurrencyNames,Currency> currencyValues, final double amount) {
    switch (from) {
      case ARS:
        switch(to) {
          case UYU:
            return new double[]{convertARStoUY(currencyValues, amount)};
          case USD:
            return new double[]{convertARStoUSDOficial(currencyValues, amount),
                convertARStoUSDParallel(currencyValues, amount)};
        }
        
      case UYU:
        switch(to) {
          case ARS:
            return new double[]{convertUYtoARS(currencyValues, amount)};
          case USD:
            return new double[]{convertUYtoUSD(currencyValues, amount)};
        }
      case USD:
        switch(to) {
          case UYU:
            return new double[]{convertUSDtoUY(currencyValues, amount)};
          case ARS:
            return new double[]{convertUSDtoARSOficial(currencyValues, amount),
                convertUSDtoARSParallel(currencyValues, amount)};
        }

    }
    //shouldn't reach here
    return null;
  }

  /**
   * 
   * @param currencyValues
   * @param amount
   * @return
   */
  private static double convertARStoUY(final Map<CurrencyNames,Currency> currencyValues,
      final double amount) {
    double arbitrage = getArbitrage(currencyValues, CurrencyNames.UYU, CurrencyNames.ARS_BLUE);
    return total(amount, arbitrage); 
  }

  /**
   * 
   * @param currencyValues
   * @param amount
   * @return
   */
  private static double convertARStoUSDOficial(final Map<CurrencyNames,Currency> currencyValues,
      final double amount) {
    double arbitrage = getArbitrage(currencyValues, CurrencyNames.USD, CurrencyNames.ARS);
    return total(amount, arbitrage); 
  }

  /**
   * 
   * @param currencyValues
   * @param amount
   * @return
   */
  private static double convertARStoUSDParallel(final Map<CurrencyNames,Currency> currencyValues,
      final double amount) {
    double arbitrage = getArbitrage(currencyValues, CurrencyNames.USD, CurrencyNames.ARS_BLUE);
    return total(amount, arbitrage); 
  }

  /**
   * 
   * @param currencyValues
   * @param amount
   * @return
   */
  private static double convertUYtoARS(final Map<CurrencyNames,Currency> currencyValues,
      final double amount) {
    double arbitrage = getArbitrage(currencyValues, CurrencyNames.ARS_BLUE, CurrencyNames.UYU);
    return total(amount, arbitrage); 
  }
  
  /**
   * 
   * @param currencyValues
   * @param amount
   * @return
   */
  private static double convertUYtoUSD(final Map<CurrencyNames,Currency> currencyValues,
      final double amount) {
    double arbitrage = getArbitrage(currencyValues, CurrencyNames.USD, CurrencyNames.UYU);
    return total(amount, arbitrage); 
  }

  /**
   * 
   * @param currencyValues
   * @param amount
   * @return
   */
  private static double convertUSDtoUY(final Map<CurrencyNames,Currency> currencyValues,
      final double amount) {
    double arbitrage = getArbitrage(currencyValues, CurrencyNames.UYU, CurrencyNames.USD);
    return total(amount, arbitrage);
  }

  /**
   * 
   * @param currencyValues
   * @param amount
   * @return
   */
  private static double convertUSDtoARSOficial(final Map<CurrencyNames,Currency> currencyValues,
      final double amount) {
    double arbitrage = getArbitrage(currencyValues, CurrencyNames.ARS, CurrencyNames.USD);
    return total(amount, arbitrage);
  }
  
  /**
   * 
   * @param currencyValues
   * @param amount
   * @return
   */
  private static double convertUSDtoARSParallel(final Map<CurrencyNames,Currency> currencyValues,
      final double amount) {
    double arbitrage = getArbitrage(currencyValues, CurrencyNames.ARS_BLUE, CurrencyNames.USD);
    return total(amount, arbitrage);
  }

  
  /**
   * 
   * @param currencyValues
   * @param from
   * @param to
   * @return
   */
  private static double getArbitrage(final Map<CurrencyNames,Currency> currencyValues,
      CurrencyNames from, CurrencyNames to) {
    return currencyValues.get(from).getValue() / currencyValues.get(to).getValue();
  }

  /**
   *
   * @param amount
   * @param arbitrage
   * @return
   */
  private static double total(final double amount, final double arbitrage) {
    return amount * arbitrage;
  }
}
