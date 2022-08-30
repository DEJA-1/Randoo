package com.example.randoo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.randoo.R
import com.example.randoo.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    //viewBinding
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting up a click listener on main_image_button
        binding.mainImageButton.setOnClickListener {onButtonClicked()}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //setting up the navigation
    private fun onButtonClicked(){
        findNavController().navigate(R.id.action_startFragment_to_mainFragment)
    }

}