package com.anoniscoding.mimo.injection

import com.anoniscoding.mimo.cache.service.LessonCacheImpl
import com.anoniscoding.mimo.data.LessonRepositoryImpl
import com.anoniscoding.mimo.data.contract.LessonCache
import com.anoniscoding.mimo.domain.contract.LessonRepository
import com.anoniscoding.mimo.remote.LessonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class LessonModule {
    @Provides
    fun provideLessonService(retrofit: Retrofit): LessonService = retrofit.create()

    @Provides
    fun provideLessonRepository(lessonRepositoryImpl: LessonRepositoryImpl): LessonRepository =
        lessonRepositoryImpl

    @Provides
    fun provideLessonCache(lessonCacheImpl: LessonCacheImpl): LessonCache =
        lessonCacheImpl
}