package com.example.aneon_test_case.di

import android.content.Context
import com.example.aneon_test_case.data.repositoryImpl.DataStoreGetUserRepository
import com.example.aneon_test_case.data.repositoryImpl.DataStoreLogoutRepository
import com.example.aneon_test_case.data.repositoryImpl.DataStoreSaveRepository
import com.example.aneon_test_case.domain.repository.DataStoreRepositoryGetUser
import com.example.aneon_test_case.domain.repository.DataStoreRepositoryLogout
import com.example.aneon_test_case.domain.repository.DataStoreRepositorySave
import com.example.aneon_test_case.domain.usecase.DataStoreGetUserUseCase
import com.example.aneon_test_case.domain.usecase.DataStoreLogoutUseCase
import com.example.aneon_test_case.domain.usecase.DataStoreSaveUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStoreSaveUseCase(dataStoreRepositorySave: DataStoreRepositorySave): DataStoreSaveUseCase {
        return DataStoreSaveUseCase(dataStoreRepositorySave)
    }

    @Provides
    @Singleton
    fun provideDataStoreLogoutUseCase(dataStoreRepositoryLogout: DataStoreRepositoryLogout): DataStoreLogoutUseCase {
        return DataStoreLogoutUseCase(dataStoreRepositoryLogout)
    }

    @Provides
    @Singleton
    fun provideDataStoreGetUserUseCase(dataStoreRepositoryGetUser: DataStoreRepositoryGetUser): DataStoreGetUserUseCase {
        return DataStoreGetUserUseCase(dataStoreRepositoryGetUser)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepositorySave(@ApplicationContext context: Context): DataStoreRepositorySave {
        return DataStoreSaveRepository(context)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepositoryLogout(@ApplicationContext context: Context): DataStoreRepositoryLogout {
        return DataStoreLogoutRepository(context)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepositoryGetUser(@ApplicationContext context: Context): DataStoreRepositoryGetUser {
        return DataStoreGetUserRepository(context)
    }
}


