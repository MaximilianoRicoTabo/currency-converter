package com.globant.currencyconverter.model;


/**
 * Model object representing given currency.
 *
 * @author Maximiliano Rico &lt;maximiliano.rico@globant.com&gt;
 * @since 0.1
 */
public class Currency {

  /**
   * The currency name. 
   */
  private String name;

  /**
   * The currency value.
   */
  private double value;

  /**
   * 
   * @param name
   * @param value
   */
  public Currency(final CurrencyNames name, final double value) {
    this.name = name.name();
    this.value = value;
  }

  /**
   * 
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 
   * @return
   */
  public double getValue() {
    return value;
  }

  /**
   * 
   * @param value
   */
  public void setValue(double value) {
    this.value = value;
  }
}

