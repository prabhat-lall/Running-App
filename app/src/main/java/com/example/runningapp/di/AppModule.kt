package com.example.runningapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.runningapp.db.RunDAO
import com.example.runningapp.db.RunningDatabase
import com.example.runningapp.utility.Constants
import com.example.runningapp.utility.Constants.KEY_FIRST_TIME_TOGGLE
import com.example.runningapp.utility.Constants.KEY_NAME
import com.example.runningapp.utility.Constants.KEY_WEIGHT
import com.example.runningapp.utility.Constants.SHARED_PREFERENCE_NAME
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
    fun provideRunningDatabase(@ApplicationContext context: Context): RunningDatabase {
        return Room.databaseBuilder(
            context,
            RunningDatabase::class.java,
            Constants.RUNNING_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRunDao(db:RunningDatabase): RunDAO {
        return db.getRunDao()
    }

    @Singleton
    @Provides
    fun providesSharedPreference(@ApplicationContext app:Context) =
        app.getSharedPreferences(SHARED_PREFERENCE_NAME,MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPref:SharedPreferences) = sharedPref.getString(KEY_NAME,"")?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref:SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT,80f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref:SharedPreferences) = sharedPref.getBoolean(
        KEY_FIRST_TIME_TOGGLE,true)

}