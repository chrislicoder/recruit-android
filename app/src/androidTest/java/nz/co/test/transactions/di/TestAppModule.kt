package nz.co.test.transactions.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.co.test.transactions.data.TransactionRepository
import nz.co.test.transactions.services.TransactionApi
import org.mockito.Mockito
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    fun provideMockTransactionApi(): TransactionApi {
        return MockedTransactionApi()
    }

    @Singleton
    @Provides
    fun provideMockTransactionRepository(): TransactionRepository {
        return MockedTransactionRepository()
    }
}
