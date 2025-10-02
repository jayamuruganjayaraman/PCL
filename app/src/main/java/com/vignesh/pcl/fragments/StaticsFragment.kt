package com.vignesh.pcl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vignesh.pcl.R
import com.vignesh.pcl.databinding.FragmentStaticsBinding

class StaticsFragment : Fragment() {
    lateinit var binding: FragmentStaticsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStaticsBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = StaticsFragment()
    }
}