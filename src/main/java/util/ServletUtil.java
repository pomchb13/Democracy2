package util;

/**
 * Author: Leonhard Gangl
 * Created on:
 * Description: Filters special characters from the inputs.
 */
public class ServletUtil {
    /***
     * This method filters the user / admin input to be safe against attacks
     * @param inputText user / admin input
     * @return filtered string
     */
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
