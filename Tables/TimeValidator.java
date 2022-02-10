package Tables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeValidator {

    private Pattern pattern;

    private static final String TIME_PATTERN =
    "^(([0,1][0-9])|(2[0-3])):[0-5][0-9]$";

    public TimeValidator() {
        pattern = Pattern.compile(TIME_PATTERN);
    }

    public boolean validate(final String time) {

        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
}