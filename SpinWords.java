import java.util.stream.Stream;
import java.util.stream.Collectors;

/** https://www.codewars.com/kata/5264d2b162488dc400000001 */
public class SpinWords {

  public String spinWords(String sentence) {
    if (sentence == null || sentence.length() < 5) {
      return sentence;
    }
    return Stream.of(sentence.split(" "))
        .map(s -> {
          if (s.length() < 5)
             return s;
          else
            return new StringBuilder(s).reverse().toString();
        }).collect(Collectors.joining(" "));
  }
}
