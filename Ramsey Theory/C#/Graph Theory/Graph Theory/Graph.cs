using System;
using System.Collections.Generic;
using System.ComponentModel.Design;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using Microsoft.VisualBasic.CompilerServices;

namespace Graph_Theory
{
    public struct Graph
    {
        public bool Equals(Graph other)
        {
            return this == other;
        }

        public override bool Equals(object obj)
        {
            return obj is Graph other && Equals(other);
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(_edges, _edgeColors, _vertexColors, _directional);
        }

        private int[][] _edges;
        private GraphColor[][] _edgeColors;
        private GraphColor[] _vertexColors;
        private bool _directional;

        public Graph(int k)
        {
            var g = Complete(k);
            _directional = g._directional;
            _edges = g._edges;
            _edgeColors = g._edgeColors;
            _vertexColors = g._vertexColors;
        }

        public static Graph Complete(int k)
        {
            Graph g;
            g._directional = false;
            
            g._edges = new int[k][];
            g._edgeColors = new GraphColor[k][];
            g._vertexColors = new GraphColor[k];

            for (var i = 0; i < k; i++)
            {
                g._edges[i] = new int[k];
                g._edgeColors[i] = new GraphColor[k];
                g._vertexColors[i] = 0;
                
                for (var j = 0; j < k; j++)
                {
                    g._edges[i][j] = i == j ? 0 : 1;
                    g._edgeColors[i][j] = 0;
                }
            }

            return g;
        }

        public static Graph Circuit(int k)
        {
            Graph g;
            g._directional = false;

            g._edges = new int[k][];
            g._edgeColors = new GraphColor[k][];
            g._vertexColors = new GraphColor[k];

            for (var i = 0; i < k; i++)
            {
                g._edges[i] = new int[k];
                g._edgeColors[i] = new GraphColor[k];
                g._vertexColors[i] = 0;
                for (var j = 0; j < k; j++)
                    g._edgeColors[i][j] = 0;
            }
            
            for (var i = 1; i < k; i++)
                g._edges[i - 1][i] = g._edges[i][i - 1] = 1;

            g[0, k - 1] = 1;

            return g;
        }

        public bool Directional
        {
            get => _directional;
            set => _directional = value;
        }

        public readonly int Size
        {
            get => _edges.Length;
        }

        public int this[int v1, int v2]
        {
            get => _edges[v1][v2];
            set
            {
                _edges[v1][v2] = value;
                if (!_directional) _edges[v2][v1] = value;
            }
        }
        
        public GraphColor GetVertexColor(int vertex) => _vertexColors[vertex];

        public void SetVertexColor(int vertex, GraphColor color) => _vertexColors[vertex] = color;

        public GraphColor GetEdgeColor(int vertex1, int vertex2) => _edgeColors[vertex1][vertex2];

        public void SetEdgeColor(int vertex1, int vertex2, GraphColor color)
        {
            _edgeColors[vertex1][vertex2] = color;
            if (!_directional) _edgeColors[vertex2][vertex1] = color;
        }

        public void ResetEdgeColors()
        {
            for(var i = 0; i < Size; i++)
                for(var j = _directional ? 0 : i+1; j < Size; j++)
                    SetEdgeColor(i,j,0);
        }

        public void PrintEdgeColors()
        {
            foreach (var row in _edgeColors)
            {
                foreach (var item in row)
                {
                    Console.Write(item.ToString());
                }
                Console.WriteLine();
            }
        }

        public override string ToString()
        {
            StringBuilder result = new StringBuilder();

            foreach (var row in _edges)
            {
                foreach (var item in row)
                    result.Append(item.ToString());

                result.Append("\n");
            }

            return result.ToString();
        }

