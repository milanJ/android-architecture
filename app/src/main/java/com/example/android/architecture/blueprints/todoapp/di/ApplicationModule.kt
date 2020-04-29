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

package com.example.android.architecture.blueprints.todoapp.di

import android.content.Context
import com.example.android.architecture.blueprints.todoapp.data.GeneralPreferences
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import milan.common.other.EventBus
import javax.inject.Singleton


@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideGeneralPreferences(context: Context, moshi: Moshi) = GeneralPreferences(context.applicationContext, moshi)

    @JvmStatic
    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder().build();

    @JvmStatic
    @Singleton
    @Provides
    fun provideEventBus() = EventBus

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
abstract class ApplicationModuleBinds {

//    @Singleton
//    @Binds
//    abstract fun bindRepository(repo: DefaultTasksRepository): TasksRepository
}
