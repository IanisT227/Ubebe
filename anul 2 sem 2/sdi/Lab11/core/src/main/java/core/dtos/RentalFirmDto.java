package core.dtos;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RentalFirmDto extends BaseDto implements Serializable {
    @NonNull
    private String name;

    public RentalFirmDto(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
