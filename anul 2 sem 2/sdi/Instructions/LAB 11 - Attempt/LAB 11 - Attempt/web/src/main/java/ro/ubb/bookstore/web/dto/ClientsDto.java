package ro.ubb.bookstore.web.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ClientsDto {
    private List<ClientDto> clients;
}
