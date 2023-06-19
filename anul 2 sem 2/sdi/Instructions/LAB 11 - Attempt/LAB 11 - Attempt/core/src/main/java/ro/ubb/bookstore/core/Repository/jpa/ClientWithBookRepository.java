package ro.ubb.bookstore.core.Repository.jpa;


import ro.ubb.bookstore.core.Domain.ClientWithBook;
import ro.ubb.bookstore.core.Repository.Repository;

public interface ClientWithBookRepository extends Repository<Long, ClientWithBook> {
//    @Modifying
//    @Query("delete from clientwithbook where cid = :cid")
//    void deleteClientsByCid(@Param("cid") Long cid);
//
//    @Modifying
//    @Query("delete from clientwithbook where bid = :bid")
//    void deleteBooksByBid(@Param("bid") Long bid);

}
