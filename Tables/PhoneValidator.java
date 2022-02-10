package Tables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {

    private Pattern pattern;

    private static final String PHONE_PATTERN =
            "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }

    public boolean validate(final String phone) {

        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}