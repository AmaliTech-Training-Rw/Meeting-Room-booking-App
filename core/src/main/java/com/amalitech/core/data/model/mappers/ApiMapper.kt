package com.amalitech.core.data.model.mappers

interface ApiMapper<E, D> {
  fun mapToDomain(apiEntity: E): D
}
