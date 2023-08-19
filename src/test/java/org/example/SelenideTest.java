package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

class SelenideTest {
    public String date(int day, String pattern) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void testDeliveryCard() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[type='text']").setValue("Москва");
        String nowDate = date(4, "dd.MM.yyyy");
        $("[data-test-id='date']").click();
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").sendKeys(nowDate);
        $x("//*[@data-test-id='name']//input").setValue("Хасан Рашидов");
        $x("//*[@data-test-id='phone']//input").setValue("+79998638700");
        $("[data-test-id='agreement']").click();
        $("[class='button__content']").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + nowDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}