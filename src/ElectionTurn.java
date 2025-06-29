import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElectionTurn {
    private List<Candidate> candidates = new ArrayList<>();
    private List<Vote> votes = new ArrayList<>();

    public void populate(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                Vote vote = Vote.fromCsvLine(line, candidates);
                votes.add(vote);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public ElectionTurn(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
