package com.example.projetofinalpdm.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.projetofinalpdm.adapter.PrioritySpinnerAdapter
import com.example.projetofinalpdm.adapter.ProjectsSpinnerAdapter
import com.example.projetofinalpdm.adapter.TaskAdapter
import com.example.projetofinalpdm.dao.TaskDAO
import com.example.projetofinalpdm.database.AppDatabase
import com.example.projetofinalpdm.databinding.ActivityTasksBinding
import com.example.projetofinalpdm.databinding.FrameNewTaskBinding
import com.example.projetofinalpdm.model.Project
import com.example.projetofinalpdm.model.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTasksBinding
    private lateinit var taskDAO: TaskDAO
    private lateinit var db: AppDatabase
    private lateinit var adapter: TaskAdapter
    private var projectId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        projectId = intent.getIntExtra("PROJECT_ID", -1)

        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-db"
        )
            .fallbackToDestructiveMigration()
            .build()


        taskDAO = db.taskDAO()

        adapter = TaskAdapter(
            onToggleTask = { task ->
                finishTask(task)
            })
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = adapter

        binding.addTask.setOnClickListener {
            showAddTask()
        }
        reloadTaskList(db)
    }

    private fun finishTask(task: Task) {
        lifecycleScope.launch {
            try {
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "app-db"
                ).build()

                task.status = true
                db.taskDAO().updateTask(task)

            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Erro ao salvar o projeto: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun reloadTaskList(db: AppDatabase){
        lifecycleScope.launch {

            val taskList = db.taskDAO().getTasksByProject(projectId)

            val filteredList = taskList.filter{ !it.status }
            adapter.submitList(filteredList)
        }
    }


    private fun showAddTask() {
        val dialogBinding = FrameNewTaskBinding.inflate(layoutInflater)
        var newTask: Task
        val priorities = listOf("P1 - Urgente", "P2 - Alta", "P3 - Média", "P4 - Baixa")
        val colors = listOf(
            android.graphics.Color.RED,
            android.graphics.Color.BLACK,
            android.graphics.Color.BLACK,
            android.graphics.Color.BLACK
        )



        lifecycleScope.launch {
            try {
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "app-db"
                ).build()

                val projects = db.projectDAO().getAllProjects()

                val projectAdapter = ProjectsSpinnerAdapter(this@TaskActivity, projects)
                val priorityAdapter = PrioritySpinnerAdapter(this@TaskActivity, priorities, colors)

                dialogBinding.projectSpinner.adapter = projectAdapter
                dialogBinding.prioritySpinner.adapter = priorityAdapter

            } catch (e: Exception) {
                Snackbar.make(dialogBinding.root, "Erro ao carregar projetos: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
        }

        AlertDialog.Builder(this)
            .setTitle("Adicionar nova Tarefa")
            .setView(dialogBinding.root)
            .setPositiveButton("Adicionar Tarefa") { _, _ ->
                val name = dialogBinding.fieldTaskName.text.toString().trim()
                val selectedPriority = dialogBinding.prioritySpinner.selectedItemPosition + 1
                val selectedProject = dialogBinding.projectSpinner.selectedItem as Project


                if (name.isEmpty()) {
                    Snackbar.make(dialogBinding.root, "O nome não pode estar vazio", Snackbar.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                lifecycleScope.launch {
                    try {
                        newTask = Task(
                            projectId = selectedProject.id,
                            title = name,
                            priority = selectedPriority,
                            status = false
                        )
                        db.taskDAO().insertTask(newTask)
                        reloadTaskList(db)
                    } catch (e: Exception) {
                        Snackbar.make(dialogBinding.root, "Erro ao adicionar tarefa: ${e.message}", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}

