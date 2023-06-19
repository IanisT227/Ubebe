package ro.ubb.bookstore.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
//@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClientWithBookDto extends BaseDto {
    private Long cid;
    private Long bid;
//    private LocalDate rentalDate;
//    private LocalDate returnDate;
}
