package com.studgenie.app.ui.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.studgenie.app.R
import com.studgenie.app.ui.main.fragment.CourseFragment
import com.studgenie.app.ui.main.fragment.HomeFragment
import com.studgenie.app.ui.main.fragment.ProfileFragment
import com.studgenie.app.ui.main.fragment.VideoFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        val videoFragment = VideoFragment()
        val courseFragment = CourseFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_fragment -> makeCurrentFragment(homeFragment)
                R.id.video_fragment -> makeCurrentFragment(videoFragment)
                R.id.course_fragment -> makeCurrentFragment(courseFragment)
                R.id.profile_fragment -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.home_fragment_container, fragment).commit()
        }
}