package ro.ubb.bookstore.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
//@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BookDto  extends BaseDto{
    private String title;
    private String author;
    private Integer year;
    private Integer price;
    private Integer publishingHouseID;
}
