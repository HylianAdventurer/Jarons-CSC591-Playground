using System;
using System.Collections.Generic;
using System.Linq;

namespace Graph_Theory
{
    internal static class Program
    {
        static void Main(string[] args)
        {
            for (var i = 3; i < 100; i++)
            {
                Console.WriteLine(i + ": " + Graph.Complete(i).Eulerian);
            }
        }

        public static Graph UnitedStates(bool continental)
        {
            const int AL = 00; // Alabama
            const int AZ = 01; // Arizona
            const int AR = 02; // Arkansas
            const int CA = 03; // California
            const int CO = 04; // Colorado
            const int CT = 05; // Connecticut
            const int DE = 06; // Delaware
            const int FL = 07; // Florida
            const int GA = 08; // Georgia
            const int ID = 09; // Idaho
            const int IL = 10; // Illinois
            const int IN = 11; // Indiana
            const int IA = 12; // Iowa
            const int KS = 13; // Kansas
            const int KY = 14; // Kentucky
            const int LA = 15; // Louisiana
            const int ME = 16; // Maine
            const int MD = 17; // Maryland
            const int MA = 18; // Massachusetts
            const int MI = 19; // Michigan
            const int MN = 20; // Minnesota
            const int MS = 21; // Mississippi
            const int MO = 22; // Missouri
            const int MT = 23; // Montana
            const int NE = 24; // Nebraska
            const int NV = 25; // Nevada
            const int NH = 26; // New Hampshire
            const int NJ = 27; // New Jersey
            const int NM = 28; // New Mexico
            const int NY = 29; // New York
            const int NC = 30; // North Carolina
            const int ND = 31; // North Dakota
            const int OH = 32; // Ohio
            const int OK = 33; // Oklahoma
            const int OR = 34; // Oregon
            const int PA = 35; // Pennsylvania
            const int RI = 36; // Rhode Island
            const int SC = 37; // South Carolina
            const int SD = 38; // South Dakota
            const int TN = 39; // Tennessee
            const int TX = 40; // Texas
            const int UT = 41; // Utah
            const int VT = 42; // Vermont
            const int VA = 43; // Virginia
            const int WA = 44; // Washington
            const int WV = 45; // West Virginia
            const int WI = 46; // Wisconsin
            const int WY = 47; // Wyoming
            const int AK = 48; // Alaska
            const int HI = 49; // Hawaii
            
            var US = new Graph(continental ? 48 : 50);

            US[CA, AZ] = 1;
            US[CA, NV] = 1;
            US[CA, OR] = 1;
            US[OR, WA] = 1;
            US[OR, ID] = 1;
            US[OR, NV] = 1;
            US[WA, ID] = 1;
            US[NV, AZ] = 1;
            US[NV, UT] = 1;
            US[NV, ID] = 1;
            US[AZ, UT] = 1;
            US[AZ, NM] = 1;
            US[ID, MT] = 1;
            US[ID, WY] = 1;
            US[ID, UT] = 1;
            US[UT, WY] = 1;
            US[UT, CO] = 1;
            US[MT, ND] = 1;
            US[MT, SD] = 1;
            US[MT, WY] = 1;
            US[WY, CO] = 1;
            US[WY, SD] = 1;
            US[WY, NE] = 1;
            US[CO, NE] = 1;
            US[CO, KS] = 1;
            US[CO, NM] = 1;
            US[NM, OK] = 1;
            US[NM, TX] = 1;
            US[ND, MN] = 1;
            US[ND, SD] = 1;
            US[SD, MN] = 1;
            US[SD, NE] = 1;
            US[SD, IA] = 1;
            US[NE, IA] = 1;
            US[NE, MO] = 1;
            US[NE, KS] = 1;
            US[KS, MO] = 1;
            US[KS, OK] = 1;
            US[OK, MO] = 1;
            US[OK, AR] = 1;
            US[OK, TX] = 1;
            US[TX, AR] = 1;
            US[TX, LA] = 1;
            US[MN, WI] = 1;
            US[MN, IA] = 1;
            US[IA, WI] = 1;
            US[IA, IL] = 1;
            US[IA, MO] = 1;
            US[MO, IL] = 1;
            US[MO, KY] = 1;
            US[MO, TN] = 1;
            US[MO, AR] = 1;
            US[AR, TN] = 1;
            US[AR, MS] = 1;
            US[AR, LA] = 1;
            US[LA, MS] = 1;
            US[WI, MI] = 1;
            US[WI, IL] = 1;
            US[IL, ID] = 1;
            US[IL, KY] = 1;
            US[MI, ID] = 1;
            US[MI, OH] = 1;
            US[ID, OH] = 1;
            US[ID, KY] = 1;
            US[KY, OH] = 1;
            US[KY, WV] = 1;
            US[KY, VA] = 1;
            US[KY, TN] = 1;
            US[TN, VA] = 1;
            US[TN, NC] = 1;
            US[TN, GA] = 1;
            US[TN, AL] = 1;
            US[TN, MS] = 1;
            US[MS, AL] = 1;
            US[AL, FL] = 1;
            US[AL, GA] = 1;
            US[FL, GA] = 1;
            US[GA, SC] = 1;
            US[GA, NC] = 1;
            US[SC, NC] = 1;
            US[NC, VA] = 1;
            US[VA, WV] = 1;
            US[VA, MD] = 1;
            US[WV, OH] = 1;
            US[WV, PA] = 1;
            US[WV, MD] = 1;
            US[OH, PA] = 1;
            US[MD, DE] = 1;
            US[MD, PA] = 1;
            US[DE, PA] = 1;
            US[DE, NJ] = 1;
            US[PA, NY] = 1;
            US[PA, NJ] = 1;
            US[NJ, NY] = 1;
            US[NY, CT] = 1;
            US[NY, MA] = 1;
            US[NY, VT] = 1;
            US[CT, RI] = 1;
            US[CT, MA] = 1;
            US[RI, MA] = 1;
            US[MA, VT] = 1;
            US[MA, NH] = 1;
            US[MA, VT] = 1;
            US[VT, NH] = 1;
            US[NH, ME] = 1;

            return US;
        }

