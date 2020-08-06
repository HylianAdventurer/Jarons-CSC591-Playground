import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Testing {
    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("Output.csv"));

        Sudoku[] Problems = new Sudoku[10];
        double time;
        int nanoToSec = 1000000000, timeOutLimit = 100000, size = 3, cycles;
        String startingString = "Rand Time 0%,Rand Cycles 0%,Large Time 0%,Large Cycles 0%,Small Time 0%,Small Cycles 0%,Rand Precolored Time 0%,Rand Precolored Cycles 0%,Large Precolored Time 0%,Large Precolored Cycles 0%,Small Precolored Time 0%,Small Precolored Cycles 0%,"
                + "Rand Time 10%,Rand Cycles 10%,Large Time 10%,Large Cycles 10%,Small Time 10%,Small Cycles 10%,Rand Precolored Time 10%,Rand Precolored Cycles 10%,Large Precolored Time 10%,Large Precolored Cycles 10%,Small Precolored Time 10%,Small Precolored Cycles 10%,"
                + "Rand Time 20%,Rand Cycles 20%,Large Time 20%,Large Cycles 20%,Small Time 20%,Small Cycles 20%,Rand Precolored Time 20%,Rand Precolored Cycles 20%,Large Precolored Time 20%,Large Precolored Cycles 20%,Small Precolored Time 20%,Small Precolored Cycles 20%,"
                + "Rand Time 30%,Rand Cycles 30%,Large Time 30%,Large Cycles 30%,Small Time 30%,Small Cycles 30%,Rand Precolored Time 30%,Rand Precolored Cycles 30%,Large Precolored Time 30%,Large Precolored Cycles 30%,Small Precolored Time 30%,Small Precolored Cycles 30%,"
                + "Rand Time 40%,Rand Cycles 40%,Large Time 40%,Large Cycles 40%,Small Time 40%,Small Cycles 40%,Rand Precolored Time 40%,Rand Precolored Cycles 40%,Large Precolored Time 40%,Large Precolored Cycles 40%,Small Precolored Time 40%,Small Precolored Cycles 40%,"
                + "Rand Time 50%,Rand Cycles 50%,Large Time 50%,Large Cycles 50%,Small Time 50%,Small Cycles 50%,Rand Precolored Time 50%,Rand Precolored Cycles 50%,Large Precolored Time 50%,Large Precolored Cycles 50%,Small Precolored Time 50%,Small Precolored Cycles 50%,"
                + "Rand Time 60%,Rand Cycles 60%,Large Time 60%,Large Cycles 60%,Small Time 60%,Small Cycles 60%,Rand Precolored Time 60%,Rand Precolored Cycles 60%,Large Precolored Time 60%,Large Precolored Cycles 60%,Small Precolored Time 60%,Small Precolored Cycles 60%,"
                + "Rand Time 70%,Rand Cycles 70%,Large Time 70%,Large Cycles 70%,Small Time 70%,Small Cycles 70%,Rand Precolored Time 70%,Rand Precolored Cycles 70%,Large Precolored Time 70%,Large Precolored Cycles 70%,Small Precolored Time 70%,Small Precolored Cycles 70%,"
                + "Rand Time 80%,Rand Cycles 80%,Large Time 80%,Large Cycles 80%,Small Time 80%,Small Cycles 80%,Rand Precolored Time 80%,Rand Precolored Cycles 80%,Large Precolored Time 80%,Large Precolored Cycles 80%,Small Precolored Time 80%,Small Precolored Cycles 80%,"
                + "Rand Time 90%,Rand Cycles 90%,Large Time 90%,Large Cycles 90%,Small Time 90%,Small Cycles 90%,Rand Precolored Time 90%,Rand Precolored Cycles 90%,Large Precolored Time 90%,Large Precolored Cycles 90%,Small Precolored Time 90%,Small Precolored Cycles 90%,";


        System.out.println("Generating Problems...");
        for(int i = 0; i < 10; i++) {
            System.out.println("Generating " + i * 10 + "% Problem");
            Problems[i] = new Sudoku(size, ((double) i / 100));
        }

        pw.print(startingString);
        System.out.println("Testing...");
        System.out.println(startingString);

        for(int i = 1; i <= 1000; i++) {
            for(int j = 0; j < 10; j++) {
                Sudoku p = Problems[j].clone();
                time = System.nanoTime();
                for(cycles = 1; !p.kempesColoringRandom((int) Math.pow(size,2)) && cycles < timeOutLimit; cycles++);
                time = System.nanoTime() - time;
                pw.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                System.out.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                p = Problems[j].clone();
                time = System.nanoTime();
                for(cycles = 1; !p.kempesColoringLargest((int) Math.pow(size,2)) && cycles < timeOutLimit; cycles++);
                time = System.nanoTime() - time;
                pw.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                System.out.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                p = Problems[j].clone();
                time = System.nanoTime();
                for(cycles = 1; !p.kempesColoringSmallest((int) Math.pow(size,2)) && cycles < timeOutLimit; cycles++);
                time = System.nanoTime() - time;
                pw.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                System.out.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                p = Problems[j].clone();
                time = System.nanoTime();
                for(cycles = 1; !p.kempesColoringRandomPrecolored((int) Math.pow(size,2)) && cycles < timeOutLimit; cycles++);
                time = System.nanoTime() - time;
                pw.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                System.out.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                p = Problems[j].clone();
                time = System.nanoTime();
                for(cycles = 1; !p.kempesColoringLargestPrecolored((int) Math.pow(size,2)) && cycles < timeOutLimit; cycles++);
                time = System.nanoTime() - time;
                pw.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                System.out.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                p = Problems[j].clone();
                time = System.nanoTime();
                for(cycles = 1; !p.kempesColoringSmallestPrecolored((int) Math.pow(size,2)) && cycles < timeOutLimit; cycles++);
                time = System.nanoTime() - time;
                pw.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
                System.out.print(time/nanoToSec + "," + (cycles < timeOutLimit ? cycles : "TIMED OUT") + ",");
            }
            pw.println();
            System.out.println();
        }
        System.out.println("DONE");

        pw.close();
    }
}
