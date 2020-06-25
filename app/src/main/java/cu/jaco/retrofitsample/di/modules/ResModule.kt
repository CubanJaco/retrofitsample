package cu.jaco.retrofitsample.di.modules

import com.google.gson.GsonBuilder
import cu.jaco.retrofitsample.BuildConfig
import cu.jaco.retrofitsample.api.ApiInterface
import cu.jaco.retrofitsample.utils.Consts
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ResModule {

    @Singleton
    @Provides
    fun providerOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
            .connectTimeout(45000L, TimeUnit.MILLISECONDS)
            .readTimeout(45000L, TimeUnit.MILLISECONDS)
            .writeTimeout(45000L, TimeUnit.MILLISECONDS)
    }

    @Singleton
    @Provides
    fun providerRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }

    @Singleton
    @Provides
    @Named("authRetrofit")
    fun providerAuthRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit {

        val client = okHttpClientBuilder.apply {

            if (BuildConfig.DEBUG)
                addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })


        }.build()

        return retrofitBuilder
            .client(client)
            .baseUrl(Consts.API_URL)
            .build()
    }

    @Singleton
    @Provides
    fun providerServices(@Named("authRetrofit") retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}