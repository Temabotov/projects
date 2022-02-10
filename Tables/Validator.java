package Tables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Pattern pattern;

    private static final String PATTERN =
            "^(.+)\\s+(\\S+)(-(\\d+))?$";

    public Validator() {
        pattern = Pattern.compile(PATTERN);
    }

    public boolean validate(final String address) {
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }
}