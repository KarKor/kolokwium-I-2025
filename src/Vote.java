import java.util.*;

public class Vote {
    private static Map<Candidate, Integer> votesForCandidate = new HashMap<>();
    private static List<String> location = new ArrayList<>();

    public Vote(List<String> location, Map<Candidate, Integer> votesForCandidate) {
        this.location = location;
        this.votesForCandidate = votesForCandidate;
    }

    public List<String> getLocation() {
        return location;
    }

    public static void setLocation(List<String> location) {
        Vote.location = location;
    }

    public static Map<Candidate, Integer> getVotesForCandidate() {
        return votesForCandidate;
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

    // w klasie Vote
    public static Vote summarize(List<Vote> votes, List<String> location) {
        Vote summary = summarize(votes); // zakładamy, że istnieje już metoda summarize(List<Vote>)
        summary.setLocation(location);   // metoda setLocation ustawia lokalizację w obiekcie Vote
        return summary;
    }



    public Integer votes(Candidate candidate){
        Integer voteCount = votesForCandidate.get(candidate);
        return voteCount;
    }

    public double percentage(Candidate candidate){
        int sum = 0;
        for (int count : votesForCandidate.values()) { //pętla sumująca wszystkie wartości z mapy
            sum += count;
        }
        Integer voteCount = votesForCandidate.get(candidate);

        if(sum ==0 || voteCount == null) return 0.0;
        return ((double) voteCount / sum)*100.00;
    }

    public Integer getVoteSum(){
        int sum = 0;
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
            sb.append(": ").append(String.format("%.2f", percent)).append("%\n");
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

    public static List<Vote> filterByLocation(List<Vote> votes, List<String> locations){
        List<Vote> filtered = new ArrayList<>();
        if(locations.size()==1){
            for(Vote vote: votes) {
                if (Objects.equals(vote.getLocation().getFirst(), locations.getFirst())) {
                    filtered.add(vote);
                }
            }
        } else if (locations.size()==2) {
            for(Vote vote: votes) {
                if (Objects.equals(vote.getLocation().get(0), locations.get(0))
                && Objects.equals(vote.getLocation().get(1), locations.get(1))) {
                    filtered.add(vote);
                }
            }
        } else if (locations.size()==3) {
            for(Vote vote: votes) {
                if (Objects.equals(vote.getLocation().get(0), locations.get(0))
                        && Objects.equals(vote.getLocation().get(1), locations.get(1))
                        && Objects.equals(vote.getLocation().get(2), locations.get(2))) {
                    filtered.add(vote);
                }
            }

        }

        if(filtered.isEmpty()) System.out.println("No matches found");
        return filtered;
    }
}
