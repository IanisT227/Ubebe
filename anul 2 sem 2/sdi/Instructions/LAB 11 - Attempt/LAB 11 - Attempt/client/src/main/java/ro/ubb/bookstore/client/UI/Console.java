package ro.ubb.bookstore.client.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.ubb.bookstore.core.Domain.exceptions.ValidatorException;
import ro.ubb.bookstore.web.dto.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class Console {
    private String urlPublishingHouses = "http://localhost:8090/api/publishingHouses";
    private String urlBooks = "http://localhost:8090/api/books";
    private String urlClients = "http://localhost:8090/api/clients";
    private String urlClientsWithBook = "http://localhost:8090/api/booksWithClient";
    @Autowired
    private RestTemplate restTemplate;
    private final Map<String, Runnable> commands;

    public Console() {

        this.commands = new HashMap<>();
        this.commands.put("0", () -> {
            System.out.println("Goodbye!");
            System.exit(0);
        });
        this.commands.put("1", this::addClient);
        this.commands.put("2", this::updateClient);
        this.commands.put("3", this::deleteClient);
        this.commands.put("4", this::printAllClients);
        this.commands.put("5", this::addBook);
        this.commands.put("6", this::updateBook);
        this.commands.put("7", this::deleteBook);
        this.commands.put("8", this::printAllBooks);
        this.commands.put("9", this::addClientWithBook);
        this.commands.put("10", this::getSortedPublishingHouses);
        this.commands.put("11", this::updateClientWithBook);
        this.commands.put("12", this::deleteClientWithBook);
        this.commands.put("13", this::printAllClientsWithBook);
        this.commands.put("14", this::addPublishingHouse);
        this.commands.put("15", this::updatePublishingHouse);
        this.commands.put("16", this::deletePublishingHouse);
        this.commands.put("17", this::printAllPublishingHouses);
        this.commands.put("18", this::getBooksByTitle);
    }

    private void getBooksByTitle() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the book title: ");
        String title = stdin.nextLine();
        BooksDto books = restTemplate.getForObject("http://localhost:8080/api/books/" + title, BooksDto.class);
        System.out.println(books);
    }

    private void getSortedPublishingHouses() {
        PublishingHousesDto publishingHouses = restTemplate.getForObject("http://localhost:8080/api/publishingHousesSorted", PublishingHousesDto.class);
        System.out.println(publishingHouses);
    }


    private static void printMenu() {
        System.out.print("""
                Available commands:
                \t0. Exit
                \t1. Add a new client
                \t2. Update client
                \t3. Delete client
                \t4. Print all clients
                \t5. Add a new book
                \t6. Update book
                \t7. Delete book
                \t8. Print all books
                \t9. Add a new bookWithClient
                \t10. Sorted publishingHouses
                \t11. Update bookWithClient
                \t12. Delete bookWithClient
                \t13. Print all booksWithClient
                \t14. Add a new publishingHouse
                \t15. Update publishingHouse
                \t16. Delete publishingHouse
                \t17. Print all publishingHouses
                \t18. Books by title""");
    }

    public void runConsole() {
        Scanner stdin = new Scanner(System.in);
        while (true) {
            try {
                printMenu();
                System.out.print("\nPlease enter a valid command: ");
                String cmd = stdin.nextLine().strip();
                Runnable cmdToRun = this.commands.get(cmd);
                if (cmdToRun == null) {
                    System.out.println("ERROR: Invalid command given. No command with ID \"" + cmd + "\"");
                } else {
                    cmdToRun.run();
                }
            } catch (ValidatorException | IllegalArgumentException exception) {
                System.out.println("ERROR: Failed to execute the command.");
                System.out.println(exception.toString());   // CANNOT USE exception.getMessage(); THE EXCEPTION WILL NOT BE CAUGHT
            }                                               // THE PROGRAM WILL CRASH IF WE USE .getMessage() (GRADLE!)
        }
    }

    private void printAllPublishingHouses() {
        PublishingHousesDto publishingHouses = restTemplate.getForObject(urlPublishingHouses, PublishingHousesDto.class);
        System.out.println(publishingHouses);
    }

    private void addPublishingHouse() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the publishingHouse ID: ");
        Long id = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the publishingHouse county: ");
        String county = stdin.nextLine();
        System.out.print("Enter the publishingHouse city: ");
        String city = stdin.nextLine();

        PublishingHouseDto savedPublishingHouse = restTemplate.postForObject(urlPublishingHouses, new PublishingHouseDto(county, city), PublishingHouseDto.class);
        System.out.println("saved publishingHouse:");
        System.out.println(savedPublishingHouse);
    }

    private void updatePublishingHouse() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the publishingHouse ID: ");
        Long id = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the new publishingHouse country: ");
        String country = stdin.nextLine();
        System.out.print("Enter the new publishingHouse name: ");
        String name = stdin.nextLine();
        PublishingHouseDto lala = new PublishingHouseDto(country, name);
        lala.setId(id);
        PublishingHouseDto updatedPublishingHouse = restTemplate.postForObject(urlPublishingHouses, lala, PublishingHouseDto.class);
