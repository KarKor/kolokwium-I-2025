import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Vote> votes = new ArrayList<>();
        Election election = new Election();
        election.populate();
        VoivodeshipMap voivodeshipMap = new VoivodeshipMap();

        voivodeshipMap.saveToSvg("map.svg");

        votes = readCsv("1.csv", election.getCandidates());

        Vote summarized = Vote.summarize(votes);
        System.out.println(summarized.toString());
    }

    public static List<Vote> readCsv(String filePath, List<Candidate> candidates){
        List<Vote> votes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int linecounter = 0;
            while ((line = br.readLine()) != null) {
                if(linecounter>0) votes.add(Vote.fromCsvLine(line, candidates));
                linecounter++;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());;
        }
        return votes;
    }
}
