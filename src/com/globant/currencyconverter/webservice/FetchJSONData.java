package com.globant.currencyconverter.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.globant.currencyconverter.model.CurrencyNames;

 public abstract class FetchJSONData {


  /**
   * 
   * @param currencies
   * @return
   */
  public static Map<CurrencyNames,String> fecthAllCurrencies() {
    Map<CurrencyNames, String> map = new HashMap<CurrencyNames, String>();
    for (CurrencyNames name : CurrencyNames.values()) {
      switch (name) {
        case ARS:
          map.put(CurrencyNames.ARS, fetchARSOficial());
          map.put(CurrencyNames.ARS_BLUE, fetchARSBlue());
          break;
        case UYU:
          map.put(CurrencyNames.UYU, fetchUyCurrency());
          break;
        case USD:
          map.put(CurrencyNames.USD, fetchUSDCurrency());
          break;
      }
    }
    return map;
  }


  /**
   * 
   * @return
   */
  private static String fetchARSOficial() {
    HttpGet httpGet = new HttpGet("http://www.google.com/ig/calculator?hl=en&q=1USD=?ARS");
    String currencyValue = doHardFetchWork(httpGet);
    return currencyValue;
  }

  /**
   * 
   * @return
   */
  private static String fetchARSBlue() {
    HttpGet httpGet = new HttpGet("http://www.eldolarblue.net/getDolarBlue.php?as=json");
    String currencyValue = doHardFetchWork(httpGet);
    return currencyValue;
  }

  /**
   * 
   * @return
   */
  private static String fetchUyCurrency() {
    HttpGet httpGet = new HttpGet("http://www.google.com/ig/calculator?hl=en&q=1USD=?UYU");
    String currencyValue  = doHardFetchWork(httpGet);
    return currencyValue;
  }

  /**
   * 
   * @return
   */
  private static String fetchUSDCurrency() {
    HttpGet httpGet = new HttpGet("http://www.google.com/ig/calculator?hl=en&q=1USD=?USD");
    String currencyValue = doHardFetchWork(httpGet);
    return currencyValue;
  }

  /**
   * 
   * @param httpGet
   * @return
   */
  private static String doHardFetchWork(final HttpGet httpGet) {
    StringBuilder builder = new StringBuilder();
    try {
      HttpClient client = new DefaultHttpClient();
      HttpResponse response = client.execute(httpGet);
      StatusLine statusLine = response.getStatusLine();
      int statusCode = statusLine.getStatusCode();
      if (statusCode == 200) {
        HttpEntity entity = response.getEntity();
        InputStream content = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        String line;
        while ((line = reader.readLine()) != null) {
          builder.append(line);
        }
      } else {
        Log.e(FetchJSONData.class.toString(), "Failed to download file");
      }
    } catch(ClientProtocolException e) {
      e.printStackTrace();
    } catch(IOException e) {
      e.printStackTrace();
    }
    return builder.toString();
  }
}
