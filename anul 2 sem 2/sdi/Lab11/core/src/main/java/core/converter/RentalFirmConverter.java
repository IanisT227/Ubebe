package core.converter;

import core.domain.RentalFirm;
import core.dtos.RentalFirmDto;
import org.springframework.stereotype.Component;

@Component
public class RentalFirmConverter extends BaseConverter<RentalFirm, RentalFirmDto> {
    @Override
    public RentalFirm convertDtoToModel(RentalFirmDto dto) {
        return new RentalFirm(dto.getId(), dto.getName());
    }

    @Override
    public RentalFirmDto convertModelToDto(RentalFirm rentalfirm) {
        RentalFirmDto dto = new RentalFirmDto(rentalfirm.getId(), rentalfirm.getName());
        dto.setId(rentalfirm.getId());
        return dto;
    }
}
