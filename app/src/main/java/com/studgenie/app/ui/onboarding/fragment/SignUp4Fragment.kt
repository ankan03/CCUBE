package com.studgenie.app.ui.onboarding.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.studgenie.app.R
import com.studgenie.app.ui.main.activity.HomeActivity

@Suppress("DEPRECATION")
class SignUp4Fragment : Fragment(){

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_up_4, container, false)
        val beginLearning = rootView.findViewById<Button>(R.id.verify_proceed_button)
        val backArrow = rootView.findViewById<Button>(R.id.back_arrow_button)

        beginLearning.setOnClickListener {
            val i = Intent(activity, HomeActivity::class.java)
            startActivity(i)
            (activity as Activity?)!!.overridePendingTransition(0, 0)
            activity?.finish()
        }
        backArrow.setOnClickListener {
            val signUp3Fragment = SignUp3Fragment()
            fragmentManager!!.beginTransaction().replace(R.id.signup_fragment_container,signUp3Fragment).commit()
        }
        return rootView

    }
}