        public static int Ramsey(params int[] sizes)
        {
            var graphs = new Graph[sizes.Length];
            for(var i = 0; i < sizes.Length; i++) graphs[i] = new Graph(sizes[i]);

            return Ramsey(graphs);
        }

        public static int Ramsey(params Graph[] graphs)
        {
            var colors = new List<Graph.GraphColor>();
            
            for(var i = 1; i <= graphs.Length; i++) colors.Add(i);

            var sizes = (from graph in graphs select graph.Size);

            var size = (int)Math.Max((sizes.Min() / Math.E * (2 ^ ((sizes.Min() - 1) / 2)) - 1), sizes.Max() - 1);
            
            Console.WriteLine(size+1);
            
            while (!CheckColorPermutationsFirst(new Graph(++size), colors, graphs)) Console.WriteLine(size);

            return size;
        }

        private static bool CheckColorPermutationsFirst(Graph graph, IReadOnlyList<Graph.GraphColor> colors,
            params Graph[] graphs)
        {
            if (graphs.Any(g => graph.Size < g.Size))
                return false;

            var sameSize = true;
            for (var i = 1; i < graphs.Length; i++)
                sameSize &= graphs[0].Size == graphs[i].Size;

            if (sameSize)
            {
                graph.SetEdgeColor(0,0,colors[0]);
                return CheckColorPermutations(graph.Clone(), colors, graphs);
            }
            foreach (var color in colors)
            {
                graph.SetEdgeColor(0, 0, color);
                if (!CheckColorPermutations(graph.Clone(), colors, graphs)) return false;
            }

            return true;
        }

        private static bool CheckColorPermutations(Graph graph, IReadOnlyList<Graph.GraphColor> colors, params Graph[] graphs)
        {
            var (vertex1, vertex2) = (0, 0);

            while (graph[vertex1,vertex2] == 0 || graph.GetEdgeColor(vertex1, vertex2) != 0)
            {
                if (vertex1 < graph.Size - 1) vertex1++;
                else (vertex1, vertex2) = (0, vertex2 + 1);

                if (vertex2 != graph.Size) continue;
                
                for (var i = 0; i < colors.Count; i++)
                {
                    var subGraph = graph.Clone();
                    (vertex1, vertex2) = (0, 0);

                    while (vertex2 != subGraph.Size)
                    {
                        if (subGraph.GetEdgeColor(vertex1, vertex2) != colors[i]) subGraph[vertex1, vertex2] = 0;
                        if (vertex1 < subGraph.Size - 1) vertex1++;
                        else (vertex1, vertex2) = (0, vertex2 + 1);
                    }
                    
                    if (subGraph >= graphs[i]) return true;
                }

                return false;
            }

            foreach (var color in colors)
            {
                graph.SetEdgeColor(vertex1, vertex2, color);
                if (!CheckColorPermutations(graph.Clone(), colors, graphs)) return false;
            }

            return true;
        }
    }
}