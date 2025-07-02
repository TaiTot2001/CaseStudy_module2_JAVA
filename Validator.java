import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final String idRegex = "(?=.*[a-zA-Z])[a-zA-Z0-9]+";

    public boolean isValidID(String id) {
        Pattern pattern = Pattern.compile(idRegex);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }
}
