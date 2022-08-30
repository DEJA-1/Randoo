package com.example.randoo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.randoo.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Task>>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task_table ORDER BY random() LIMIT 1")
    fun findRandom(): LiveData<Task>
}