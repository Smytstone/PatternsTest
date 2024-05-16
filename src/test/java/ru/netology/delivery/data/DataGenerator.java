package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.DAYS;

public class DataGenerator {
    private DataGenerator() {
    }

    public static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(int shift) {
        // TODO: добавить логику для объявления переменной date и задания её значения, для генерации строки с датой
        // Вы можете использовать класс LocalDate и его методы для получения и форматирования даты
//        String startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        String endDate = LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate start = LocalDate.now();
        long days = ChronoUnit.DAYS.between(start, LocalDate.now().plusYears(3));
        LocalDate randomDate = start.plusDays(new Random().nextInt((int) days + 1));
//        LocalDate startDate = LocalDate.now();
//        LocalDate endDate = LocalDate.of(2025, 10, 31);
////        LocalDate endDate = LocalDate.now();
//        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
//        long randomNumberOfDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
//        LocalDate randomDate = startDate.plusYears(randomNumberOfDays);
        return randomDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        // TODO: добавить логику для объявления переменной city и задания её значения, генерацию можно выполнить
        // с помощью Faker, либо используя массив валидных городов и класс Random
        return faker.address().city();
    }

    public static String generateName(String locale) {
        // TODO: добавить логику для объявления переменной name и задания её значения, для генерации можно
        // использовать Faker
        return faker.name().fullName();
    }

    public static String generatePhone(String locale) {
        // TODO: добавить логику для объявления переменной phone и задания её значения, для генерации можно
        // использовать Faker
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            // TODO: добавить логику для создания пользователя user с использованием методов generateCity(locale),
            // generateName(locale), generatePhone(locale)
            UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
