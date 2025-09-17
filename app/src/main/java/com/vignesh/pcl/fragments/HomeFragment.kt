package com.vignesh.pcl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.vignesh.pcl.R
import com.vignesh.pcl.databinding.FragmentHomeBinding
import com.vignesh.pcl.viewModel.TournamentViewModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val args: HomeFragment by navArgs()
    private lateinit var viewModel: TournamentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}