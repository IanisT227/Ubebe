package core.converter;

import core.domain.BaseEntity;
import core.dtos.BaseDto;

/**
 * Created by radu.
 */

public abstract class BaseConverter<Model extends BaseEntity<Integer>, Dto extends BaseDto>
        implements Converter<Model, Dto> {


//    public Set<Long> convertModelsToIDs(Set<Model> models) {
//        return models.stream()
//                .map(model -> model.getId())
//                .collect(Collectors.toSet());
//    }
//
//    public Set<Long> convertDTOsToIDs(Set<Dto> dtos) {
//        return dtos.stream()
//                .map(dto -> dto.getId())
//                .collect(Collectors.toSet());
//    }
//
//    public Set<Dto> convertModelsToDtos(Collection<Model> models) {
//        return models.stream()
//                .map(model -> convertModelToDto(model))
//                .collect(Collectors.toSet());
//    }
}
