package ro.ubb.bookstore.core.Domain.validators;


import ro.ubb.bookstore.core.Domain.Client;
import ro.ubb.bookstore.core.Domain.exceptions.ValidatorException;

import java.io.Serializable;

public class ClientValidator implements Validator<Client>, Serializable {

    @Override
    public void validate(Client entity) throws ValidatorException {
        if (entity.getName().split(" ").length < 2)
            throw new ValidatorException("Client name must have at least 2 words");
        if (entity.getTotalBalance() < 0)
            throw new ValidatorException("Total balance cannot be negative");
    }
}
