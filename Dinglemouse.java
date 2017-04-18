import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/** https://www.codewars.com/kata/57d7536d950d8474f6000a06 */
public class Dinglemouse {

    public static int[] findWrongWayCow(final char[][] field) {

        List<Cow> cows = getCowsFromField(field);
        Map<Orientation, List<Cow>> cowsByOrientation = groupCowsByOrientation(cows);

        return cowsByOrientation.values().stream()
                .filter(l -> l.size() == 1)
                .map(l -> l.get(0))
                .map(Cow::getPosition)
                .map(Position::toArray)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("no 'wrong way cow' found"));
    }

    private static Map<Orientation, List<Cow>> groupCowsByOrientation(List<Cow> cows) {

        return cows.stream().collect(Collectors.groupingBy(Cow::getOrientation));
    }

    private static List<Cow> getCowsFromField(char[][] field) {

        char [][] fieldClone = new char[field.length][];
        for(int i = 0; i < field.length; i++) {
            fieldClone[i] = field[i].clone();
        }

        List<Cow> cows = new ArrayList<>();

        boolean needSecondPass = true;
        while (needSecondPass) {
            int before = cows.size();
            needSecondPass = false;
            for (int y = 0; y < fieldClone.length; y++) {
                char[] row = fieldClone[y];
                for (int x = 0; x < row.length; x++) {
                    if (row[x] == 'c') { // cow found
                        Position position = new Position(x, y);
                        Optional<Orientation> orientation = determinOrientation(position, fieldClone);
                        if (orientation.isPresent()) {
                            Cow cow = new Cow(position, orientation.get());
                            cows.add(cow);
                            fieldClone = removeCowFromField(cow, fieldClone);
                        } else {
                            needSecondPass = true;
                        }
                    }
                }
            }
            if (before == cows.size()) {
                throw new RuntimeException("failed to get cows from field");
            }
        }

        return cows;
    }

    private static char[][] removeCowFromField(Cow cow, char[][] field) {

        int x = cow.getPosition().x;
        int y = cow.getPosition().y;

        field[y][x] = '.';
        switch (cow.getOrientation()) {
            case N:
                field[y+1][x] = '.';
                field[y+2][x] = '.';
                return field;
            case E:
                field[y][x-1] = '.';
                field[y][x-2] = '.';
                return field;
            case S:
                field[y-1][x] = '.';
                field[y-2][x] = '.';
                return field;
            case W:
                field[y][x+1] = '.';
                field[y][x+2] = '.';
                return field;
            default:
                throw new IllegalStateException();
        }
    }

    private static Optional<Orientation> determinOrientation(Position position, char[][] field) {

        EnumSet<Orientation> candidates = EnumSet.noneOf(Orientation.class);

        if (isNorthFacing(position, field)) {
            candidates.add(Orientation.N);
        }
        if (isEastFacing(position, field)) {
            candidates.add(Orientation.E);
        }
        if (isSouthFacing(position, field)) {
            candidates.add(Orientation.S);
        }
        if (isWestFacing(position, field)) {
            candidates.add(Orientation.W);
        }
        if (candidates.size() == 1) {
            return Optional.of(candidates.iterator().next());
        }
        return Optional.empty();
    }

    private static boolean isNorthFacing(Position position, char[][] field) {

        return position.y < field.length - 2 && field[position.y + 1][position.x] == 'o' && field[position.y + 2][position.x] == 'w';
    }

    private static boolean isEastFacing(Position position, char[][] field) {

        return position.x >= 2 && field[position.y][position.x - 1] == 'o' && field[position.y][position.x - 2] == 'w';
    }

    private static boolean isWestFacing(Position position, char[][] field) {

        return position.x < field[position.y].length - 2 && field[position.y][position.x + 1] == 'o' && field[position.y][position.x + 2] == 'w';
    }

    private static boolean isSouthFacing(Position position, char[][] field) {

        return position.y >= 2 && field[position.y - 1][position.x] == 'o' && field[position.y - 2][position.x] == 'w';
    }
}

enum Orientation {
    N, E, S, W
}

class Cow {
    Position position;
    Orientation orientation;

    public Cow(Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public Position getPosition() {
        return position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

}

class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int[] toArray() {
        return new int[]{x, y};
    }
}
