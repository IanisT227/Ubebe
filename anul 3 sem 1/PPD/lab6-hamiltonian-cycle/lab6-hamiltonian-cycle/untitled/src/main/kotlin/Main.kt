import kotlin.system.measureTimeMillis

fun main() {
//    val graph = Graph.getRandomGraph(200, 10500)
    val graph = getExampleGraph()
    timeSequential(graph)
    timeParallel(graph)
}

private fun getExampleGraph(): Graph {
    val edges = mutableListOf<Pair<Int, Int>>()
    edges.add(Pair(0, 1));
    edges.add(Pair(0, 4));
    edges.add(Pair(1, 0));
    edges.add(Pair(1, 5));
    edges.add(Pair(1, 2));
    edges.add(Pair(2, 3));
    edges.add(Pair(2, 5));
    edges.add(Pair(2, 1));
    edges.add(Pair(3, 4));
    edges.add(Pair(3, 5));
    edges.add(Pair(3, 2));
    edges.add(Pair(4, 0));
    edges.add(Pair(4, 5));
    edges.add(Pair(4, 3));
    edges.add(Pair(5, 1));
    edges.add(Pair(5, 2));
    edges.add(Pair(5, 3));
    edges.add(Pair(5, 4));
    return Graph(6, edges)
}

private fun timeSequential(graph: Graph){
    var hamiltonianCycle : List<Int>
    val time = measureTimeMillis {
        hamiltonianCycle = HamiltonianSearcher(graph).getListSequential()
    }
    println(hamiltonianCycle)
    println("Sequential searching took: $time")
}

private fun timeParallel(graph: Graph){
    var hamiltonianCycle : List<Int>
    val time = measureTimeMillis {
        hamiltonianCycle = HamiltonianSearcher(graph).getListParallel()
    }
    println(hamiltonianCycle)
    println("Parallel searching took: $time")
}