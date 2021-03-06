/*
 * Copyright (C) 2021 Axon Ivy AG
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
package com.axonivy.ivy.webtest.primeui.widget;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class SelectOneRadio
{
  private final String oneRadioId;

  public SelectOneRadio(By oneRadio)
  {
    oneRadioId = $(oneRadio).shouldBe(visible).attr("id");
  }

  public SelectOneRadio selectItemById(final String id)
  {
    $(radioById(id)).click();
    $(radioById(id)).shouldHave(cssClass("ui-state-active"));
    return this;
  }

  public SelectOneRadio selectItemByValue(final String value)
  {
    $(radioByLabel(value)).click();
    $(radioByLabel(value)).shouldHave(cssClass("ui-state-active"));
    return this;
  }
  
  public SelectOneRadio selectedValueShouldBe(Condition condition)
  {
    $(By.id(oneRadioId)).findAll(".ui-radiobutton-box").find(Condition.cssClass("ui-state-active"))
            .parent().find("div > input").shouldBe(condition);
    return this;
  }
  
  @Deprecated
  public String getSelectedValue()
  {
    return $(By.id(oneRadioId)).findAll(".ui-radiobutton-box").find(Condition.cssClass("ui-state-active"))
            .parent().find("div > input").getValue();
  }
  
  private SelenideElement radioById(String id)
  {
    return $(By.id(id)).parent().parent().find("div.ui-radiobutton-box").shouldBe(visible);
  }
  
  private SelenideElement radioByLabel(String label)
  {
    return $(By.id(oneRadioId)).findAll("label").find(exactText(label)).parent()
            .find("div.ui-radiobutton-box").shouldBe(visible);
  }

}