package core.converter;

import core.domain.BaseEntity;
import core.dtos.BaseDto;

/**
 * Created by radu.
 */

public interface Converter<Model extends BaseEntity<Integer>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}

