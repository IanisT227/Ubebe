package core.converter;

import core.domain.Fueling;
import core.dtos.FuelingDto;
import org.springframework.stereotype.Component;

@Component
public class FuelingConverter {
    public Fueling convertDtoToModel(FuelingDto dto) {
        return new Fueling(dto.getCarId(), dto.getGasStationId());
    }

    public FuelingDto convertModelToDto(Fueling articleJournalist) {
        return new FuelingDto(articleJournalist.getArticleId(), articleJournalist.getJournalistId());
    }
}
