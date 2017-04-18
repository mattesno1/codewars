import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** https://www.codewars.com/kata/5629db57620258aa9d000014 */
public class Mixing {

    public static String mix(String s1, String s2) {

        return IntStream.range('a', 'z' + 1)
                .mapToObj(c -> CharStat.from((char) c, s1, s2))
                .filter(CharStat::isValid)
                .sorted()
                .map(CharStat::toString)
                .collect(Collectors.joining("/"));
    }
}

class CharStat implements Comparable<CharStat> {

    private final int source;
    private final char c;
    private final long count;
    private final String stringRep;

    CharStat(int source, char c, long count) {

        this.source = source;
        this.c = c;
        this.count = count;
        stringRep = buildStringRep();
    }

    static CharStat from(char c, String s1, String s2) {

        long count1 = s1.chars().filter(ch -> ch == c).count();
        long count2 = s2.chars().filter(ch -> ch == c).count();
        return count1 > count2 ? new CharStat(1, c, count1)
                : count1 == count2 ? new CharStat(0, c, count1)
                : new CharStat(2, c, count2);
    }

    boolean isValid() {

        return count > 1;
    }

    private String buildStringRep() {

        String src = source == 0 ? "=" : String.valueOf(source);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return String.format("%s:%s", src, sb);
    }

    @Override
    public int compareTo(CharStat o) {

        int byCount = Long.compare(o.count, count);
        if (byCount != 0) {
            return byCount;
        }
        return stringRep.compareTo(o.stringRep);
    }

    @Override
    public String toString() {

        return stringRep;
    }
}
