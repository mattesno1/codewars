/** https://www.codewars.com/kata/55d7e5aa7b619a86ed000070 */
public class Ordering {
  public String orderWord(String s) {
    
    return s == null || "".equals(s) ? "Invalid String!" :
            s.chars().sorted()
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
  }
}
