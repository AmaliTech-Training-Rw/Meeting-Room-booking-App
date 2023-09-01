package com.amalitech.core.data.model.mappers

interface UiMapper<E, V> {
  fun mapToView(input: E): V
//  fun mapToDomain(viewData: V): E
}