        public void Remove(int vertex)
        {
            for (var i = 0; i < Size; i++)
            {
                var t1 = _edges[i].ToList();
                t1.RemoveAt(vertex);
                _edges[i] = t1.ToArray();
                
                var t2 = _edgeColors[i].ToList();
                t2.RemoveAt(vertex);
                _edgeColors[i] = t2.ToArray();
            }

            var t3 = _edges.ToList();
            t3.RemoveAt(vertex);
            _edges = t3.ToArray();
            
            var t4 = _edgeColors.ToList();
            t4.RemoveAt(vertex);
            _edgeColors = t4.ToArray();
            
            var t5 = _vertexColors.ToList();
            t5.RemoveAt(vertex);
            _vertexColors = t5.ToArray();
        }

        public readonly List<int> NeighborsOf(int vertex)
        {
            var result = new List<int>();
            
            for (var i = 0; i < Size; i++)
                if(_edges[vertex][i] != 0) result.Add(i);

            return result;
        }

        public int DegreeOf(int vertex)
        {
            var result = 0;
            for (var i = 0; i < Size; i++)
                if (_edges[vertex][i] != 0)
                    result++;

            return result;
        }
        
        public static bool operator ==(Graph graph1, Graph graph2) // graph1 == graph 2 : graph 1 and graph 2 are isomorphic
        {
            if (graph1.Size != graph2.Size) return false;
            
            List<int> selected = new List<int>(), unselected = new List<int>();
            
            for(var i = 0; i < graph1.Size; i++) unselected.Add(i);
            
            return IsomorphismPermutations(graph1, graph2, selected, unselected);
        }

        private static bool IsomorphismPermutations(Graph graph1, Graph graph2, List<int> selected, List<int> unselected)
        {
            if (unselected.Count == 0)
            {
                for (var i = 0; i < graph1.Size; i++)
                {
                    List<int> g1VNeighbors = graph1.NeighborsOf(selected[i]), g2VNeighbors = graph2.NeighborsOf(i);

                    if (g1VNeighbors.Count != g2VNeighbors.Count) return false;

                    for (var j = 0; j < g1VNeighbors.Count; j++)
                        if (!g1VNeighbors.Contains(selected[g2VNeighbors[j]])) return false;
                }

                return true;
            }

            for (var i = 0; i < unselected.Count; i++)
            {
                var n = unselected[i];
                unselected.RemoveAt(i);
                selected.Add(n);

                var result = IsomorphismPermutations(graph1, graph2, selected, unselected);

                for (var j = i; j < unselected.Count; j++)
                {
                    var t = unselected[j];
                    unselected[j] = n;
                    n = t;
                }
                unselected.Add(n);
                if (result) return true;
            }

            return false;
        }

        public static bool operator !=(Graph graph1, Graph graph2) => !(graph1 == graph2); // graph1 != graph2 : graph 1 and graph 2 are not isomorphic

        public static bool operator <(Graph graph1, Graph graph2) => graph1.Size <= graph2.Size && graph1 != graph2 && graph1 <= graph2; // graph1 < graph2 : graph1 is a proper subgraph of graph2;

        public static bool operator >(Graph graph1, Graph graph2) => graph2 < graph1; // graph1 > graph2 : graph 2 is a proper subgraph of graph1

        public static bool operator <=(Graph graph1, Graph graph2) // graph1 <= graph2 : graph 1 is a subgraph of graph2
        {
            if (graph1.Size == graph2.Size)
            {
                var edges1 = 0;
                var edges2 = new List<(int, int)>();;
                for (var i = 0; i < graph1.Size; i++)
                    for (var j = graph1._directional && graph2._directional ? i + 1 : 0; j < graph1.Size; j++)
                    {
                        if (graph1._edges[i][j] != 0) edges1++;
                        if (graph2._edges[i][j] != 0) edges2.Add((i, j));
                    }

                if (edges1 == edges2.Count) return graph1 == graph2;

                foreach (var (v1, v2) in edges2)
                {
                    var t = graph2._edges[v1][v2];
                    graph2[v1, v2] = 0;
                    var result = graph1 <= graph2;
                    graph2[v1, v2] = t;
                    if (result) return true;
                }

                return false;
            }
            
            var degrees = new List<int>();
            for (var i = 0; i < graph1.Size; i++) degrees.Add(graph1.DegreeOf(i));

            var smallestDegree = (from degree in degrees select degree).Min();

            for (var i = 0; i < graph2.Size; i++)
                if (graph2.DegreeOf(i) < smallestDegree)
                {
                    var t = graph2.Clone();
                    t.Remove(i);
                    return graph1 <= t;
                }

            for (var i = 0; i < graph2.Size; i++)
            {
                var t = graph2.Clone();
                t.Remove(i);
                if (graph1 <= t) return true;
            }

            return false;
        }

