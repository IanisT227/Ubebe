package core.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car extends BaseEntity<Integer> {
    @NonNull
    private String brand;
    private Integer rentalfirmId;

    public Car(Integer id, String brand, Integer rentalfirmId) {
        this.id = id;
        this.brand = brand;
        this.rentalfirmId = rentalfirmId;
    }
}
