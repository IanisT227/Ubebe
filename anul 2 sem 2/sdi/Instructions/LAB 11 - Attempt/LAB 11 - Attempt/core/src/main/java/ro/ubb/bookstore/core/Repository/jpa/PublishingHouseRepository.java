package ro.ubb.bookstore.core.Repository.jpa;

import ro.ubb.bookstore.core.Domain.PublishingHouse;
import ro.ubb.bookstore.core.Repository.Repository;

import java.util.List;

public interface PublishingHouseRepository extends Repository<Long, PublishingHouse> {
    List<PublishingHouse> findByOrderByCountryDesc();
}
