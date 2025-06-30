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

    public Candidate winner() throws NoWinnerException {
        if (votes.isEmpty()) throw new NoWinnerException("No votes cast.");

        for (Candidate candidate : candidates) {
            double totalPercent = 0.0;
            for (Vote vote : votes) {
                totalPercent += vote.percentage(candidate);
            }
            if (totalPercent > 50.0) {
                return candidate;
            }
        }
        throw new NoWinnerException("No clear winner");
    }

    public List<Candidate> runOffCandidates(){
        List<Candidate> runOffs = new ArrayList<>();
        List<Candidate> tempC = new ArrayList<>();
        List<Vote> tempV = new ArrayList<>();
        for(Candidate candidate : candidates) tempC.add(new Candidate(candidate.name()));
        for(Vote vote : votes) tempV.add(new Vote(vote.getLocation(), vote.getVotesForCandidate()));
        int i=0;
        while(i<2) {
            double max=0.0;
            Candidate maxC=null;
            for (Candidate candidate : tempC) {
                double totalPercent = 0.0;
                for (Vote vote : tempV) {
                    totalPercent += vote.percentage(candidate);
                }
                if (totalPercent > max) {
                    max=totalPercent;
                    maxC = candidate;
                }
            }
            runOffs.add(maxC);
            tempC.remove((maxC));
            i++;
        }
        return runOffs;
    }

    // w klasie ElectionTurn
    public Vote summarize() {
        return Vote.summarize(this.votes);
    }

    public Vote summarize(List<String> location) {
        List<Vote> filtered = Vote.filterByLocation(this.votes, location);
        return Vote.summarize(filtered, location);
    }

    public ElectionTurn(List<Candidate> candidates) {
        this.candidates = candidates;
    }


}
