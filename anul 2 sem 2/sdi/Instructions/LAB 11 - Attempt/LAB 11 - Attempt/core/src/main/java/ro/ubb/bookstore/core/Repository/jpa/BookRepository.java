package ro.ubb.bookstore.core.Repository.jpa;

import ro.ubb.bookstore.core.Domain.Book;
import ro.ubb.bookstore.core.Repository.Repository;

import java.util.List;

public interface BookRepository extends Repository<Long, Book> {
    List<Book> findAllByTitle(String title);

//    @Modifying
//    @Query("update book set price=:price  where id = :id")
//    void updatePriceById(@Param("id") Long id, @Param("price") int price);
}
