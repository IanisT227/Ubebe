package ro.ubb.bookstore.web.converter;

import ro.ubb.bookstore.core.Domain.Client;
import ro.ubb.bookstore.web.dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        var model = new Client();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setTotalBalance(dto.getTotalBalance());
        return model;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto dto = new ClientDto(client.getName(), client.getTotalBalance());
        dto.setId(client.getId());
        return dto;
    }
}
