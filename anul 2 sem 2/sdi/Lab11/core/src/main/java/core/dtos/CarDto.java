package core.dtos;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CarDto extends BaseDto implements Serializable {
    @NonNull
    private String brand;

    @NonNull
    private Integer rentalfirmId;

    public CarDto(Integer id, String brand, Integer rentalfirmId) {
        super(id);
        this.brand = brand;
        this.rentalfirmId = rentalfirmId;
    }
}
