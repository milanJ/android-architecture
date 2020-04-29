/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.data

import android.content.Context
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.BuildConfig
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.ApiService
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME


@Module(includes = [DataModuleBinds::class])
object DataModule {

    private const val CONNECT_READ_WRITE_TIMEOUT = 60000L

    @Qualifier
    @Retention(RUNTIME)
    annotation class TasksRemoteDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class TasksLocalDataSource

    @JvmStatic
    @Singleton
    @TasksRemoteDataSource
    @Provides
    fun provideTasksRemoteDataSource(apiService: ApiService, ioDispatcher: CoroutineDispatcher): TasksDataSource {
        return TasksRemoteDataSource(apiService, ioDispatcher)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideApiService(moshi: Moshi, loggingInterceptor: HttpLoggingInterceptor, generalPreferences: GeneralPreferences): ApiService {
        val headerInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestBuilder = chain.request()
                        .newBuilder()
                        .apply {
                            val authToken = generalPreferences.authToken
                            if (authToken != null) {
                                val token = StringBuilder(7 + authToken.length)
                                        .append("Bearer ")
                                        .append(authToken)
                                        .toString()

                                addHeader("Authorization", token)
                            }
                            addHeader("Client", "ANDROID")
                        }
                return chain.proceed(requestBuilder.build())
            }
        }

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .connectTimeout(CONNECT_READ_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(CONNECT_READ_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(CONNECT_READ_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build()

        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BuildConfig.BASE_URL_API)
                .build().create(ApiService::class.java)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @JvmStatic
    @Singleton
    @TasksLocalDataSource
    @Provides
    fun provideTasksLocalDataSource(
            database: ToDoDatabase,
            ioDispatcher: CoroutineDispatcher
    ): TasksDataSource {
        return TasksLocalDataSource(
                database.taskDao(), ioDispatcher
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): ToDoDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                ToDoDatabase::class.java,
                "Tasks.db"
        ).build()
    }
}

@Module
abstract class DataModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultTasksRepository): TasksRepository
}
