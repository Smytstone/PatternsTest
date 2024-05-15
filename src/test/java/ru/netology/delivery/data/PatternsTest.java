package ru.netology.delivery.data;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        Faker faker = new Faker(new Locale("ru"));
        String city = faker.address().city();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();


        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(LEFT_CONTROL, INSERT, BACK_SPACE);
        $("[data-test-id=date] input").setValue((now().plusDays( 4).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".notification").shouldBe(visible, Duration.ofSeconds(10));
        $(".notification").shouldHave(exactText("Успешно! Встреча успешно запланирована на " +
                now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        $("[data-test-id=date] input").sendKeys(LEFT_CONTROL, INSERT, BACK_SPACE);
        $("[data-test-id=date] input").setValue((now().plusDays( 5).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        $(".button").click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification]").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=replan-notification] button").click();
        $(".notification").shouldHave(exactText("Успешно! Встреча успешно запланирована на " +
                now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));

    }
}
