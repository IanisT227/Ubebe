package ro.ubb.bookstore.web.converter;

import ro.ubb.bookstore.core.Domain.ClientWithBook;
import ro.ubb.bookstore.web.dto.ClientWithBookDto;
import org.springframework.stereotype.Component;

@Component
public class ClientWithBookConverter extends BaseConverter<ClientWithBook, ClientWithBookDto> {
    @Override
    public ClientWithBook convertDtoToModel(ClientWithBookDto dto) {
        var model = new ClientWithBook();
        model.setId(dto.getId());
        model.setCid(dto.getCid());
        model.setBid(dto.getBid());
        return model;
    }

    @Override
    public ClientWithBookDto convertModelToDto(ClientWithBook rental) {
        ClientWithBookDto dto = new ClientWithBookDto(rental.getCid(), rental.getBid());
        dto.setId(rental.getId());
        return dto;
    }
}
