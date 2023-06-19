
public class Main {
    public static void main(String[] args) {
        var repository = new Repository(8);
        try {
            float startTime = System.nanoTime();
            repository.runTransactionsOnANumberOfThreads();
            float endTime = System.nanoTime();
            System.out.println("Execution time: " + (endTime - startTime)/1000000);
        }
        catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }
}
