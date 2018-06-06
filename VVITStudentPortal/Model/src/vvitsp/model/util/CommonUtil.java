package vvitsp.model.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    
    private static final String EMAIL_REGEXP = "[a-zA-Z0-9!$%&'*+=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?";
    
    public CommonUtil() {
        super();
    }
    
    public static boolean isBlank(String value) {
        int strLen = 0;
        if (value == null || (strLen = value.length()) == 0) {
            return Boolean.TRUE;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(value.charAt(i)) == Boolean.FALSE) {
                return Boolean.FALSE;
            }
        }
        return true;
    }
    
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }
    
    public static boolean isValidEmailAddress(String emailAddress) {
        Pattern pattern = Pattern.compile(EMAIL_REGEXP, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress.trim());
        return matcher.matches();
    }
}
