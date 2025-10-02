package com.vignesh.pcl.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import com.vignesh.pcl.R
import com.vignesh.pcl.adapter.HomeTabAdapter
import com.vignesh.pcl.databinding.FragmentHomeContainerBinding

class HomeContainerFragment : Fragment() {
    lateinit var binding: FragmentHomeContainerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fab = requireActivity().findViewById<ExtendedFloatingActionButton>(R.id.fab_add_new)
        fab?.hide()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val fab = requireActivity().findViewById<ExtendedFloatingActionButton>(R.id.fab_add_new)
        fab?.show()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeContainerBinding.inflate(inflater, container, false)
        val tabTitles = listOf(
            getString(R.string.title_home),
            getString(R.string.title_teams),
            getString(R.string.title_matches),
            getString(R.string.title_points),
            getString(R.string.title_statistics)
        )
        val tournamentId = arguments?.getLong("tournamentId")
        val position = 0

        val adapter = tournamentId?.let { HomeTabAdapter(requireActivity(), it) }
       adapter?.createFragment(position)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabHome, binding.viewPager) { tab, pos ->
            tab.text = tabTitles[pos]
        }.attach()
        return binding.root
    }
}