import com.github.javafaker.Faker;

import java.util.Locale;

public class FakerGenerator {
    private FakerGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationByCardInfo generateByCard() {
            Faker faker = new Faker(new Locale("ru"));
            return new RegistrationByCardInfo(
                    faker.address().city(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber()
            );
        }
    }
}

class RegistrationByCardInfo {
    private final String city;
    private final String name;
    private final String phone;

    public RegistrationByCardInfo(String city, String name, String phone) {
        this.city = city;
        this.name = name;
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
