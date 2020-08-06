import java.awt.*;
import java.util.*;
import java.util.List;

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
            if(connected(vertex, i))
                result.add(i);
        return result;
    }

    public List<Rectangle> getNeighbors(Rectangle edge) {
        List<Rectangle> result = new ArrayList<>();
        for(int i = 0; i < size(); i++)
            for(int j = directional ? 0 : i; j < size(); j++)
                if(connected(edge, new Rectangle(i,j)))
                    result.add(new Rectangle(i, j));
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

    public boolean connected(Rectangle edge1, Rectangle edge2) {
        if(edges[edge1.width][edge1.height] == 0 || edges[edge2.width][edge2.height] == 0) return false;
        if(edge1.height == edge2.height && edge1.width == edge2.width) return false;
        if(!directional && edge1.height == edge2.height && edge1.width == edge2.height) return false;
        if(edge1.width == edge2.width || edge1.width == edge2.height || edge1.height == edge2.height || edge1.height == edge2.width) return true;
        return false;
    }

    public void resetColoring() {
        for(int i = 0; i < size(); i++) setColor(i,0);
    }

    public void resetWeights() {
        for(int i = 0; i < edges.length; i++)
            for(int j = 0; j < edges[0].length; j++)
                edges[i][j] = edges[i][j] == 0 ? 0 : 1;
    }

    public Graph clone() {
        Graph result = new Graph(size());
        result.edges = edges.clone();
        result.colors = colors.clone();
        result.directional = directional;
        return result;
    }

    public boolean kempesColoringRandom(int maxColor) {
        Random rng = new Random();
        List<Integer> remaining = new ArrayList<>(), firstChoice = new ArrayList<>(), precolored = new ArrayList<>();
        Stack<Integer> removed = new Stack<>();
        int[] tempColors = colors.clone();
        for(int i = 0; i < size(); i++) {
            if (tempColors[i] == 0) remaining.add(i);
            else precolored.add(i);
        }

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

    public boolean kempesColoringRandomPrecolored(int maxColor) {
        Random rng = new Random();
        List<Integer> remaining = new ArrayList<>(), firstChoice = new ArrayList<>(), precolored = new ArrayList<>();
        Stack<Integer> removed = new Stack<>();
        int[] tempColors = colors.clone();
        for(int i = 0; i < size(); i++) {
            if (tempColors[i] == 0) remaining.add(i);
            else precolored.add(i);
        }

        while(!remaining.isEmpty()) {
            for(int vertex : remaining)
                if(!firstChoice.contains(vertex)) {
                    List<Integer> remainingNeighbors = getNeighbors(vertex);
                    remainingNeighbors.removeAll(removed);
                    remainingNeighbors.removeAll(precolored);
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

    public boolean kempesColoringLargest(int maxColor) {
        Random rng = new Random();
        List<Integer> remaining = new ArrayList<>(), firstChoice = new ArrayList<>(), precolored = new ArrayList<>(), secondChoice;
        Stack<Integer> removed = new Stack<>();
        int[] tempColors = colors.clone();
        for(int i = 0; i < size(); i++) {
            if (tempColors[i] == 0) remaining.add(i);
            else precolored.add(i);
        }

        while(!remaining.isEmpty()) {
            int largestDegree = 0;
            secondChoice = new ArrayList<>();
            for(int vertex : remaining)
                if(!firstChoice.contains(vertex)) {
                    List<Integer> remainingNeighbors = getNeighbors(vertex);
                    remainingNeighbors.removeAll(removed);
                    if (remainingNeighbors.size() < maxColor) firstChoice.add(vertex);
                    if(remainingNeighbors.size() > largestDegree) {
                        largestDegree = remainingNeighbors.size();
                        secondChoice = new ArrayList<>();
                    }
                    if(remainingNeighbors.size() == largestDegree)
                        secondChoice.add(vertex);
                }
            Integer vertex;
            if(firstChoice.isEmpty()) {
                vertex = secondChoice.remove(rng.nextInt(secondChoice.size()));
            } else {
                vertex = firstChoice.remove(rng.nextInt(firstChoice.size()));
            }
            remaining.remove(vertex);
            removed.add(vertex);
        }

        for(int vertex : removed)
            if (k_assignColor(vertex,tempColors) > maxColor)
                return false;
        colors = tempColors;
        return true;
    }

    public boolean kempesColoringLargestPrecolored(int maxColor) {
        Random rng = new Random();
        List<Integer> remaining = new ArrayList<>(), firstChoice = new ArrayList<>(), precolored = new ArrayList<>(), secondChoice;
        Stack<Integer> removed = new Stack<>();
        int[] tempColors = colors.clone();
        for(int i = 0; i < size(); i++) {
            if (tempColors[i] == 0) remaining.add(i);
            else precolored.add(i);
        }

        while(!remaining.isEmpty()) {
            int largestDegree = 0;
            secondChoice = new ArrayList<>();
            for(int vertex : remaining)
                if(!firstChoice.contains(vertex)) {
                    List<Integer> remainingNeighbors = getNeighbors(vertex);
                    remainingNeighbors.removeAll(removed);
                    remainingNeighbors.removeAll(precolored);
                    if (remainingNeighbors.size() < maxColor) firstChoice.add(vertex);
                    if(remainingNeighbors.size() > largestDegree) {
                        largestDegree = remainingNeighbors.size();
                        secondChoice = new ArrayList<>();
                    }
                    if(remainingNeighbors.size() == largestDegree)
                        secondChoice.add(vertex);
                }
            Integer vertex;
            if(firstChoice.isEmpty()) {
                vertex = secondChoice.remove(rng.nextInt(secondChoice.size()));
            } else {
                vertex = firstChoice.remove(rng.nextInt(firstChoice.size()));
            }
            remaining.remove(vertex);
            removed.add(vertex);
        }

        for(int vertex : removed)
            if (k_assignColor(vertex,tempColors) > maxColor)
                return false;
        colors = tempColors;
        return true;
    }

    public boolean kempesColoringSmallest(int maxColor) {
        Random rng = new Random();
        List<Integer> remaining = new ArrayList<>(), firstChoice = new ArrayList<>(), precolored = new ArrayList<>();
        Stack<Integer> removed = new Stack<>();
        int[] tempColors = colors.clone();
        for(int i = 0; i < size(); i++) {
            if (tempColors[i] == 0) remaining.add(i);
            else precolored.add(i);
        }

        while(!remaining.isEmpty()) {
            int smallestDegree = Integer.MAX_VALUE;
            for(int vertex : remaining)
                if(!firstChoice.contains(vertex)) {
                    List<Integer> remainingNeighbors = getNeighbors(vertex);
                    remainingNeighbors.removeAll(removed);
                    if(remainingNeighbors.size() < smallestDegree) {
                        smallestDegree = remainingNeighbors.size();
                        firstChoice = new ArrayList<>();
                    }
                    if(remainingNeighbors.size() <= smallestDegree)
                        firstChoice.add(vertex);
                }
            Integer vertex = firstChoice.remove(rng.nextInt(firstChoice.size()));
            remaining.remove(vertex);
            removed.add(vertex);
        }

        for(int vertex : removed)
            if (k_assignColor(vertex,tempColors) > maxColor)
                return false;
        colors = tempColors;
        return true;
    }

    public boolean kempesColoringSmallestPrecolored(int maxColor) {
        Random rng = new Random();
        List<Integer> remaining = new ArrayList<>(), firstChoice = new ArrayList<>(), precolored = new ArrayList<>();
        Stack<Integer> removed = new Stack<>();
        int[] tempColors = colors.clone();
        for(int i = 0; i < size(); i++) {
            if (tempColors[i] == 0) remaining.add(i);
            else precolored.add(i);
        }

        while(!remaining.isEmpty()) {
            int smallestDegree = Integer.MAX_VALUE;
            for(int vertex : remaining)
                if(!firstChoice.contains(vertex)) {
                    List<Integer> remainingNeighbors = getNeighbors(vertex);
                    remainingNeighbors.removeAll(removed);
                    remainingNeighbors.removeAll(precolored);
                    if(remainingNeighbors.size() < smallestDegree) {
                        smallestDegree = remainingNeighbors.size();
                        firstChoice = new ArrayList<>();
                    }
                    if(remainingNeighbors.size() <= smallestDegree)
                        firstChoice.add(vertex);
                }
            Integer vertex = firstChoice.remove(rng.nextInt(firstChoice.size()));
            remaining.remove(vertex);
            removed.add(vertex);
        }

        for(int vertex : removed)
            if (k_assignColor(vertex,tempColors) > maxColor)
                return false;
        colors = tempColors;
        return true;
    }

    public boolean kempesColoringRecursive(int maxColor) {
        List<Integer> remaining = new ArrayList<>(), firstChoice = new ArrayList<>();
        Stack<Integer> removed = new Stack<>();

        for(int i = 0; i < size(); i++) {
            if (colors[i] == 0) remaining.add(i);
        }

        return kempesColoringRecursive(remaining,firstChoice,removed,maxColor);
    }

    public boolean kempesColoringRecursive(List<Integer> remaining, List<Integer> firstChoice, Stack<Integer> removed, int maxColor) {
        System.out.println(Arrays.toString(removed.toArray()));
        if(remaining.isEmpty()) {
            for(int vertex : removed)
                if(assignColor(vertex) > maxColor) {
                    for(int v : removed) colors[v] = 0;
                    return false;
                }
            return true;
        }

        for(Integer vertex : remaining) {
            if(!firstChoice.contains(vertex)) {
                List<Integer> neighbors = getNeighbors(vertex);
                neighbors.removeAll(removed);
                if (neighbors.size() < maxColor) firstChoice.add(vertex);
            }
        }

        if(firstChoice.isEmpty()) {
            for(int vertex : firstChoice) {
                List<Integer> firstChoiceCopy = new ArrayList<>(firstChoice);
                removed.add(firstChoiceCopy.remove(vertex));
                remaining.remove(vertex);
                if(kempesColoringRecursive(remaining,firstChoiceCopy,removed,maxColor))
                    return true;
                remaining.add(0,vertex);
                removed.remove(vertex);
            }
        }

        for(Integer vertex : remaining) {
            List<Integer> remainingCopy = new ArrayList<>(remaining);
            remainingCopy.remove(vertex);
            removed.add(vertex);
            if(kempesColoringRecursive(remainingCopy,firstChoice,removed,maxColor))
                return true;
            removed.remove(vertex);
        }

        return false;
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

    public int greedyEdgeColoring() {
        resetWeights();
        int maxColor = 2, numOfEdges;
        List<Rectangle> visited = new ArrayList<>(), notVisited = new ArrayList<>();
        Queue<Rectangle> queue = new LinkedList<>();
        Random rng = new Random();

        for(int i = 0; i < size(); i++)
            for(int j = directional ? 0 : i; j < size(); j++)
                if(edges[i][j] != 0)
                    notVisited.add(new Rectangle(i, j));

        numOfEdges = notVisited.size();

        queue.add(notVisited.remove(rng.nextInt(notVisited.size())));

        while(visited.size() < numOfEdges) {
            if(queue.isEmpty()) queue.add(notVisited.remove(rng.nextInt(notVisited.size())));

            Rectangle edge = queue.remove();
            visited.add(edge);
            maxColor = Math.max(maxColor,assignColor(edge));
            for(Rectangle neighbor : getNeighbors(edge))
                if(!visited.contains(neighbor) && !queue.contains(neighbor)) {
                    queue.add(neighbor);
                    notVisited.remove(neighbor);
                }
        }

        return maxColor-1;
    }

    private int assignColor(Rectangle edge) {
        int j;
        for(j = 2; neighborColored(edge,j); j++);
        edges[edge.width][edge.height] = j;
        if(!directional) edges[edge.height][edge.width] = j;
        return j;
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

    private boolean neighborColored(Rectangle edge, int color) {
        for(Rectangle neighbor : getNeighbors(edge))
            if(edges[neighbor.width][neighbor.height] == color)
                return true;
        return false;
    }
}