//        restTemplate.put(urlPublishingHouses + "/{id}", updatedPublishingHouse, updatedPublishingHouse.getId());
        restTemplate.put(urlPublishingHouses + "/{id}", updatedPublishingHouse, id);
        System.out.println("update:");
        System.out.println(restTemplate.getForObject(urlPublishingHouses, PublishingHousesDto.class));
    }

    private void deletePublishingHouse() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the publishingHouse ID: ");
        Long id = Long.valueOf(stdin.nextLine());

        restTemplate.delete(urlPublishingHouses + "/{id}", id);
        System.out.println("delete:");
        System.out.println(restTemplate.getForObject(urlPublishingHouses, PublishingHousesDto.class));
    }


    public void printAllClients() {
        ClientsDto clients = restTemplate.getForObject(urlClients, ClientsDto.class);
        System.out.println(clients);
    }

    private void addClient() throws ValidatorException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the client ID: ");
        Long id = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the client name: ");
        String name = stdin.nextLine();
        System.out.print("Enter the client total balance: ");
        Integer totalBalance = Integer.valueOf(stdin.nextLine());

        ClientDto savedClient = restTemplate.postForObject(urlClients, new ClientDto(name, totalBalance), ClientDto.class);
        System.out.println("saved client:");
        System.out.println(savedClient);
    }

    private void updateClient() throws ValidatorException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the client ID: ");
        Long id = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the new client name: ");
        String name = stdin.nextLine();
        System.out.print("Enter the client total balance: ");
        Integer totalBalance = Integer.valueOf(stdin.nextLine());
        ClientDto newClient = new ClientDto(name, totalBalance);
        newClient.setId(id);
        ClientDto updatedClient = restTemplate.postForObject(urlClients, newClient, ClientDto.class);
//        restTemplate.put(urlClients + "/{id}", updatedClient, updatedClient.getId());
        restTemplate.put(urlClients + "/{id}", updatedClient, id);
        System.out.println("update:");
        System.out.println(restTemplate.getForObject(urlClients, ClientsDto.class));
    }

    private void deleteClient() throws ValidatorException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the client ID: ");
        Long id = Long.valueOf(stdin.nextLine());

        restTemplate.delete(urlClients + "/{id}", id);
        System.out.println("delete:");
        System.out.println(restTemplate.getForObject(urlClients, ClientsDto.class));
    }


    private void printAllBooks() {
        BooksDto books = restTemplate.getForObject(urlBooks, BooksDto.class);
        System.out.println(books);
    }

    private void addBook() throws ValidatorException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the book ID: ");
        Long id = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the book title: ");
        String title = stdin.nextLine();
        System.out.print("Enter the book author: ");
        String author = (stdin.nextLine());
        System.out.print("Enter the book year: ");
        int year = Integer.parseInt(stdin.nextLine());
        System.out.print("Enter the book price: ");
        Integer price = Integer.parseInt(stdin.nextLine());
        System.out.print("Enter the book publishingHouseID: ");
        Integer publishingHouseId = Integer.parseInt(stdin.nextLine());


        BookDto savedBook = restTemplate.postForObject(urlBooks, new BookDto(title, author, year, price, publishingHouseId), BookDto.class);
        System.out.println("saved book:");
        System.out.println(savedBook);
    }

    private void updateBook() throws ValidatorException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the book ID: ");
        Long id = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the book title: ");
        String title = stdin.nextLine();
        System.out.print("Enter the book author: ");
        String author = (stdin.nextLine());
        System.out.print("Enter the book year: ");
        int year = Integer.parseInt(stdin.nextLine());
        System.out.print("Enter the book price: ");
        Integer price = Integer.parseInt(stdin.nextLine());
        System.out.print("Enter the book publishingHouseID: ");
        Integer publishingHouseId = Integer.parseInt(stdin.nextLine());


        BookDto newBook = new BookDto(title, author, year, price, publishingHouseId);
        newBook.setId(id);
        BookDto updatedBook = restTemplate.postForObject(urlBooks, newBook, BookDto.class);
