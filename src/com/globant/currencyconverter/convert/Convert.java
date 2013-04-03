package com.globant.currencyconverter.convert;

import java.util.Map;

import com.globant.currencyconverter.model.Currency;
import com.globant.currencyconverter.model.CurrencyNames;


/**
 * Convert  abstract class. Contains static methods to do math stuff.
 *
 * @author Maximiliano Rico &lt;maximiliano.rico@globant.com&gt;
 * @since 0.1
 */
public abstract class Convert {

  /**
   * Convert an amount expressed in a given currency to an amount expressed in a different currency, 
   *
   * @param from the actual currency name to convert.
   * @param to the expected currency to be converted.
   * @param currencyValues a Map containing name and values.
   * @param amount The amount to be converted.
   * @return An array with converted amounts.
   */
  public static double[] convert(final CurrencyNames from, final CurrencyNames to,
      final Map<CurrencyNames,Currency> currencyValues, final double amount) {
    switch (from) {
      case ARS:
        switch(to) {
          case UYU:
            return new double[]{round(convertARStoUY(currencyValues, amount))};
          case USD:
            return new double[]{round(convertARStoUSDOficial(currencyValues, amount)),
                round(convertARStoUSDParallel(currencyValues, amount))};
        }
      case UYU:
        switch (to) {
          case ARS:
            return new double[] {round(convertUYtoARS(currencyValues, amount))};
          case USD:
            return new double[] {round(convertUYtoUSD(currencyValues, amount))};
        }
      case USD:
        switch (to) {
          case UYU:
            return new double[] {round(convertUSDtoUY(currencyValues, amount))};
          case ARS:
            return new double[] {
                round(convertUSDtoARSOficial(currencyValues, amount)),
                round(convertUSDtoARSParallel(currencyValues, amount))};
        }
    }
    //shouldn't reach here
    return null;
  }

  /**
   * Convert an amount of Argentinian pesos to an amount of Uruguayan pesos.
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
   * Convert an amount of Argentinian pesos to an amount of United States dollars. Official market.
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
   * Convert an amount of Argentinian pesos to an amount of United States dollars. Parallel market.
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
   * Convert an amount of Uruguayan pesos to an amount of Argentinian pesos.
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

  /**
   * 
   * @param value
   * @param places
   * @return
   */
  private static double round(double value) {
    long factor = (long) Math.pow(10, 2);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
  }
}
