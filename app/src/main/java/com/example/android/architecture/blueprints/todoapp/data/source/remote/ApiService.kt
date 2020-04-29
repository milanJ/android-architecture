package com.example.android.architecture.blueprints.todoapp.data.source.remote

import com.example.android.architecture.blueprints.todoapp.data.model.Task
import retrofit2.http.*

interface ApiService {

    @GET("get_tasks.php")
    suspend fun getTasks(): List<Task>

    @GET("get_task.php")
    suspend fun getTask(@Query(value = "taskId") taskId: String): Task

    @POST("task_create.php")
    suspend fun saveTask(@Body task: Task)

    @PUT("complete_task.php")
    suspend fun completeTask(@Query(value = "taskId") taskId: String)

    @PUT("activate_task.php")
    suspend fun activateTask(@Query(value = "taskId") taskId: String)

    @PUT("clear_completed_tasks.php")
    suspend fun clearCompletedTasks()

    @DELETE("delete_all_tasks.php")
    suspend fun deleteAllTasks()

    @DELETE("delete_task.php")
    suspend fun deleteTask(@Query(value = "taskId") taskId: String)
}
