package ir.esmaeili.stopcar.di.app

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.repository.RepositoryManagerImpl
import ir.esmaeili.stopcar.repository.database.AppDataBase
import ir.esmaeili.stopcar.repository.local.StaticDataHelperImpl
import ir.esmaeili.stopcar.repository.network.ApiHelperImpl
import ir.esmaeili.stopcar.repository.preferences.PreferencesHelperImpl
import ir.esmaeili.stopcar.utils.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

@Module
class AppModule {
    @Provides
    @AppScope
    fun providerSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @AppScope
    fun providerPersianData(): PersianDate {
        return PersianDate()
    }

    @Provides
    @AppScope
    fun providerMapHelper(context: Context): MapHelper {
        return MapHelper(context)
    }

    @Provides
    @AppScope
    fun providerPersianDateFormat(): PersianDateFormat {
        return PersianDateFormat(Constants.DATE_FORMAT)
    }

    @Provides
    @AppScope
    fun providerRepositoryManager(repositoryManagerImpl: RepositoryManagerImpl): RepositoryManager {
        return repositoryManagerImpl
    }

    @Provides
    @AppScope
    fun providerStaticDataHelperImplementation(context: Context): StaticDataHelperImpl {
        return StaticDataHelperImpl(context)
    }

    @Provides
    @AppScope
    fun providerAppDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java, Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @AppScope
    fun providerUtils(context: Context): Utils {
        return Utils(context)
    }

    @Provides
    @AppScope
    fun providerPreferenceImplementation(sharedPreferences: SharedPreferences): PreferencesHelperImpl {
        return PreferencesHelperImpl(sharedPreferences)
    }

    @Provides
    @AppScope
    fun providerApiHelper(retrofit: Retrofit): ApiHelperImpl {
        return ApiHelperImpl(retrofit)
    }

    @Provides
    @AppScope
    fun providerOKHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor()).build()
    }

    @Provides
    @AppScope
    fun providerRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.GEO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun providerGsonConvertFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @AppScope
    fun providerGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @AppScope
    fun providerViewModelFactory(repositoryManager: RepositoryManager): SimpleViewModelProviderFactory {
        return SimpleViewModelProviderFactory(repositoryManager)
    }
}