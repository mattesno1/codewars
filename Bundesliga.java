import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** https://www.codewars.com/kata/57c178e16662d0d932000120 */
public class Bundesliga {

    public static String table(String[] results) {

        Table table = new Table();
        Arrays.stream(results).map(Match::fromString).forEach(table::addMatch);
        return table.prettyString();
    }
}

class Table {

    private static final String LINE_FORMAT = "%2s. %-30s%d  %d  %d  %d  %s  %d";

    private Map<Team, Stats> table = new HashMap<>();

    public void addMatch(Match match) {
        table.merge(match.homeTeam, Stats.homeTeam(match), Stats::merge);
        table.merge(match.awayTeam, Stats.awayTeam(match), Stats::merge);
    }

    public String prettyString() {

        List<Entry> entries = table.entrySet().stream()
                .map(e -> new Entry(e.getKey(), e.getValue()))
                .sorted()
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        int rank = 1;
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            Team team = entry.team;
            Stats stats = entry.stats;

            if (i > 0) {
                sb.append('\n');
                if (entries.get(i - 1).stats.compareTo(stats) < 0) {
                    rank = i + 1;
                }
            }
            sb.append(String.format(LINE_FORMAT, rank, team.name, stats.matchesPlayed, stats.wins, stats.ties, stats.losses, stats.totalScore, stats.points));
        }
        return sb.toString();
    }

    static class Entry implements Comparable<Entry> {
        final Team team;
        final Stats stats;

        public Entry(Team team, Stats stats) {
            this.team = team;
            this.stats = stats;
        }

        @Override
        public int compareTo(Entry o) {

            int byStats = stats.compareTo(o.stats);
            if (byStats != 0) {
                return byStats;
            }
            return team.compareTo(o.team);
        }
    }

}

class Match {

    private static final Pattern PATTERN = Pattern.compile("^([\\d\\-]+):([\\d\\-]+) (.*?) - (.*?)$");

    final Team homeTeam;
    final Team awayTeam;
    final int homeScore;
    final int awayScore;
    final boolean played;

    public Match(Team homeTeam, Team awayTeam, int homeScore, int awayScore, boolean played) {

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.played = played;
    }

    static Match fromString(String matchString) {

        Matcher matcher = PATTERN.matcher(matchString);
        if (matcher.matches()) {
            String score1 = matcher.group(1);
            String score2 = matcher.group(2);
            boolean played = false;
            int homeScore = 0;
            int awayScore = 0;
            if (!"-".equals(score1)) {
                played = true;
                homeScore = Integer.parseInt(score1);
                awayScore = Integer.parseInt(score2);
            }
            Team homeTeam = new Team(matcher.group(3));
            Team awayTeam = new Team(matcher.group(4));
            return new Match(homeTeam, awayTeam, homeScore, awayScore, played);
        }
        return null;
    }
}

class Score implements Comparable<Score> {

    final int goalsShot;
    final int goalsGotten;

    Score(int goalsShot, int goalsGotten) {
        this.goalsShot = goalsShot;
        this.goalsGotten = goalsGotten;
    }

    public Score add(Score other) {

        return new Score(goalsShot + other.goalsShot, goalsGotten + other.goalsGotten);
    }

    @Override
    public int compareTo(Score o) {

        int byDifference = Integer.compare(o.goalsShot - o.goalsGotten, goalsShot - goalsGotten);
        if (byDifference != 0) {
            return byDifference;
        }
        int byGoalsShot = Integer.compare(o.goalsShot, goalsShot);
        if (byGoalsShot != 0) {
            return byGoalsShot;
        }

        return 0;
    }

    @Override
    public String toString() {

        return String.format("%d:%d", goalsShot, goalsGotten);
    }
}

class Stats implements Comparable<Stats> {

    final int matchesPlayed;
    final int wins;
    final int ties;
    final int losses;
    final Score totalScore;
    final int points;

    public Stats(int matchesPlayed, int wins, int ties, int losses, Score totalScore, int points) {

        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.ties = ties;
        this.losses = losses;
        this.totalScore = totalScore;
        this.points = points;
    }

    @Override
    public int compareTo(Stats o) {

        int byPoints = Integer.compare(o.points, points);
        if (byPoints != 0) {
            return byPoints;
        }
        return totalScore.compareTo(o.totalScore);
    }

    public static Stats homeTeam(Match match) {

        return stats(match.homeScore, match.awayScore, match.played);
    }

    public static Stats awayTeam(Match match) {

        return stats(match.awayScore, match.homeScore, match.played);
    }

    private static Stats stats(int shot, int gotten, boolean played) {

        boolean win = played && shot > gotten;
        boolean tie = played && shot == gotten;
        boolean loss = played && shot < gotten;
        return new Stats(played ? 1 : 0, win ? 1 : 0, tie ? 1 : 0 , loss ? 1 : 0, new Score(shot, gotten), win ? 3 : tie ? 1 : 0);
    }

    public static Stats merge(Stats stats1, Stats stats2) {

        return new Stats(stats1.matchesPlayed + stats2.matchesPlayed,
                stats1.wins + stats2.wins,
                stats1.ties + stats2.ties,
                stats1.losses + stats2.losses,
                stats1.totalScore.add(stats2.totalScore),
                stats1.points + stats2.points);
    }
}

class Team implements Comparable<Team> {

    final String name;
    private final String lowerName;

    public Team(String name) {
        this.name = name;
        lowerName = name.toLowerCase();
    }

    @Override
    public int compareTo(Team o) {
        return lowerName.compareTo(o.lowerName);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        return Objects.equals(lowerName, team.lowerName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lowerName);
    }
}
