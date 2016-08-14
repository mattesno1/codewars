import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;

/** https://www.codewars.com/kata/55ec80d40d5de30631000025 */
public class NbInSum {
    
    public static long[][] decompose(long n) {

        List<Integer> exponents = new ArrayList<>();
        long remainder = n;

        for (int base = 2; remainder >= base * base; base++) {

            int exp = 0;
            long pow = 1;
            for (long l = remainder; l >= base; l = l / base) {
                exp++;
                pow *= base;
            }

            exponents.add(exp);
            remainder -= pow;
        }

        return new long[][]{
                exponents.stream().mapToLong(Integer::longValue).toArray(),
                {remainder}
        };
    }
}
