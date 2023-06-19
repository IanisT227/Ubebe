package ro.ubb.bookstore.core.Domain;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PublishingHouse extends BaseEntity<Long> {
    private String country;
    private String name;
}
