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
public class ClientWithBook extends BaseEntity<Long> {
    private Long cid;
    private Long bid;

    public ClientWithBook(Long id, Long cid, Long bid) {
        super.setId(id);
        this.cid = cid;
        this.bid = bid;
    }
}
