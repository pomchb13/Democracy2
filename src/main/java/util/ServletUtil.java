package util;

/**
 * Created by Leonhard on 17.09.2017.
 */
public class ServletUtil {
    public static String filter(String inputText) {
        if (inputText == null)
            return "";

        StringBuilder text = new StringBuilder(inputText.length());
        for (char c : inputText.toCharArray()) {
            switch (c) {
                case '<':
                    text.append("&lt;");
                    break;
                case '>':
                    text.append("&gt;");
                    break;
                case '&':
                    text.append("&amp;");
                    break;
                case '\"':
                    text.append("&quot;");
                    break;
                case '\'':
                    text.append("&apos;");
                    break;
                default:
                    text.append(c);
            }
        }
        return text.toString();
    }

}