//        restTemplate.put(urlBooks + "/{id}", updatedBook, updatedBook.getId());
        restTemplate.put(urlBooks + "/{id}", updatedBook, id);
        System.out.println("update:");
        System.out.println(restTemplate.getForObject(urlBooks, BooksDto.class));
    }

    private void deleteBook() throws ValidatorException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the book ID: ");
        Long id = Long.valueOf(stdin.nextLine());

        restTemplate.delete(urlBooks + "/{id}", id);
        System.out.println("delete:");
        System.out.println(restTemplate.getForObject(urlBooks, BooksDto.class));
    }

    private void addClientWithBook() {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the bookWithClient ID: ");
        Long id = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the bookWithClient client id: ");
        Long cid = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the bookWithClient book id: ");
        Long bid = Long.valueOf(stdin.nextLine());

        ClientWithBookDto savedClientWithBook = restTemplate.postForObject(urlClients, new ClientWithBookDto(cid, bid), ClientWithBookDto.class);
        System.out.println("saved bookWithClient:");
        System.out.println(savedClientWithBook);
    }

    private void updateClientWithBook() throws ValidatorException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the bookWithClient ID: ");
        Long id = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the new client ID: ");
        Long cid = Long.valueOf(stdin.nextLine());
        System.out.print("Enter the new book ID: ");
        Long bid = Long.valueOf(stdin.nextLine());

        ClientWithBookDto updatedClientWithBook = restTemplate.postForObject(urlClientsWithBook, new ClientWithBookDto(cid, bid), ClientWithBookDto.class);
//        restTemplate.put(urlBooks + "/{id}", updatedClientWithBook, updatedClientWithBook.getId());
        restTemplate.put(urlBooks + "/{id}", updatedClientWithBook, id);
        System.out.println("update:");
        System.out.println(restTemplate.getForObject(urlClientsWithBook, ClientsWithBookDto.class));
    }

    private void deleteClientWithBook() throws ValidatorException {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the bookWithClient ID: ");
        Long id = Long.valueOf(stdin.nextLine());

        restTemplate.delete(urlClientsWithBook + "/{id}", id);
        System.out.println("delete:");
        System.out.println(restTemplate.getForObject(urlClientsWithBook, ClientsWithBookDto.class));
    }

//    private void returnBookClientWithBook() {
//        Scanner stdin = new Scanner(System.in);
//        System.out.print("Enter the bookWithClient ID: ");
//        Long id = Long.valueOf(stdin.nextLine());
//        Future<String> resultFuture = this.clientMessageService.returnBook(id);
//        try {
//            String result = resultFuture.get();
//            System.out.println("***************");
//            System.out.println(result);
//            System.out.println("***************");
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

    private void printAllClientsWithBook() {
        ClientsWithBookDto booksWithClient = restTemplate.getForObject(urlClientsWithBook, ClientsWithBookDto.class);
        System.out.println(booksWithClient);
    }
}
