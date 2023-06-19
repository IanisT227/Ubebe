import Repository.Repository;

public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository();
        try {
            repository.executeTransactions(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
