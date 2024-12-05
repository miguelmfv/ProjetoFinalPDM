package com.example.projetofinalpdm.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.projetofinalpdm.adapter.ProjectAdapter
import com.example.projetofinalpdm.database.AppDatabase
import com.example.projetofinalpdm.databinding.ActivityMainBinding
import com.example.projetofinalpdm.databinding.FrameNewProjectBinding
import com.example.projetofinalpdm.model.Project
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-db"
        ).fallbackToDestructiveMigration()
            .build()

        reloadProjectList(db)
        adapter = ProjectAdapter()
        binding.recyclerViewProjects.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProjects.adapter = adapter



        binding.btnProjects.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.addProject.setOnClickListener {
            showNewProject()
            reloadProjectList(db)
        }
    }

    private fun reloadProjectList(db: AppDatabase){
        lifecycleScope.launch {
            val projectList = db.projectDAO().getAllProjects()
            projectList.forEach{
                val taskCounter = db.taskDAO().getTasksByProject(it.id).size
                it.taskCounter = taskCounter
                db.projectDAO().updateProject(it)
            }
            adapter.submitList(projectList)
        }
    }

    private fun showNewProject() {
        val dialogBinding = FrameNewProjectBinding.inflate(layoutInflater)

        AlertDialog.Builder(this)
            .setTitle("Novo Projeto")
            .setView(dialogBinding.root)
            .setPositiveButton("Adicionar Projeto") { _, _ ->
                val nome = dialogBinding.fieldNewProject.text.toString().trim()
                val desc = dialogBinding.fieldDescNProject.text.toString().trim()

                if (nome.isNotEmpty() && desc.isNotEmpty()) {
                    lifecycleScope.launch {
                        try {
                            val db = Room.databaseBuilder(
                                applicationContext,
                                AppDatabase::class.java, "app-db"
                            ).fallbackToDestructiveMigration()
                                .build()

                            val novoProjeto = Project(name = nome, desc = desc, taskCounter = 0)

                            db.projectDAO().insertProject(novoProjeto)

                            reloadProjectList(db)

                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(applicationContext, "Erro ao salvar o projeto: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    Toast.makeText(this, "Preencha os Campos Acima!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
    private fun onProjectClick(projectId: Int) {
        val intent = Intent(this, TaskActivity::class.java)
        intent.putExtra("PROJECT_ID", projectId)
        startActivity(intent)
    }
}