import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Brainfuck Interpreter.
 * https://www.codewars.com/kata/526156943dfe7ce06200063e
 */
public class BrainLuck {

    private final String code;
    private final Memory memory;

    public BrainLuck(String code) {

        this.code = code;
        this.memory = new Memory();
    }

    public String process(String input) {

        InputReader inputReader = new InputReader(input);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            switch (code.charAt(i)) {
                case '>':
                    memory.shiftRight();
                    break;
                case '<':
                    memory.shiftLeft();
                    break;
                case '+':
                    memory.setByte(memory.getByte() + 1);
                    break;
                case '-':
                    memory.setByte(memory.getByte() - 1);
                    break;
                case '.':
                    sb.append(memory.getByte());
                    break;
                case ',':
                    memory.setByte(inputReader.accept());
                    break;
                case '[':
                    if (memory.getByte() == 0) {
                        i = jumpForward(i) - 1;
                    }
                    break;
                case ']':
                    if (memory.getByte() != 0) {
                        i = jumpBack(i);
                    }
                    break;
                default:
                    // ignore comment
            }
        }
        return sb.toString();
    }

    private int jumpForward(int instructionPointer) {
        return jump(instructionPointer, 1);
    }

    private int jumpBack(int instructionPointer) {
        return jump(instructionPointer, -1);
    }

    private int jump(int position, int direction) {

        Stack<Object> tracker = new Stack<>();

        char start = direction == 1 ? '[' : ']';
        char stop  = direction == 1 ? ']' : '[';

        for (int i = position + direction; i >= 0 && i < code.length(); i = i + direction) {
            if (code.charAt(i) == start) {
                tracker.push(null);
            }
            if (code.charAt(i) == stop) {
                if (tracker.isEmpty()) {
                    return i;
                }
                tracker.pop();
            }
        }
        return -1;
    }
}

class InputReader {

    final String input;
    int pointer = 0;

    public InputReader(String input) {
        this.input = input;
    }

    char accept() {
        char c = input.charAt(pointer);
        pointer = (pointer + 1) % input.length();
        return c;
    }

}

class Memory {

    private static final int BLOCK_SIZE = 32;

    private List<char[]> memory = new ArrayList<>();
    private int pointer = 0;

    private void allocate() {
        memory.add(new char[BLOCK_SIZE]);
    }

    public void shiftRight() {
        pointer++;
    }

    public void shiftLeft() {
        pointer--;
    }

    public char getByte() {

        return getBlock()[pointer % BLOCK_SIZE];
    }

    public void setByte(int value) {

        char[] block = getBlock();
        block[pointer % BLOCK_SIZE] = (char) (value >= 0 ? value % 256 : 255);
    }

    private char[] getBlock() {

        int block = pointer / BLOCK_SIZE;
        while (memory.size() <= block) {
            allocate();
        }
        return memory.get(block);
    }
}
