package com.vignesh.pcl.adapter


import android.app.Activity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vignesh.pcl.R
import com.vignesh.pcl.fragments.HomeFragment
import com.vignesh.pcl.fragments.MatchesFragment
import com.vignesh.pcl.fragments.PointsFragment
import com.vignesh.pcl.fragments.StaticsFragment
import com.vignesh.pcl.fragments.TeamsFragment
class HomeTabAdapter(
    fragmentActivity: FragmentActivity,
    private val tournamentId: Long
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> HomeFragment()
            1 -> TeamsFragment()
            2 -> MatchesFragment()
            3 -> PointsFragment()
            4 -> StaticsFragment()
            else -> HomeFragment()
        }
        fragment.arguments = Bundle().apply {
            putLong("tournamentId", tournamentId)
        }
        return fragment
    }
}


