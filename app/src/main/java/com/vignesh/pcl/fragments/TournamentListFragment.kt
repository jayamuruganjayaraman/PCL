package com.vignesh.pcl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vignesh.pcl.R
import com.vignesh.pcl.databinding.FragmentTournamentListBinding
import com.vignesh.pcl.viewModel.TournamentViewModel
import com.vignesh.pcl.viewModel.TournamentViewModelFactory
import com.vignesh.pcl.adapter.TournamentBannerAdapter // create this adapter for ViewPager2

class TournamentListFragment : Fragment() {
    private lateinit var binding: FragmentTournamentListBinding
    private lateinit var adapter: TournamentBannerAdapter
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTournamentListBinding.inflate(inflater, container, false)
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val tournamentViewModelFactory =
            TournamentViewModelFactory(requireActivity().application)
        val tournamentViewModel = ViewModelProvider(
            requireActivity(),
            tournamentViewModelFactory
        )[TournamentViewModel::class.java]

        adapter = TournamentBannerAdapter { tournament ->
            val bundle = Bundle().apply {
                putLong("tournamentId", tournament.tournamentId)
            }
           navController.navigate(R.id.action_welcomeFragment_to_homeContainerFragment,bundle)
        }

        binding.viewPagerTournament.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPagerTournament)

        // Observe tournaments
        tournamentViewModel.allTournaments.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.viewPagerTournament.visibility = View.GONE
                binding.dotsIndicator.visibility = View.GONE
                binding.tvNoTournament.visibility = View.VISIBLE
            } else {
                binding.viewPagerTournament.visibility = View.VISIBLE
                binding.dotsIndicator.visibility = View.VISIBLE
                binding.tvNoTournament.visibility = View.GONE
                adapter.submitList(list)
            }
        }

        return binding.root
    }

}
