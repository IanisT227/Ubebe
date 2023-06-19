package core.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rentalfirm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalFirm extends BaseEntity<Integer> {
    @NonNull
    private String name;

    public RentalFirm(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
