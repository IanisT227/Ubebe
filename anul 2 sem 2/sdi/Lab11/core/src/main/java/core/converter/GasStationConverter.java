package core.converter;

import core.domain.GasStation;
import core.dtos.GasStationDto;
import org.springframework.stereotype.Component;

@Component
public class GasStationConverter extends BaseConverter<GasStation, GasStationDto> {
    @Override
    public GasStation convertDtoToModel(GasStationDto dto) {
        return new GasStation(dto.getId(), dto.getName());
    }

    @Override
    public GasStationDto convertModelToDto(GasStation gasstation) {
        GasStationDto dto = new GasStationDto(gasstation.getId(), gasstation.getName());
        dto.setId(gasstation.getId());
        return dto;
    }
}
