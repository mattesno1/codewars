import java.util.ArrayDeque;
import java.util.Deque;

/** https://www.codewars.com/kata/577e9095d648a15b800000d4 */
public class Evaluator {

    private static final String OPERATORS = "+-*/";

    public long evaluate(String expression) {

        Deque<Long> numbers = new ArrayDeque<>();

        String[] strings = expression.split("\\s");
        for (String s : strings) {
            if (isOperator(s)) {
                Long last = numbers.pop();
                numbers.push(performOperation(numbers.pop(), last, s));
            } else {
                numbers.push(Long.valueOf(s));
            }
        }

        return numbers.pop();
    }

    private boolean isOperator(String s) {
        return OPERATORS.contains(s);
    }

    private long performOperation(long n1, long n2, String operation) {
        switch (operation) {
            case "+":
                return n1 + n2;
            case "-":
                return n1 - n2;
            case "*":
                return n1 * n2;
            case "/":
                return n1 / n2;
            default:
                throw new UnsupportedOperationException(operation);
        }
    }
}
