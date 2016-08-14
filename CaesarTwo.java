import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** https://www.codewars.com/kata/55084d3898b323f0aa000546 */
public class CaesarTwo {

    private static final int NUM_OF_SPLITS = 5;

    public static List<String> encodeStr(String s, int shift) {
        String encrypted = encryptString(s, shift);
        return splitString(encrypted);
    }

    public static String decode(List<String> s) {
        String encrypted = s.stream().collect(Collectors.joining());
        int shift = encrypted.charAt(0) - encrypted.charAt(1);
        return encryptString(encrypted, shift).substring(4);
    }

    private static String encryptString(String s, int shift) {

        int offset = shift >= 0 ? shift % 26 : 26 + (shift % 26);
        char firstChar = Character.toLowerCase(s.charAt(0));
        char shifted = (char) (firstChar + offset);

        StringBuilder sb = new StringBuilder(s.length() + 2)
                .append(firstChar)
                .append(shifted);
        s.chars()
                .map(c -> shiftChar(c, offset))
                .forEach(c -> sb.append((char) c));

        return sb.toString();
    }

    private static int shiftChar(int c, int shift) {
        if (!Character.isAlphabetic(c))
            return c;
        if (Character.isLowerCase(c))
            return ((c - 'a' + shift) % 26) + 'a';
        return ((c - 'A' + shift) % 26) + 'A';
    }

    private static List<String> splitString(String s) {

        int splitSize;
        int restSize = 0;
        if (0 == s.length() % NUM_OF_SPLITS) {
            splitSize = s.length() / NUM_OF_SPLITS;
        } else {
            splitSize = (s.length() + NUM_OF_SPLITS) / NUM_OF_SPLITS;
            restSize = s.length() % splitSize;
        }

        List<String> substrings = new ArrayList<>();
        for (int i = 0; (i + 1) * splitSize <= s.length(); i++) {
            substrings.add(s.substring(i * splitSize, (i + 1) * splitSize));
        }
        if (restSize > 0) {
            substrings.add(s.substring(s.length() - restSize, s.length()));
        }

        return substrings;
    }
}
