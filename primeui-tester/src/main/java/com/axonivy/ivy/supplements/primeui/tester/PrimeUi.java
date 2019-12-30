/*
 * Copyright (C) 2016 AXON IVY AG
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.axonivy.ivy.supplements.primeui.tester;

import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.axonivy.ivy.supplements.primeui.tester.widget.Dialog;
import com.axonivy.ivy.supplements.primeui.tester.widget.SelectBooleanCheckbox;
import com.axonivy.ivy.supplements.primeui.tester.widget.SelectCheckboxMenu;
import com.axonivy.ivy.supplements.primeui.tester.widget.SelectManyCheckbox;
import com.axonivy.ivy.supplements.primeui.tester.widget.SelectOneMenu;
import com.axonivy.ivy.supplements.primeui.tester.widget.SelectOneRadio;
import com.axonivy.ivy.supplements.primeui.tester.widget.Table;

/**
 * An API using a {@link org.openqa.selenium.WebDriver WebDriver} to interact
 * with Primefaces {@link org.openqa.selenium.WebElement WebElements}.
 * 
 * @author Denis Huelin
 * @since 6.0.0
 */
public class PrimeUi
{
  private AjaxHelper ajax;
  private WebDriver webDriver;

  public PrimeUi(WebDriver driver)
  {
    this.webDriver = driver;
    this.ajax = new AjaxHelper(driver);
  }

  public static SelectOneMenu selectOne(By locator)
  {
    return new SelectOneMenu(locator);
  }

  public static SelectCheckboxMenu selectCheckboxMenu(By locator)
  {
    return new SelectCheckboxMenu(locator);
  }

  public static SelectBooleanCheckbox selectBooleanCheckbox(By checks)
  {
    return new SelectBooleanCheckbox(checks);
  }
  
  public static SelectManyCheckbox selectManyCheckbox(By manyCheckbox)
  {
    return new SelectManyCheckbox(manyCheckbox);
  }

  public static SelectOneRadio selectOneRadio(By oneRadio)
  {
    return new SelectOneRadio(oneRadio);
  }

  public static Table table(By dataTable)
  {
    return new Table(dataTable);
  }

  public static Dialog dialog(By dialog)
  {
    return new Dialog(dialog);
  }

  public Accordion accordion(By locator)
  {
    return new Accordion(locator);
  }

  

  

  public class Accordion
  {
    private String accordionId;

    public Accordion(final By accordionLocator)
    {
      accordionId = await(driver -> driver.findElement(accordionLocator).getAttribute("id"));
    }

    public void toggleTab(String tabName)
    {
      String xPath = "//*[@id='" + accordionId + "']/*[contains(., '" + tabName + "')][@role='tab']";
      By tabLocator = By.xpath(xPath);
      String expansionAttribute = "aria-expanded";
      WebElement item = awaitCondition(ExpectedConditions.elementToBeClickable(webDriver.findElement(
              tabLocator)));
      String previousState = item.getAttribute(expansionAttribute);
      item.click();

      By nextSibling = By.xpath(xPath + "/following-sibling::*[contains(@role,'tabpanel')]");
      await(driver ->
              !driver.findElements(nextSibling).get(0).getAttribute("style").matches(".*overflow:.?hidden;.*height:.*"));
      await(driver -> 
              !driver.findElement(tabLocator).getAttribute(expansionAttribute).contains(previousState));
    }

    public boolean isTabOpen(String tabName)
    {
      return webDriver
              .findElement(By.xpath("//*[@id='" + accordionId + "']/*[contains(., '" + tabName + "')]"))
              .getAttribute("aria-expanded").contains("true");
    }

    public void openTab(String tabName)
    {
      if (isTabOpen(tabName))
      {
        return;
      }
      else
      {
        toggleTab(tabName);
      }
    }
  }

  protected <T> T await(Function<WebDriver, T> condition)
  {
    return awaitCondition(new ExpectedCondition<T>()
      {
        @Override
        public T apply(WebDriver driver)
        {
          try
          {
            return condition.apply(driver);
          }
          catch (StaleElementReferenceException ex)
          {
            return null;
          }
        }
      });
  }

  protected <T> T awaitCondition(ExpectedCondition<T> condition)
  {
    return ajax.await(condition);
  }
}