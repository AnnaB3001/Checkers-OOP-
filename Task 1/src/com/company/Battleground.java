package basic.pack;

import java.util.HashMap;
import java.util.Map;

public class Battleground {
    private Map<Integer, Figure> contentOfVertex = new HashMap<>();
    private Graph field = new AdjListsGraph();

    public Battleground() {
        createGraph();

    }

    private void createGraph() {
        for (int i = 0; i < 28; i++) {        // создание рёбер между вершинами графа
            field.addAdge(i, i + 4);
            if (i != 0 && i % 4 != 0) {
                field.addAdge(i, i + 3);
            }
        }

        Figure buffer;

        for (int j = 0; j < 12; j++) {           // заполнение вершин шашками первого игрока
            buffer = new Checker(true);
            contentOfVertex.put(j, buffer);

        }

        for (int j = 20; j < 32; j++) {            // заполнение вершин шашками второго игрока
            buffer = new Checker(false);
            contentOfVertex.put(j, buffer);

        }


    }

    @Override
    public String toString() {                          // отрисовка поля в консоли
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        Figure buffer;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                if (i % 2 == 0) {
                    buffer = contentOfVertex.get(counter);
                    if(buffer == null){
                        builder.append(" ").append(".");
                        counter++;
                        continue;
                    }
                    builder.append(buffer.toString()).append(".");
                } else {
                    buffer = contentOfVertex.get(counter);
                    if(buffer == null){
                        builder.append(".").append(" ");
                        counter++;
                        continue;
                    }
                    builder.append(".").append(buffer.toString());
                }
                counter++;
            }
            builder.append("\n");
        }
        return builder.toString();
    }


}
