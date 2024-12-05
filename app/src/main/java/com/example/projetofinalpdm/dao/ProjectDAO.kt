package com.example.projetofinalpdm.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.projetofinalpdm.model.Project

@Dao
interface ProjectDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project)

    @Delete
    suspend fun deleteProject(project: Project)

    @Update
    suspend fun updateProject(project: Project) : Int

    @Query("SELECT * FROM project WHERE project.id == :projectId")
    fun getProjectById(projectId: Int): Project

    @Query("SELECT * FROM project")
    suspend fun getAllProjects() : List<Project>
}