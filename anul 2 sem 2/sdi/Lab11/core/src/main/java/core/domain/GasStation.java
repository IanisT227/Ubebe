package core.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "gasstation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GasStation extends BaseEntity<Integer> {
    @NonNull
    private String name;

    public GasStation(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
