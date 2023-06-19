package core.dtos;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class GasStationDto extends BaseDto implements Serializable {
    private String name;

    public GasStationDto(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
