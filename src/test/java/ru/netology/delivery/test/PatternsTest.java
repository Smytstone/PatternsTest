package ru.netology.delivery.test;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static io.opentelemetry.semconv.trace.attributes.SemanticAttributes.FaasDocumentOperationValues.INSERT;
import static java.time.LocalDate.now;
import static org.openqa.selenium.Keys.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class PatternsTest {
    @Test
    public void shouldTestForm() {
        var newUser = DataGenerator.Registration.generateUser("ru");
        var firstDate = DataGenerator.generateDate(1);
        var secondDate = DataGenerator.generateDate(2);
        open("http://localhost:9999");

        $("[data-test-id=city] input").val(newUser.getCity());
        $("[data-test-id=date] input").sendKeys(LEFT_CONTROL, INSERT, BACK_SPACE);
        $("[data-test-id=date] input").val(firstDate);
        $("[data-test-id=name] input").val(newUser.getName());
        $("[data-test-id=phone] input").val(newUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".notification").shouldBe(visible, Duration.ofSeconds(10));
        $(".notification").shouldHave(exactText("Успешно! Встреча успешно запланирована на " +
                firstDate));
        $("[data-test-id=date] input").sendKeys(LEFT_CONTROL, INSERT, BACK_SPACE);
        $("[data-test-id=date] input").val(secondDate);
        $(".button").click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification]").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=replan-notification] button").click();
        $(".notification").shouldHave(exactText("Успешно! Встреча успешно запланирована на " +
                secondDate));

    }
}
