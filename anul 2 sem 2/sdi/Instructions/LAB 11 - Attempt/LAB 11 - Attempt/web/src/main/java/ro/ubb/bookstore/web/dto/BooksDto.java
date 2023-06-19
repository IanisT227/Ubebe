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
public class BooksDto {
    private List<BookDto> books;
}
