package com.exampracticelala.web.converter;

import com.exampracticelala.core.Domain.BaseEntity;
import com.exampracticelala.web.dto.BaseDto;

/**
 * Created by radu.
 */

public interface ConverterBaseEntity<Model extends BaseEntity<Long>, Dto extends BaseDto> extends Converter<Model, Dto> {

}

