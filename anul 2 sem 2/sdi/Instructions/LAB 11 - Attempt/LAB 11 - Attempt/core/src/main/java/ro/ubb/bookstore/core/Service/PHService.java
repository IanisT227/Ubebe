package ro.ubb.bookstore.core.Service;

import ro.ubb.bookstore.core.Domain.PublishingHouse;

import java.util.List;

public interface PHService {
    List<PublishingHouse> getCountries();

    PublishingHouse saveCountry(PublishingHouse publishingHouse);

    PublishingHouse updateCountry(PublishingHouse publishingHouse);

    void deletePublishingHouse(Long id);
}
