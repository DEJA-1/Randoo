package com.example.randoo.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.randoo.R
import com.example.randoo.viewmodel.TaskViewModel
import com.example.randoo.databinding.FragmentAddBinding
import com.example.randoo.model.Task

class AddFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mTaskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTaskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        binding.newTaskButton.setOnClickListener { insertToDatabase() }
    }

    private fun insertToDatabase() {
        val taskName = binding.newTaskText.text.toString()

        if (inputCheck(taskName)){
            //creating task object
            val task = Task(0, false, taskName)

            //adding data to database
            mTaskViewModel.addTask(task)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()

            //navigate back to main fragment
            findNavController().navigate(R.id.action_addFragment2_to_mainFragment)
        } else {
            Toast.makeText(requireContext(), "Please, enter the task!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(taskName: String): Boolean {
        return !(TextUtils.isEmpty(taskName))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}