        public static bool operator >=(Graph graph1, Graph graph2) => graph2 <= graph1; // graph1 >= graph2 : graph 2 is a subgraph of graph 1
        
        public readonly bool Hamiltonian => Connected && Circuit(Size) <= this;

        public bool HamiltonianPath(int vertex1, int vertex2)
        {
            return false;
        }

        public readonly bool Eulerian
        {
            get
            {
                if (!Connected) return false;
                for(var i = 0; i < Size; i++)
                    if (DegreeOf(i) % 2 == 1)
                        return false;
                return true;
            }
        }

        public readonly bool EulerianPath(int vertex1, int vertex2)
        {
            return false;
        }

        private static Graph EulerianRecursion(int vertex, Graph graph)
        {
            Console.WriteLine(graph);
            foreach (var neighbor in graph.NeighborsOf(vertex).Where(neighbor => IsValidNextEdge(vertex, neighbor, graph)))
            {
                graph[vertex, neighbor] = 0;
                if (graph.DegreeOf(vertex) == 0) graph.Remove(vertex);
                return EulerianRecursion(neighbor > vertex ? neighbor - 1 : neighbor, graph);
            }

            return graph;
        }

        private static bool IsValidNextEdge(int vertex1, int vertex2, Graph graph)
        {
            if (graph.DegreeOf(vertex1) == 1) return true;

            var t = graph[vertex1, vertex2];
            graph[vertex1,vertex2] = 0;
            var result = graph.Connected;
            graph[vertex1, vertex2] = t;

            return result;
        }

        public readonly bool Connected
        {
            get
            {
                var visited = new List<int>();
                var queue = new Queue<int>();
                queue.Enqueue(0);

                while (queue.Count > 0)
                {
                    var selected = queue.Dequeue();
                    visited.Add(selected);
                    foreach (var neighbor in NeighborsOf(selected).Where(neighbor => !visited.Contains(neighbor) && !queue.Contains(neighbor)))
                        queue.Enqueue(neighbor);
                }

                return visited.Count == Size;
            }
        }

        public bool ConnectedPath(int vertex1, int vertex2)
        {
            var visited = new List<int>();
            var queue = new Queue<int>();
            queue.Enqueue(vertex1);

            while (queue.Count > 0)
            {
                var selected = queue.Dequeue();
                visited.Add(selected);
                foreach (var neighbor in NeighborsOf(selected).Where(neighbor => !visited.Contains(neighbor) && !queue.Contains(neighbor)))
                    if (neighbor == vertex2)
                        return true;
                    else queue.Enqueue(neighbor);
            }

            return false;
        }

        public Graph Clone()
        {
            Graph result;

            result._edges = new int[Size][];
            result._edgeColors = new GraphColor[Size][];
            result._vertexColors = new GraphColor[Size];
            result._directional = _directional;

            for (var i = 0; i < Size; i++)
            {
                result._edges[i] = new int[Size];
                result._edgeColors[i] = new GraphColor[Size];
                result._vertexColors[i] = _vertexColors[i];
                for (var j = 0; j < Size; j++)
                {
                    result._edges[i][j] = _edges[i][j];
                    result._edgeColors[i][j] = _edgeColors[i][j];
                }
            }

            return result;
        }

        public struct GraphColor
        {
            private int _value;

            public GraphColor(int color)
            {
                _value = color;
            }
            
            public static implicit operator GraphColor(int n) => new GraphColor(n);
            public static implicit operator int(GraphColor c) => c._value;

            public override string ToString()
            {
                return _value.ToString();
            }
        }
    }
}