package com.example.projetofinalpdm.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projetofinalpdm.model.Task

@Dao
interface TaskDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task) : Int

    @Query("""SELECT * FROM task
        WHERE id = :taskId
    """)
    suspend fun getTaskById(taskId: Int) : Task

    @Query("""SELECT * FROM task""")
    suspend fun getAllTasks() : List<Task>

    @Query("""SELECT * FROM task
        WHERE projectId = :projectId
    """)
    suspend fun getTasksByProject(projectId: Int) : List<Task>
}