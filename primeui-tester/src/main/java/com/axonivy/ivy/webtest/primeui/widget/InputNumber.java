package com.axonivy.ivy.webtest.primeui.widget;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.Condition;

public class InputNumber
{
  private final String inputNumberId;

  public InputNumber(By inputNumber)
  {
    this.inputNumberId = $(inputNumber).shouldBe(visible).attr("id") + "_input";
  }
  
  public void setValue(String value)
  {
    clear();
    $(By.id(inputNumberId)).sendKeys(value);
    $(By.id(inputNumberId)).sendKeys(Keys.TAB);
  }
  
  public void should(Condition condition)
  {
    $(By.id(inputNumberId)).should(condition);
  }
  
  private String getValue()
  {
    return $(By.id(inputNumberId)).shouldBe(visible).getValue();
  }
  
  public void clear()
  {
    while(StringUtils.isNotBlank(getValue()))
    {
      $(By.id(inputNumberId)).sendKeys(Keys.BACK_SPACE);
    }
  }
}
