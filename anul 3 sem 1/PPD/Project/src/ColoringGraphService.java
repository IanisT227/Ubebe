import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ColoringGraphService {
    int verticesNumber;
    int currentPath[];
    int graph[][];
    int visitedVerticesNumber;
    boolean canGraphBeColored;
    ExecutorService service;
    int threadId;
    static Boolean foundSolution = false;
    List<Integer> colorListForVertices;
    int maxNumberOfColors;

    public ColoringGraphService(int threadId, int verticesNumber, int[] currentPath, int[][] graph, int visitedVerticesNumber, List<Integer> colorListForVertices, int maxNumberOfColors) {
        this.threadId = threadId;
        this.verticesNumber = verticesNumber;
        this.currentPath = currentPath;
        this.graph = graph;
        this.visitedVerticesNumber = visitedVerticesNumber;
        this.canGraphBeColored = false;
        this.colorListForVertices = colorListForVertices;
        this.maxNumberOfColors = maxNumberOfColors;
        service = Executors.newFixedThreadPool(1000);
    }

    boolean isSafeToTravel(int source, int destination)
    {
        for (int v : currentPath) {
            if (v == destination) {
                return false;
            }
        }
        if (graph[source][destination] == 0)
            return false;
        if(colorListForVertices.get(destination) != 0) {
            return false;
        }
        return true;
    }

    boolean isSafeToColor(int currentVertex, int color) {
        for(int neighbourId = 0; neighbourId < verticesNumber; neighbourId++) {
            if(graph[currentVertex][neighbourId] != 0) {
                if(colorListForVertices.get(neighbourId) == color) {
                    return false;
                }
            }
        }
        return true;
    }

    void printSolution()
    {
        synchronized (foundSolution) {
            if (foundSolution) return;
            foundSolution = true;
            System.out.println("Solution Exists: Following" +
                    " is one Coloring");
            for (int i = 0; i < verticesNumber; i++) {
                System.out.print(" " + colorListForVertices.get(i) + " ");
            }
            System.out.println();
        }
    }

    public int Test()
    {
        if (foundSolution) return 1;
        if (visitedVerticesNumber == verticesNumber + 1)
        {
            this.canGraphBeColored = true;
            printSolution();
            return 1;
        }
        List<Future> futures = new ArrayList<>();
        boolean canBeColored = false;
        for (int colorId = 1; colorId <= maxNumberOfColors; colorId++) {
            if(isSafeToColor(visitedVerticesNumber - 1, colorId)) {
                canBeColored = true;
                colorListForVertices.set(visitedVerticesNumber - 1, colorId);

                List<Integer> copyColorListForVertices = new ArrayList<>(colorListForVertices);
                int[]  newCurrentPath = Arrays.copyOf(currentPath, currentPath.length);
                var newCheckForHamiltonianCycleService = new ColoringGraphService(threadId+1, verticesNumber, newCurrentPath, graph, visitedVerticesNumber + 1, copyColorListForVertices, maxNumberOfColors);
                futures.add(service.submit(() -> newCheckForHamiltonianCycleService.Test()));
            }
        }

        if(!canBeColored) {
            return 0;
        }

        for (var future : futures) {
            try {
                int x = (int)future.get();
                if(x == 1) {
                    this.canGraphBeColored = true;
                }
            } catch (Exception ignored) {}
        }
        service.shutdownNow();
        if (this.canGraphBeColored) return 1;
        return 0;
    }
}
