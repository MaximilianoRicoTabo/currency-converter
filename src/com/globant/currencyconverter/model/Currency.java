package com.globant.currencyconverter.model;

import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.isTrue;


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
   * Creates a new {@link Currency}.
   *
   * @param name The name of the currency. Cannot be null.
   * @param value The value of the currency. Cannot be null and greater than 0.
   */
  public Currency(final CurrencyNames name, final double value) {
    notNull(name, "The name cannot be null");
    notNull(value, "The name cannot be null");
    isTrue(value > 0, "The given value must be greater than 0");
    this.name = name.name();
    this.value = value;
  }

  /**
   * Gets the name of the {@link Currency}. Won't be null.
   *
   * @return The name of the {@link Currency}.
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the {@link Currency}. Cant't be null.
   *
   * @param name the name of the {@link Currency}.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the value of the {@link Currency}. Won't be null.
   *
   * @return the value of the {@link Currency}.
   */
  public double getValue() {
    return value;
  }

  /**
   * Set the name of the {@link Currency}. Cant't be null and must be greater than 0.
   *
   * @param value The Currency value.
   */
  public void setValue(double value) {
    this.value = value;
  }
}

