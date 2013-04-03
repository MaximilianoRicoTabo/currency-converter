package com.globant.currencyconverter;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.globant.currencyconverter.convert.Convert;
import com.globant.currencyconverter.model.Currency;
import com.globant.currencyconverter.model.CurrencyNames;
import com.globant.currencyconverter.persistence.DataBaseHandler;
import com.globant.currencyconverter.webservice.FetchJSONData;
import com.globant.currencyconverter.webservice.ParseJson;

/**
 * Main activity class. 
 *
 * @author Maximiliano Rico &lt;maximiliano.rico@globant.com&gt;
 * @since 0.1
 */
public class MainActivity extends Activity {

  /**
   * A map containing the currency name as a key and the currency itself.
   */
  private Map<CurrencyNames,Currency> currencyValues;

  /**
   * The {@link DataBaseHandler}.
   */
  private DataBaseHandler databaseHandler;

  /**
   * The default 'from' app state.
   */
  private CurrencyNames from = CurrencyNames.UYU; 

  /**
   * The default 'to' app state.
   */
  private CurrencyNames to = CurrencyNames.ARS;

  /**
   * {@inheritDoc}.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
    RadioButton radioButtonUy = (RadioButton) findViewById(R.id.RadioButton01Uy);
    radioButtonUy.setVisibility(View.INVISIBLE);
    radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        // checkedId is the RadioButton selected
        RadioButton radioButtonAr = (RadioButton) findViewById(R.id.RadioButton3Ar);
        RadioButton radioButtonUy = (RadioButton) findViewById(R.id.RadioButton01Uy);
        radioButtonUy.setVisibility(View.INVISIBLE);
        RadioButton radioButtonUsd = (RadioButton) findViewById(R.id.RadioButton02Usd);
        RadioButton radioButton = (RadioButton) findViewById(checkedId);
        if (radioButton.getText().equals("ARS")) {
          radioButtonAr.setVisibility(View.INVISIBLE);
          radioButtonUy.setVisibility(View.VISIBLE);
          radioButtonUsd.setVisibility(View.VISIBLE);
          from = CurrencyNames.ARS;
        } else if (radioButton.getText().equals("UYU")) {
          radioButtonAr.setVisibility(View.VISIBLE);
          radioButtonUy.setVisibility(View.INVISIBLE);
          radioButtonUsd.setVisibility(View.VISIBLE);
          from = CurrencyNames.UYU;
        } else if (radioButton.getText().equals("USD")) {
          radioButtonAr.setVisibility(View.VISIBLE);
          radioButtonUy.setVisibility(View.VISIBLE);
          radioButtonUsd.setVisibility(View.INVISIBLE);
          from = CurrencyNames.USD;
          
        }
      }
    });
    
    RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.RadioGroup02);
    radioGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = (RadioButton) findViewById(checkedId);
        to = CurrencyNames.valueOf(radioButton.getText().toString());
      }
      });
    // retrieve database instance.
    databaseHandler = new DataBaseHandler(this);
    databaseHandler.open();
    Map<CurrencyNames, Currency> currencyValues = databaseHandler.getAllValues();
    if (isNetworkAvailable()) {
      Map<CurrencyNames, String> map = FetchJSONData.fecthAllCurrencies();
      if (currencyValues.isEmpty() || currencyValues == null) {
        for (CurrencyNames name : map.keySet()) {
          double value = ParseJson.doublefetchAny(name, map.get(name));
          databaseHandler.createCurrency(name.name(), value);
        }
      } else {
        this.currencyValues = currencyValues;
        for (CurrencyNames name : map.keySet()) {
          double value = ParseJson.doublefetchAny(name, map.get(name));
          if (currencyValues.get(name) != null) {
            databaseHandler.updateCurrency(name.name(), value);
          } else {
            databaseHandler.createCurrency(name.name(), value);
          }
        }
      }
      
      this.currencyValues = databaseHandler.getAllValues();
      databaseHandler.close();
    } else if (! currencyValues.isEmpty()) {
      this.currencyValues = currencyValues;
      Toast.makeText(this,
          "no internet connection detected, app will make conversion with the"
      +  " lastest currency fetched values. " + "The fetched date was",
          Toast.LENGTH_LONG).show();

    } else {
      Toast.makeText(this, "There is no chance to make the conversion you has no intenet connection"
                  + "or stored values. Connect your device to internet and try again."
          , Toast.LENGTH_LONG).show();
      Button button = (Button) findViewById(R.id.button1);
      button.setVisibility(View.INVISIBLE);
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  /**
   * Handler for on click convert button event.
   * 
   * @param view the {@link View}
   */
  public void onClick(View view) {
    EditText text = (EditText) findViewById(R.id.editText1);
    switch (view.getId()) {
      case R.id.button1:
        String input = text.getText().toString();
        if (input.length() == 0) {
          Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
          return;
        } else if (from.equals(to)) {
          Toast.makeText(this, "Please select different 'from' or 'to' option. Does not make sense"
              + " convert from: " + from + " to: " + to, Toast.LENGTH_LONG).show();
        } else {
          
          TextView label = (TextView) findViewById(R.id.textView2);
          TextView label2 = (TextView) findViewById(R.id.TextView01);
          double amount = Double.parseDouble(input);
          double[] values =
              Convert.convert(this.from, this.to, this.currencyValues, amount);
          if (values.length == 2) {
            label2.setVisibility(View.VISIBLE);
            label.setText("The converted value in the oficial market is: " + values[0] + "\n");
            label2.setText("The converted value in the parallel market is: " + values[1]);
          } else {
            label2.setVisibility(View.INVISIBLE);
            label.setText("The converted value is: " + values[0]);
          }
        }
        break;
    }
  }

  /**
   * method to determine whether the device is connected or not.
   *
   * @return true if device is connected, false otherwise.
   */
  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }
}
