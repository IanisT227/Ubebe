package ro.ubb.bookstore.core.Service;

import ro.ubb.bookstore.core.Domain.PublishingHouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.bookstore.core.Repository.jpa.PublishingHouseRepository;

import java.util.List;

@Service
public class PublishingHouseService {
    private static Logger logger = LoggerFactory.getLogger(PublishingHouseService.class);
    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    public PublishingHouse savePublishingHouse(PublishingHouse ph) {
        PublishingHouse ph1 = publishingHouseRepository.save(ph);
        logger.info(ph1.toString());
        return ph1;
    }

    @Transactional
    public PublishingHouse updatePublishingHouse(PublishingHouse ph) {
        PublishingHouse updateph = publishingHouseRepository.findById(ph.getId()).orElseThrow();
        updateph.setCountry(ph.getCountry());
        updateph.setName(ph.getName());
        logger.info(updateph.toString());
        return ph;
    }

    public List<PublishingHouse> getPublishingHouses() {
        List<PublishingHouse> publishingHouses = this.publishingHouseRepository.findAll();
        publishingHouses.forEach(ph -> logger.info(ph.toString()));
        return publishingHouses;
    }

    public void deletePublishingHouse(Long id) {
        publishingHouseRepository.deleteById(id);
        logger.info("deleted " + id);
    }

    public List<PublishingHouse> getSorted() {
        logger.info("get sorted");
        List<PublishingHouse> publishingHouses = this.publishingHouseRepository.findAll(Sort.by(Sort.Direction.DESC, "country"));
        publishingHouses.forEach(a -> logger.info(a.toString()));
        return publishingHouses;
    }
}
