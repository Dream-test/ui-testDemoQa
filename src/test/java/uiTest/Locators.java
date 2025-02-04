package uiTest;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class Locators {
    public void clickCardByName(String name) {
        SelenideElement cardByName = $$(By.className("card-body")).stream()
                .filter(card -> {
                    SelenideElement h5Element = card.$("h5");
                    return h5Element.exists() && h5Element.getText().equals(name);
                })
                .findFirst()
                .orElse(null);
        if (cardByName != null) {
            cardByName.click();
        } else {
            System.out.println("Card with title " + name + " does not exist");
        }
        //$(Selectors.byTagAndText("h5", name)).click();
    }


    public void checkTextInLightButton(String name) {
        SelenideElement buttonByName = $$(By.cssSelector(".btn.btn-light")).stream()
                .filter(button -> {
                    SelenideElement lightButton = button.$("span");
                    return lightButton.exists() && lightButton.getText().equals(name);
                })
                .findFirst()
                .orElse(null);
        if (buttonByName != null) {
            buttonByName.shouldHave(visible);
        } else {
            System.out.println("Light button with title " + name + " does not exist");
        }
        //$x("//*[@id=\"item-0\"]/span").shouldHave(Condition.text("Text Box"));
    }
}
