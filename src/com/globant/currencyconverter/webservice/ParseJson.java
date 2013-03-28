package com.globant.currencyconverter.webservice;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.globant.currencyconverter.model.CurrencyNames;

/**
 * 
 * @author maximiliano
 *
 */
public abstract class ParseJson {

  public static double doublefetchAny(final CurrencyNames name, String data) {
    double currencyValue = 0;
    try {
      switch(name) {
        case ARS:
          currencyValue = parseARSoficial(data);
          break;
        case ARS_BLUE : 
          currencyValue = parseDolarBlue(data);
          break;
        case UYU:
          currencyValue = parseUYU(data);
          break;
        case USD:
          currencyValue = parseUSD(data);
          break;
      }
    } catch(JSONException e) {
      e.printStackTrace();
    }
    return currencyValue;
  }
    
  
  private static double parseARSoficial(String data) throws JSONException {
    return parseGoogleConversor(data);
  }

  private static double parseDolarBlue(String data) {
    double currencyValue = 0;
    try {
      JSONObject jsonObject = new JSONObject(data);
      JSONObject obj = jsonObject.getJSONObject("exchangerate");
      currencyValue = Double.parseDouble(obj.getString("buy"));
    } catch(Exception e) {
      e.printStackTrace();
    }
    return currencyValue;
  }
  
  private static double parseUYU(String data) throws JSONException {
    return parseGoogleConversor(data);
  }
  
  private static double parseUSD(String data) throws JSONException {
    return parseGoogleConversor(data);
  }
  
  private static double parseGoogleConversor(String data) throws JSONException {
    double currencyValue = 0;
    try {
      JSONObject jsonObject = new JSONObject(data);

      String value = (String) jsonObject.get("rhs");
      String shortValue = value.substring(0, 4);
      if (!NumberUtils.isNumber(shortValue)) {
        currencyValue =  Double.parseDouble(shortValue.substring(0, 1));
      } else {
        currencyValue = Double.parseDouble(shortValue);
      }
      } catch(Exception e) {
        e.printStackTrace();
      }
    return currencyValue;
    }
}