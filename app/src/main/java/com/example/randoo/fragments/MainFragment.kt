package com.example.randoo.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randoo.R
import com.example.randoo.adapter.RecyclerAdapter
import com.example.randoo.viewmodel.TaskViewModel
import com.example.randoo.databinding.FragmentMainBinding
import com.example.randoo.model.Task
import java.util.*

class MainFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    //viewModel
    private lateinit var mTaskViewModel: TaskViewModel
    private var savedAdapter: RecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //adding new task
        binding.fabAdding.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment22)
        }

        //recycler view
        val recyclerView = binding.tasksRecyclerview
        //adapter
        savedAdapter = RecyclerAdapter()

        //recycler view settings
        recyclerView.apply{
            adapter = savedAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        //viewModel
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        mTaskViewModel.readAllData.observe(viewLifecycleOwner, Observer {task ->
            savedAdapter!!.setTasks(task)
        })

        //setting up the delete button
        setHasOptionsMenu(true)

        //setting up the random option button
        binding.fabRandom.setOnClickListener{
            showRandomTask()
        }

    }

    private fun showRandomTask() {
        //drawing the randomTask from the database
        val Tasks = mTaskViewModel.readAllData
        //in order to avoid crashing, random select option is enabled only, when the user entered at least 2 tasks
        val len = Tasks.value?.size

        if (len != null) {
            if (len >= 2){
                val randomTask = Tasks.value?.random()

                //building the alert dialog indicating drawn task
                val builder = AlertDialog.Builder(requireContext())
                builder.setNeutralButton("Ok") {_, _ -> }
                builder.setTitle("Here's your random task!")
                builder.setMessage("Your random task is: ${randomTask?.task_text}")
                builder.create().show()
            } else {
                Toast.makeText(requireContext(), "You need at least 2 tasks to use random select!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mTaskViewModel.deleteAllTasks()
            Toast.makeText(requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


