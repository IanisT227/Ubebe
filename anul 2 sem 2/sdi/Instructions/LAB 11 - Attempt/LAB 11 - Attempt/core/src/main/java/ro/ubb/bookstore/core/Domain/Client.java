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
public class Client extends BaseEntity<Long> {
    private String name;
    private Integer totalBalance;
//    private Set<Book> boughtBooks;
}
