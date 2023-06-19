package ro.ubb.bookstore.web.dto;

import lombok.*;
import ro.ubb.bookstore.core.Domain.ClientWithBook;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ClientsWithBookDto {
    private List<ClientWithBookDto> clientswithbook;
}
