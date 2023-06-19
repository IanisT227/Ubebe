import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/*
M: send_graph (S)
M: send_range (S)
M: repeat send_V (S)
M: receive_solution
S[x]: while(true)
S[x]: receive_V (S[x-1])
S[x]: send_V (S[x+1])


 */


class ColoringGraph
{
    final int verticesNumber = 5;
    final int threadsNumber = verticesNumber + 1;
    final int maxNumberOfColors = 4;
    int currentPath[];
    List<Integer> colorListForVertices;

    ColoringGraph() {
        //  !!!!
    }

    void initializeColorForVertices() {
        colorListForVertices = new ArrayList<>();
        for(int number = 0; number < verticesNumber; number++) {
            colorListForVertices.add(0);
        }
    }

    boolean checkForColoringGraph(int graph[][], int currentPath[], int visitedVerticesNumber) throws InterruptedException {
        initializeColorForVertices();
        var startingColoringGraphCheckingService = new ColoringGraphService(0, verticesNumber, currentPath, graph, visitedVerticesNumber, colorListForVertices, maxNumberOfColors);
        startingColoringGraphCheckingService.Test();
        boolean returnValue;
        returnValue = startingColoringGraphCheckingService.canGraphBeColored;

        return returnValue;
    }


    int colorGraph(int graph[][]) throws InterruptedException {
        currentPath = new int[verticesNumber];
        for (int i = 0; i < verticesNumber; i++)
            currentPath[i] = -1;

        currentPath[0] = 0;
        if (checkForColoringGraph(graph, currentPath, 1) == false)
        {
            System.out.println("\nSolution does not exist");
            return 0;
        }
        return 1;
    }

    public static void main(String args[]) throws InterruptedException {
        ColoringGraph hamiltonian =
                new ColoringGraph();

        int graph1[][] =   {
            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 1, 0, 1, 0}
        };

        int graph2[][] = {
                {0, 1, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1},
                {1, 1, 1, 1, 0},
        };

        hamiltonian.colorGraph(graph1);
        System.out.println("Main ended");
    }
}
