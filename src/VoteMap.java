import java.util.Map;

public class VoteMap extends VoivodeshipMap {
    private final Map<String, String> winnerByVoivodeship; // województwo -> kandydat
    private final Map<String, String> candidateColors;     // kandydat -> kolor

    public VoteMap(Map<String, String> winnerByVoivodeship, Map<String, String> candidateColors) {
        this.winnerByVoivodeship = winnerByVoivodeship;
        this.candidateColors = candidateColors;
    }

    @Override
    protected String getColor(String voivodeship) {
        String candidate = winnerByVoivodeship.get(voivodeship);
        return candidateColors.getOrDefault(candidate, "#888888");
    }
}