package ru.orlovvv.ort.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.orlovvv.ort.api.Api
import ru.orlovvv.ort.db.OrtDatabase
import ru.orlovvv.ort.util.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOrtDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        OrtDatabase::class.java,
        "ort_db.db"
    ).build()

    @Singleton
    @Provides
    fun provideOrtDAO(db: OrtDatabase) = db.getOrtDao()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) = retrofit.create(Api::class.java)

}