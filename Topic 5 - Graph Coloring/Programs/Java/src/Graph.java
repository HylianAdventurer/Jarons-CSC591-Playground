import java.util.*;

public class Graph {

    protected int[][] edges;
    protected int[] colors;
    public boolean directional;

    public Graph(int numOfVertices) {
        this(numOfVertices,false);
    }

    public Graph(int numOfVertices, boolean directional) {
        edges = new int[numOfVertices][numOfVertices];
        colors = new int[numOfVertices];
        this.directional = directional;
    }

    public int size() {
        return edges.length;
    }

    public int getColor(int vertex) {
        return colors[vertex];
    }

    public void setColor(int vertex, int color) {
        colors[vertex] = color;
    }

    public List<Integer> getNeighbors(int vertex) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < size(); i++)
            if (connected(vertex, i))
                result.add(i);
        return result;
    }

    public int addEdge(int vertex1, int vertex2) {
        return addEdge(vertex1,vertex2,1);
    }

    public int addEdge(int vertex1, int vertex2, int weight) {
        int result = edges[vertex1][vertex2];
        edges[vertex1][vertex2] = weight;
        if(!directional) edges[vertex2][vertex1] = weight;
        return result;
    }

    public int removeEdge(int vertex1, int vertex2) {
        return addEdge(vertex1,vertex2,0);
    }

    public int connectedWeight(int vertex1, int vertex2) {
        return edges[vertex1][vertex2];
    }

    public boolean connected(int vertex1, int vertex2) {
        return connectedWeight(vertex1,vertex2) > 0;
    }

    public void resetColoring() {
        for(int i = 0; i < size(); i++) setColor(i,0);
    }

    public Graph clone() {
        Graph result = new Graph(size());
        result.edges = edges.clone();
        result.colors = colors.clone();
        result.directional = directional;
        return result;
    }

    public boolean kempesColoring(int maxColor) {
        Random rng = new Random();
        List<Integer> remaining = new ArrayList<>(), firstChoice = new ArrayList<>();
        Stack<Integer> removed = new Stack<>();
        int[] tempColors = colors.clone();
        for(int i = 0; i < size(); i++)
            if(tempColors[i] == 0) remaining.add(i);

        while(!remaining.isEmpty()) {
            for(int vertex : remaining)
                if(!firstChoice.contains(vertex)) {
                    List<Integer> remainingNeighbors = getNeighbors(vertex);
                    remainingNeighbors.removeAll(removed);
                    if (remainingNeighbors.size() < maxColor) firstChoice.add(vertex);
                }
            Integer vertex;
            if(firstChoice.isEmpty()) {
                vertex = remaining.remove(rng.nextInt(remaining.size()));
            } else {
                vertex = firstChoice.remove(rng.nextInt(firstChoice.size()));
                remaining.remove(vertex);
            }
            removed.add(vertex);
        }

        for(int vertex : removed)
            if (k_assignColor(vertex,tempColors) > maxColor)
                return false;
        colors = tempColors;
        return true;
    }

    private int k_assignColor(int vertex, int[] colors) {
        int j;
        for(j = 1; k_neighborColored(vertex,j,colors); j++);
        colors[vertex] = j;
        return j;
    }

    private boolean k_neighborColored(int vertex, int color, int[] colors) {
        for (int neighbor : getNeighbors(vertex))
            if (colors[neighbor] == color)
                return true;
        return false;
    }

    public int greedyColoring() {
        resetColoring();
        int maxColor = 1;
        List<Integer> visited = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);

        while(visited.size() < size()) {
            if(queue.isEmpty())
                for(int i = 0; i < size(); i++)
                    if(!visited.contains(i)) {
                        queue.add(i);
                        break;
                    }
            int vertex = queue.remove();
            visited.add(vertex);
            maxColor = Math.max(maxColor,assignColor(vertex));
            for(int neighbor : getNeighbors(vertex))
                if(!visited.contains(neighbor) && !queue.contains(neighbor))
                    queue.add(neighbor);
        }

        return maxColor;
    }

    private int assignColor(int vertex) {
        int j;
        for(j = 1; neighborColored(vertex,j); j++);
        setColor(vertex, j);
        return j;
    }

    private boolean neighborColored(int vertex, int color) {
        for (int neighbor : getNeighbors(vertex))
            if (getColor(neighbor) == color)
                return true;
        return false;
    }
}
