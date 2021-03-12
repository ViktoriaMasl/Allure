import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DataChangeTest {
    String setMeetingDate(int daysFromToday) {
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar dataToday = Calendar.getInstance();
        dataToday.add(Calendar.DAY_OF_YEAR, daysFromToday);
        return dateFormat.format(dataToday.getTime());
    }

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure",new AllureSelenide());
    }


    @BeforeEach
    void openLocalhost() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");}

    @Test
    void shouldScheduleForTheNextDate() {
        $("[data-test-id=\"city\"] input").setValue(FakerGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(3));
        $("[data-test-id=\"name\"] input").setValue(FakerGenerator.Registration.generateByCard().getName());
        $("[data-test-id=\"phone\"] input").setValue(FakerGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldScheduleForAnotherDate() {
        $("[data-test-id=\"city\"] input").setValue(FakerGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(3));
        $("[data-test-id=\"name\"] input").setValue(FakerGenerator.Registration.generateByCard().getName());
        $("[data-test-id=\"phone\"] input").setValue(FakerGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
        $(withText("Запланировать")).click();
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(7));
        $(withText("Перепланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }
    @Test
    void shouldScheduleForTheNextDateWithWrongCity() {
        $("[data-test-id=\"city\"] input").setValue("Ivanovo123");
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(3));
        $("[data-test-id=\"name\"] input").setValue(FakerGenerator.Registration.generateByCard().getName());
        $("[data-test-id=\"phone\"] input").setValue(FakerGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Неверно указан город! Допустимы только русские буквы, пробелы и дефисы")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldScheduleWithWrongData() {
        $("[data-test-id=\"city\"] input").setValue(FakerGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=\"date\"] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=\"date\"] input").setValue("01.01.2020");
        $("[data-test-id=\"name\"] input").setValue(FakerGenerator.Registration.generateByCard().getName());
        $("[data-test-id=\"phone\"] input").setValue(FakerGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldScheduleWithWrongName() {
        $("[data-test-id=\"city\"] input").setValue(FakerGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(3));
        $("[data-test-id=\"name\"] input").setValue("Viktor Ivanov");
        $("[data-test-id=\"phone\"] input").setValue(FakerGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldScheduleWithSpecialCasesOfName() {
        $("[data-test-id=\"city\"] input").setValue(FakerGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(3));
        $("[data-test-id=\"name\"] input").setValue("Ерёма Бахов");
        $("[data-test-id=\"phone\"] input").setValue(FakerGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldScheduleWithWrongPhone() {
        $("[data-test-id=\"city\"] input").setValue(FakerGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(3));
        $("[data-test-id=\"name\"] input").setValue(FakerGenerator.Registration.generateByCard().getName());
        $("[data-test-id=\"phone\"] input").setValue("+77777");
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Номер указан неверно")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldScheduleDoubleOnTheDate() {
        $("[data-test-id=\"city\"] input").setValue(FakerGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(3));
        $("[data-test-id=\"name\"] input").setValue(FakerGenerator.Registration.generateByCard().getName());
        $("[data-test-id=\"phone\"] input").setValue(FakerGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
        $(withText("Запланировать")).click();
        $(withText("У вас уже запланирована встреча на эту дату")).shouldBe(Condition.visible);
    }
}
