import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Sudoku extends Graph {
    public Sudoku() {
        this(3, false, 1);
    }

    public Sudoku(int size) {
        this(size,false, 1);
    }
    public Sudoku(int size, double perRemaining) {
        this(size,false,perRemaining);
    }

    public Sudoku(boolean empty) {
        this(3,empty, 1);
    }

    public Sudoku(boolean empty, double perRemaining) {
        this(3,empty,perRemaining);
    }

    public Sudoku(int size, boolean empty) {
        this(size,empty,1);
    }

    public Sudoku(int size, boolean empty, double perRemaining) {
        super((int) Math.pow(size,4),false);
        for(int i = 0; i < size(); i++)
            for(int j = i+1; j < size(); j++) {
                if (i != j && ((int) (i / Math.pow(size, 2)) == (int) (j / Math.pow(size, 2)) || (int) (i % Math.pow(size, 2)) == (int) (j % Math.pow(size, 2)) || section(i) == section(j)))
                    addEdge(i, j);
            }
        if(!empty) {
            while(!kempesColoringLargest((int)Math.pow(size,2)));
            Random rng = new Random();
            List<Integer> remaining = new ArrayList<>();
            for(int i = 0; i < size(); i++) remaining.add(i);
            for(int i = 0; i < size()*(1-perRemaining); i++)
                setColor(remaining.remove(rng.nextInt(remaining.size())),0);
        }
    }

    public Sudoku(int size, boolean empty, double perRemaining, boolean random) {
        super((int) Math.pow(size,4),false);
        for(int i = 0; i < size(); i++)
            for(int j = i+1; j < size(); j++) {
                if (i != j && ((int) (i / Math.pow(size, 2)) == (int) (j / Math.pow(size, 2)) || (int) (i % Math.pow(size, 2)) == (int) (j % Math.pow(size, 2)) || section(i) == section(j)))
                    addEdge(i, j);
            }
        if(!empty) {
            while(random ? !kempesColoringRandom((int)Math.pow(size,2)) : !kempesColoringLargest((int)Math.pow(size,2)));
            Random rng = new Random();
            List<Integer> remaining = new ArrayList<>();
            for(int i = 0; i < size(); i++) remaining.add(i);
            for(int i = 0; i < size()*(1-perRemaining); i++)
                setColor(remaining.remove(rng.nextInt(remaining.size())),0);
        }
    }

    public boolean kempesColoring() {
        return kempesColoringLargest((int)Math.sqrt(size()));
    }

    public Sudoku clone() {
        Sudoku result = new Sudoku(false);
        result.edges = edges.clone();
        result.colors = colors.clone();
        result.directional = directional;
        return result;
    }

    private int section(int vertex) {
        int size = (int)Math.sqrt(Math.sqrt(size()));
        return size * (vertex / (int)Math.pow(size,3)) + (vertex % (int)Math.pow(size,2)) / size;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        int size = (int)Math.sqrt(Math.sqrt(size()));

        for(int vertex = 0; vertex < size(); vertex++) {
            if(vertex % Math.pow(size,3) == 0 && vertex != 0) {
                result.append("\n");
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        result.append("----");
                    }
                    result.append("+");
                }
                result.replace(result.length()-1,result.length(),"\n");
            } else if(vertex % Math.pow(size,2) == 0 && vertex != 0){
                result.append("\n");
            } else if(vertex % size == 0 && vertex != 0) {
                result.append("|");
            }
            result.append(String.format(getColor(vertex) == 0 ? " __ " : " %2d ", getColor(vertex)));
        }

        return result.toString();
    }
}
