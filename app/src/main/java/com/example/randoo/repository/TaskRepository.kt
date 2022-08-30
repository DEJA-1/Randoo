package com.example.randoo.repository

import androidx.lifecycle.LiveData
import com.example.randoo.data.TaskDao
import com.example.randoo.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllData()
    val randomTask: LiveData<Task> = taskDao.findRandom()

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }

}