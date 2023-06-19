import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class HamiltonianSearcher(private val graph: Graph, private val startingNode: Int = 0) {
    private var visited = LinkedHashSet<Int>()
    private var resultPath = mutableListOf<Int>()
    private var lock = ReentrantLock()
    private var executor: ExecutorService? = null
    private val maxDepth: Int = 2

    private constructor(
        graph: Graph,
        startingNode: Int,
        visited: LinkedHashSet<Int>,
        resultPath: MutableList<Int>,
        lock: ReentrantLock,
        executor: ExecutorService?
    ) : this(graph, startingNode) {
        this.visited = visited
        this.resultPath = resultPath
        this.lock = lock
        this.executor = executor
    }

    private fun visitNodeSequential(node: Int) {
        lock.lock()
        if (resultPath.isNotEmpty()) {
            lock.unlock()
            return
        }
        lock.unlock()
        visited.add(node)
        if (visited.size != graph.nodesCount)
            graph.getNeighbours(node)
                .filter { it !in visited }
                .forEach {
                    visitNodeSequential(it)
                }
        else if (graph.getNeighbours(node).contains(startingNode)) {
            lock.lock()
            resultPath.addAll(visited)
            lock.unlock()
        }
        visited.remove(node)
    }

    fun getListSequential(): List<Int> {
        resultPath = mutableListOf()
        visitNodeSequential(startingNode)
        return resultPath
    }


    private fun visitNodeParallel(node: Int, depth: Int = 0) {
        lock.lock()
        if (resultPath.isNotEmpty()) {
            lock.unlock()
            return
        }
        lock.unlock()
        if (depth < maxDepth) {
            visited.add(node)
            if (visited.size != graph.nodesCount) {
                val futures = mutableListOf<Future<*>>()
                graph.getNeighbours(node)
                    .filter { it !in visited }
                    .forEach {
                        futures.add(executor!!.submit {
                            this.copy().visitNodeParallel(it, depth + 1)
                        })
                    }
                for (future in futures)
                    future.get()
            } else if (graph.getNeighbours(node).contains(startingNode)) {
                lock.lock()
                resultPath.addAll(visited)
                lock.unlock()
            }
        } else {
            this.copy().visitNodeSequential(node)
        }
    }

    fun getListParallel(threadsNumber: Int = 8): List<Int> {
        executor = Executors.newFixedThreadPool(threadsNumber)
        resultPath = mutableListOf()
        visitNodeParallel(startingNode)
        executor!!.shutdown()
        executor!!.awaitTermination(120, TimeUnit.SECONDS)
        return resultPath
    }

    private fun copy(): HamiltonianSearcher {
        val newVisited = LinkedHashSet<Int>()
        for (node in visited)
            newVisited.add(node)
        return HamiltonianSearcher(graph, startingNode, newVisited, resultPath, lock, executor)
    }
}