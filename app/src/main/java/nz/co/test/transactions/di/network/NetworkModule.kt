package nz.co.test.transactions.di.network

import dagger.Module
import dagger.Provides
import nz.co.test.transactions.services.TransactionsService
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/Josh-Ng/500f2716604dc1e8e2a3c6d31ad01830/raw/4d73acaa7caa1167676445c922835554c5572e82/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionService(retrofit: Retrofit): TransactionsService {
        return retrofit.create(TransactionsService::class.java)
    }
}