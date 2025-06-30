import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Election {
    private List<Candidate> candidates = new ArrayList<>();
    private ElectionTurn firstTurn = new ElectionTurn(candidates);
    private ElectionTurn secondTurn = null;
    private List<Candidate> runoffs = new ArrayList<>();

    private Candidate winner;

    public Candidate getWinner() {
        return winner;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public ElectionTurn getFirstTurn() {
        return firstTurn;
    }

    public ElectionTurn getSecondTurn() {
        return secondTurn;
    }

    public List<Candidate> copyCandidates(){
        List<Candidate> candidatesCopy = new ArrayList<>();
        for(Candidate candidate : candidates){
            candidatesCopy.add(candidate);
        }

        return candidatesCopy;
    }

    private void populateCandidates(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                candidates.add(new Candidate(line));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());;
        }
    }

    public void populate(){
        populateCandidates("kandydaci.txt");

        firstTurn.populate("1.csv");

        try{
            winner = firstTurn.winner();
        }catch (NoWinnerException e) {
            System.out.println(e.getMessage());
            runoffs = firstTurn.runOffCandidates();
            secondTurn = new ElectionTurn(runoffs);
            secondTurn.populate("2.csv");
        }
        try {
            if(secondTurn!=null) {
                winner = secondTurn.winner();
            }
        }catch (NoWinnerException e){
            System.out.println(e.getMessage());
        };

    }


}
