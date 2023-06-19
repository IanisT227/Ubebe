package ro.ubb.bookstore.core.Domain;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
//@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Book extends BaseEntity<Long> {
    private String title;
    private String author;
    private Integer year;
    private Integer price;
    private Integer publishingHouseID;
}
