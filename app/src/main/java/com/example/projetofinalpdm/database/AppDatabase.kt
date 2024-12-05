package com.example.projetofinalpdm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projetofinalpdm.dao.ProjectDAO
import com.example.projetofinalpdm.dao.TaskDAO
import com.example.projetofinalpdm.model.Project
import com.example.projetofinalpdm.model.Task

@Database(entities = [Project::class, Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDAO() : ProjectDAO
    abstract fun taskDAO() : TaskDAO
}