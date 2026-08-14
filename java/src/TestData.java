import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Loads Split the Playlist test cases from ../test_data/{simple,medium,hard}/*.txt
 * (relative to the java/ directory — the scripts in java/scripts/ always run
 * from there).
 *
 * File format:
 *
 *     name=&lt;case name&gt;
 *     d=&lt;int&gt;
 *     expected=&lt;long&gt;
 *     songs=
 *     &lt;duration 0&gt;
 *     &lt;duration 1&gt;
 *     ...
 */
public class TestData {

    static final String TEST_DATA_DIR = "../test_data";

    static class TestCase {
        final String name;
        final int[] songs;
        final int d;
        final long expected;

        TestCase(String name, int[] songs, int d, long expected) {
            this.name = name;
            this.songs = songs;
            this.d = d;
            this.expected = expected;
        }
    }

    static TestCase loadCase(String path) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        String name = null;
        int d = 0;
        long expected = 0;
        int songsStart = -1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.equals("songs=")) {
                songsStart = i + 1;
                break;
            }
            int eq = line.indexOf('=');
            String key = line.substring(0, eq);
            String value = line.substring(eq + 1);
            if (key.equals("name")) {
                name = value;
            } else if (key.equals("d")) {
                d = Integer.parseInt(value);
            } else if (key.equals("expected")) {
                expected = Long.parseLong(value);
            }
        }

        List<String> songLines = lines.subList(songsStart, lines.size());
        int[] songs = new int[songLines.size()];
        for (int i = 0; i < songs.length; i++) {
            songs[i] = Integer.parseInt(songLines.get(i));
        }
        return new TestCase(name, songs, d, expected);
    }

    static List<TestCase> loadTier(String tier) throws IOException {
        File dir = new File(TEST_DATA_DIR, tier);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        List<TestCase> cases = new ArrayList<>();
        if (files != null) {
            Arrays.sort(files);
            for (File f : files) {
                cases.add(loadCase(f.getPath()));
            }
        }
        return cases;
    }
}
