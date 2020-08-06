import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        Graph Figure17_1 = new Graph(8);
//        int colors;
//
//        Figure17_1.addEdge(0,2);
//        Figure17_1.addEdge(1,2);
//        Figure17_1.addEdge(2,3);
//        Figure17_1.addEdge(3,4);
//        Figure17_1.addEdge(3,5);
//        Figure17_1.addEdge(4,6);
//        Figure17_1.addEdge(5,7);
//
//        for(int[] row : Figure17_1.edges)
//            System.out.println(Arrays.toString(row));
//        System.out.println();
//        colors = Figure17_1.greedyEdgeColoring();
//
//        for(int[] row : Figure17_1.edges)
//            System.out.println(Arrays.toString(row));
//        System.out.println(colors);
//
//        for(int i = 0; i < 100; i++)
//            System.out.println(Figure17_1.greedyEdgeColoring());

        Sudoku twoByTwo = new Sudoku(3,0.60);
        twoByTwo.kempesColoringRecursive(9);
        System.out.println(twoByTwo);
    }
}
