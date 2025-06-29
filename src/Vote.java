import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vote {
    private static Map<Candidate, Integer> votesForCandidate = new HashMap<>();
    private List<String> location = new ArrayList<>();

    public Vote(List<String> location, Map<Candidate, Integer> votesForCandidate) {
        this.location = location;
        this.votesForCandidate = votesForCandidate;
    }

    public static Vote summarize(List<Vote> votes){
        Map<Candidate, Integer> forCandidate = new HashMap<>();
        for(Vote vote : votes){ // pewnie ważne: metoda sumuje wszystkie głosy oddane na poszczególnych kandydatów z podanej listy
            for(Map.Entry<Candidate, Integer> entry : votesForCandidate.entrySet()){
                forCandidate.put(entry.getKey(),
                        forCandidate.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }
        return new Vote(new ArrayList<String>(),forCandidate);
    }

    public Integer votes(Candidate candidate){
        Integer voteCount = votesForCandidate.get(candidate);
        return voteCount;
    }

    public double percentage(Candidate candidate){
        Integer sum = 0;
        for (int count : votesForCandidate.values()) { //pętla sumująca wszystkie wartości z mapy
            sum += count;
        }
        Integer voteCount = votesForCandidate.get(candidate);

        if(sum ==0 || voteCount == null) return 0.0;
        double percentage = ((double) voteCount / sum)*100.00;
        return percentage;
    }

    public Integer getVoteSum(){
        Integer sum = 0;
        for (int count : votesForCandidate.values()) { //pętla sumująca wszystkie wartości z mapy
            sum += count;
        }
        return sum;
    }

    @Override
    public String toString(){
        int sum = getVoteSum();
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Candidate, Integer> entry : votesForCandidate.entrySet()){
            double percent = sum == 0 ? 0.0 : (double) votes(entry.getKey()) / sum * 100.0;
            sb.append(String.valueOf(entry.getKey()));
            sb.append(": " + String.format("%.2f", percent) + "%\n");
        }
        return sb.toString();
    }

    public static Vote fromCsvLine(String line, List<Candidate> candidates){
        Vote vote ;
        String parts[] = line.trim().split(",");
        List<String> loc = new ArrayList<>();

        int i=0;
        while (i<3){
            loc.add(parts[i]);
            i++;
        }
        Map<Candidate, Integer> votes = new HashMap<>();
        while(i<parts.length){
            Candidate candidate = candidates.get(i-3);
            int voteCount = Integer.parseInt(parts[i]);
            votes.put(candidate, voteCount);

            i++;
        }

        vote = new Vote(loc, votes);

        return vote;
    }
}
