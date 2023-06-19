package com.exampracticelala.core.Repository.jpa;

import com.exampracticelala.core.Domain.Movie;
import com.exampracticelala.core.Repository.MovieAppRepository;

public interface MovieRepository extends MovieAppRepository<Movie,Long> {
//    List<Movie> findAllByTitle(String title);

//    @Modifying
//    @Query("update movie set stock=:stock  where id = :id")
//    void updateStockById(@Param("id") Long id, @Param("stock") int stock);
}
