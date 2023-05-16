package com.amalitech.core.di

import com.amalitech.core.data.BookMeetingRepositoryImpl
import com.amalitech.core.domain.BookMeetingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

  @Binds
  @ActivityRetainedScoped
  abstract fun bindAnimalRepository(repository: BookMeetingRepositoryImpl): BookMeetingRepository

}
