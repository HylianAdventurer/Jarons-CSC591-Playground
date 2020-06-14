public class Main {
    public static void main(String[] args) {
        double t = System.nanoTime();
        Sudoku problem = new Sudoku(3,false,0.7);
        System.out.println("Problem");
        System.out.println(problem);
        System.out.println("Solution");
        while(!problem.kempesColoring(9));
        System.out.println(problem);
        System.out.println((System.nanoTime()-t)/1000000000 + " s");
    }
}
