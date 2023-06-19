package core.dtos;

import java.io.Serializable;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class FuelingDto implements Serializable {
    private Integer carId;
    private Integer gasStationId;
}
