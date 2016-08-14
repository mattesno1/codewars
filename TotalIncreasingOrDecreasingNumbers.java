import java.math.BigInteger;

/** https://www.codewars.com/kata/55b195a69a6cc409ba000053 */
public class TotalIncreasingOrDecreasingNumbers {

  public static BigInteger totalIncDec(int x) {
  
    // 1. increasing numbers: draw x from (0, ..., 9) with replacement
    // 2. decreasing numbers: draw x from (0, ..., 9, 0') with replacement
    //    we have two zeroes, one regular digit and one leading (invisible) zero
    // 3. 9*x 'constant' numbers (111, 222, ...) were counted twice
    // 4. x+1 zeroes can be drawn from the decreasing pot which also
    //    have to be subtracted as we have already have a zero:
    //        x * 0
    //        (x-1) * 0 + 0'
    //        ...
    //        0 + (x-1)*0'
    //        x * 0'
    
    BigInteger increasing = binomial(x + 9, x);
    BigInteger decreasing = binomial(x + 10, x);
    BigInteger duplicates = BigInteger.valueOf(10 * x + 1);
    
    return increasing.add(decreasing).subtract(duplicates);
  }

  private static BigInteger binomial(int n, int k) {
  
    if (k == 0) {
      return BigInteger.ONE;
    }
    if (k == 1) {
      return BigInteger.valueOf(n);
    }
    return BigInteger.valueOf(n)
             .multiply(binomial(n - 1, k - 1))
             .divide(BigInteger.valueOf(k));
    }
}
