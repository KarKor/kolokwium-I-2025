import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// W SelectableMap
public class SelectableMap extends VoivodeshipMap {
    private String selected = null;

    public void select(String voivodeship) {
        selected = voivodeship;
    }

    @Override
    protected String getColor(String voivodeship) {
        if (voivodeship.equals(selected)) {
            return "#FF0000"; // wyróżniony kolor
        }
        return "#000000";
    }

    @Override
    public void saveToSvg(String filePath) {
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\" width=\"497\" height=\"463\" viewBox=\"0 0 497 463\">\n");
        getVoivodeshipPaths().forEach((voivodeship, d) -> {
            svgBuilder.append(String.format("<path d=\"%s\" style=\"fill:%s\" id=\"%s\"/>\n", d, getColor(voivodeship), voivodeship));
        });
        svgBuilder.append("</svg>");
        try {
            Files.write(Paths.get(filePath), svgBuilder.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}