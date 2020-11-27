package checker;

import java.util.HashMap;
import java.util.Map;

public class Maps {
    private Map<Figure, String> cells = new HashMap<>();
    private Map<String, Figure> figures = new HashMap<>();

    public Map<Figure, String> getCells() {
        return cells;
    }

    public Map<String, Figure> getFigures() {
        return figures;
    }
}
