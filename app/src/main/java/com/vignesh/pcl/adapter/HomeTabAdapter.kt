package com.vignesh.pcl.adapter


import android.app.Activity
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
    fm: FragmentActivity,
    private val position: Int
) : FragmentStateAdapter(fm) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.title_home,
            R.string.title_teams,
            R.string.title_matches,
            R.string.title_points,
            R.string.title_statistics
        )
    }

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> TeamsFragment.newInstance()
            2 -> MatchesFragment.newInstance()
            3 -> PointsFragment.newInstance()
            4 -> StaticsFragment.newInstance()
            else -> HomeFragment()
        }
    }
}

