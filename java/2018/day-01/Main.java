import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        var values = Files.lines(Path.of("../resources/2018/day-01/input.txt"))
                          .map(Integer::parseInt)
                          .collect(Collectors.toUnmodifiableList());

        System.out.println("part 1 -> " + part1(values));
        System.out.println("part 2 -> " + part2(values));
    }

    private static int part1(List<Integer> values) {
        return values.stream().reduce(0, Integer::sum);
    }

    private static int part2(List<Integer> values) {
        var seen = new HashSet<>();
        var sum = new AtomicInteger(0);
        Stream.generate(() -> values)
              .flatMap(List::stream)
              .dropWhile(i -> seen.add(sum.addAndGet(i)))
              .findFirst();
        return sum.get();
    }
}