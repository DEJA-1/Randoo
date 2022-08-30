package com.example.randoo.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.randoo.R
import com.example.randoo.databinding.FragmentMainBinding
import com.example.randoo.databinding.FragmentUpdateBinding
import com.example.randoo.model.Task
import com.example.randoo.viewmodel.TaskViewModel


class UpdateFragment : Fragment() {
    //saveArgs
    private val args by navArgs<UpdateFragmentArgs>()

    //viewBinding
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        //updating the text
        binding.updateTaskText.setText(args.currentTask.task_text)

        //setting up the click listener for saving changes
        binding.updateTaskButton.setOnClickListener {
            updateItem()
        }

        //setting up the delete option
        setHasOptionsMenu(true)
    }

    //updating the item
    private fun updateItem(){
        val taskName = binding.updateTaskText.text.toString()

        if (inputCheck(taskName)){
            //create task
            val updatedTask = Task(args.currentTask.id, args.currentTask.status, taskName)
            //update the task
            mTaskViewModel.updateTask(updatedTask)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_LONG).show()
            //navigate back to the main fragment
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
        } else {
            Toast.makeText(requireContext(), "Please, enter the task!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(taskName: String): Boolean {
        return !(TextUtils.isEmpty(taskName))
    }

    @Deprecated("Deprecated in Java", ReplaceWith("inflater.inflate()"))
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("super.onOptionsItemSelected(item)",
        "androidx.fragment.app.Fragment"))
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteTask()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteTask() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mTaskViewModel.deleteTask(args.currentTask)
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
            Toast.makeText(requireContext(),
                "Successfully removed: ${args.currentTask.task_text}",
            Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete ${args.currentTask.task_text}?")
        builder.setMessage("Are you sure you want to delete ${args.currentTask.task_text}?")
        builder.create().show()
    }
}