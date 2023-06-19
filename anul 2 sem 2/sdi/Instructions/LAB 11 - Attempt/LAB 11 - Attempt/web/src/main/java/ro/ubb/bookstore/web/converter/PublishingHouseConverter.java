package ro.ubb.bookstore.web.converter;

import ro.ubb.bookstore.core.Domain.PublishingHouse;
import ro.ubb.bookstore.web.dto.PublishingHouseDto;
import org.springframework.stereotype.Component;

@Component
public class PublishingHouseConverter extends BaseConverter<PublishingHouse, PublishingHouseDto> {
    @Override
    public PublishingHouse convertDtoToModel(PublishingHouseDto dto) {
        var model = new PublishingHouse();
        model.setId(dto.getId());
        model.setCountry(dto.getCountry());
        model.setName(dto.getName());
        return model;
    }

    @Override
    public PublishingHouseDto convertModelToDto(PublishingHouse address) {
        PublishingHouseDto dto = new PublishingHouseDto(address.getCountry(), address.getName());
        dto.setId(address.getId());
        return dto;
    }
}
