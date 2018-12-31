import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws IOException {
        var values = Files.lines(Path.of("../resources/2018/day-02/input.txt"))
                          .collect(Collectors.toUnmodifiableList());

        System.out.println("part 1 -> " + part1(values));
        System.out.println("part 2 -> " + part2(values));
    }

    public static Integer part1(List<String> values) {
        return values.stream()
                     .map(String::chars)
                     .map(s -> s.boxed().collect(Collectors.groupingBy(i -> i, Collectors.counting())))
                     .map(map -> Map.entry(map.containsValue(2L) ? 1 : 0, map.containsValue(3L) ? 1 : 0))
                     .reduce((t, n) -> Map.entry(t.getKey() + n.getKey(), t.getValue() + n.getValue()))
                     .map(e -> e.getKey() * e.getValue())
                     .get();
    }

    public static String part2(List<String> values) {
        var copy = new LinkedList<String>(values);
        return values.stream()
                     .peek(s -> copy.removeFirst())
                     .flatMap(s -> copy.stream().map(e -> Map.entry(s, e)))
                     .map(Main::intersection)
                     .dropWhile(s -> s.length() != values.get(0).length() - 1)
                     .findFirst()
                     .get();
    }

    public static String intersection(Map.Entry<String, String> e) {
        var k = e.getKey();
        var v = e.getValue();
        return IntStream.range(0, k.length())
                        .mapToObj(i -> k.charAt(i) == v.charAt(i) ? "" + k.charAt(i)  : "")
                        .collect(Collectors.joining());
    }
}