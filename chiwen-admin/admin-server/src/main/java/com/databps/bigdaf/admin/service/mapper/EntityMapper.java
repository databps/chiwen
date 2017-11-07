package com.databps.bigdaf.admin.service.mapper;

import java.util.List;

/**
 * @author merlin
 * @create 2017-07-26 上午1:17
 */

public interface EntityMapper<V, E> {

  E toEntity(V dto);

  V toVO(E entity);

  List<E> toEntity(List<V> dtoList);

  List<V> toVO(List<E> entityList);

}
