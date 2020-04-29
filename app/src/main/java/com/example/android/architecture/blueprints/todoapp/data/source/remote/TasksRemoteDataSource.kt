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
package com.example.android.architecture.blueprints.todoapp.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Result.Error
import com.example.android.architecture.blueprints.todoapp.data.Result.Success
import com.example.android.architecture.blueprints.todoapp.data.model.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Network implementation of the data source.
 */
class TasksRemoteDataSource internal constructor(
        private val apiService: ApiService,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksDataSource {

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(apiService.getTasks())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getTask(taskId: String): Result<Task> = withContext(ioDispatcher) {
        try {
            return@withContext Success(apiService.getTask(taskId))
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
        apiService.saveTask(task)
    }

    override suspend fun completeTask(task: Task) = withContext(ioDispatcher) {
        apiService.completeTask(task.id)
    }

    override suspend fun completeTask(taskId: String) {
        apiService.completeTask(taskId)
    }

    override suspend fun activateTask(task: Task) = withContext(ioDispatcher) {
        apiService.activateTask(task.id)
    }

    override suspend fun activateTask(taskId: String) {
        apiService.activateTask(taskId)
    }

    override suspend fun clearCompletedTasks() = withContext<Unit>(ioDispatcher) {
        apiService.clearCompletedTasks()
    }

    override suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        apiService.deleteAllTasks()
    }

    override suspend fun deleteTask(taskId: String) = withContext(ioDispatcher) {
        apiService.deleteTask(taskId)
    }
}
