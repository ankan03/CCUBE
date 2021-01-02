package com.studgenie.app.ui.onboarding.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.studgenie.app.R
import com.studgenie.app.data.model.SendUserDetails
import com.studgenie.app.data.remote.response.UserDetailsApiResponse
import com.studgenie.app.data.local.tokenDatabase.AuthTokenViewModel
import com.studgenie.app.ui.main.activity.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.studgenie.app.data.local.userDetailsDatabase.UserDataModel
import com.studgenie.app.data.local.userDetailsDatabase.UserViewModel
import com.studgenie.app.data.remote.request.CreateDetailsApi
import com.studgenie.app.util.Config
import kotlinx.android.synthetic.main.fragment_sign_up_3.view.*
import java.text.SimpleDateFormat


class SignUp3Fragment : Fragment() {
    private lateinit var authTokenViewModel: AuthTokenViewModel
    private lateinit var userViewModel: UserViewModel
    var isTokenEmpty = 1
    var isUserEmpty: Int = 1
    var storeAuthTokenId = 1
    var storeUserId = 1

    lateinit var enterNameString: String
    lateinit var enterEmailString: String
    lateinit var enterDobString: String
//    lateinit var enterConfirmPasswordString: String
    lateinit var authToken: String

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authTokenViewModel = ViewModelProvider(requireActivity()).get(AuthTokenViewModel::class.java)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_sign_up_3, container, false)
        val submitButton = rootView.findViewById<Button>(R.id.submit_button)
        //val skipButton = rootView.findViewById<Button>(R.id.skip_button)
        val toastMessage = rootView.findViewById<TextView>(R.id.toast_message_3rd_signup_fragment)
        toastMessage.visibility = View.INVISIBLE
        val calendar=rootView.findViewById<ImageView>(R.id.calendar)
        val DOB=rootView.findViewById<EditText>(R.id.enter_dob_edit_text)

        enterNameString = rootView.enter_name_edit_text.text.toString().trim()
        enterEmailString = rootView.enter_email_edit_text.text.toString().trim()
        enterDobString = rootView.enter_dob_edit_text.text.toString().trim()
//        enterConfirmPasswordString = rootView.interested_course_edit_Text.text.toString().trim()

//        //skipButton.setOnClickListener {
//            val i = Intent(activity, HomeActivity::class.java)
//            startActivity(i)
//            (activity as Activity?)!!.overridePendingTransition(0, 0)
//
//            activity?.finish()
//        }

//        spinner for interested courses
        val spinner: Spinner = rootView.spinner_courses
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.interested_courses,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
val dateFormat=SimpleDateFormat("dd MMMM YYYY")
        calendar.setOnClickListener {
            val getDate: Calendar = Calendar.getInstance()
            val datePicker= activity?.let { it1 -> DatePickerDialog(it1,android.R.style.Theme_Holo_Light_Dialog_MinWidth,DatePickerDialog.OnDateSetListener{ datePicker, i, i2, i3 ->
                val SelectDate=Calendar.getInstance()
                SelectDate.set(Calendar.YEAR,i)
                SelectDate.set(Calendar.MONTH,i2)
                SelectDate.set(Calendar.DAY_OF_MONTH,i3)
                val date=dateFormat.format(SelectDate.time)
               DOB.setText(date)


            },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH)) }
            if (datePicker != null) {
                datePicker.show()
            }
        }



        authTokenViewModel.readAllData?.observe(viewLifecycleOwner, Observer { auth ->
            if (auth.isEmpty()) {
                isTokenEmpty = 1
                Log.d("CoroutineToken", "List is empty")
            } else {
                isTokenEmpty = 0
                Log.d("CoroutineToken", auth[0].id.toString() + auth[0].authToken)
                storeAuthTokenId = auth[0].id
                authToken = auth[0].authToken
            }
        })

        userViewModel.readAllDataModel?.observe(viewLifecycleOwner, Observer { user ->
            if (user.isEmpty()) {
                isUserEmpty = 1
                Log.d("CoroutineUserData", "List is empty")
            } else {
                isUserEmpty = 0
                storeUserId = user[0].id
                Log.d("CoroutineUserData", user[0].id.toString() + user[0].number)
            }
        })




        submitButton.setOnClickListener {
            enterNameString = rootView.enter_name_edit_text.text.toString().trim()
            enterEmailString = rootView.enter_email_edit_text.text.toString().trim()
            enterDobString = rootView.enter_dob_edit_text.text.toString().trim()
//            enterConfirmPasswordString = rootView.interested_course_edit_Text.text.toString().trim()

            if (!enterNameString.isEmpty() && !enterEmailString.isEmpty() ) {

                if (enterEmailString.isValidEmail()) {

                            val retrofit = Retrofit.Builder()
//                .baseUrl("http://192.168.43.217:3000")
//                .baseUrl("http://sg-backend-dev.ap-south-1.elasticbeanstalk.com")
                                .baseUrl(Config.baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                            val createDetailsApi = retrofit.create(CreateDetailsApi::class.java)
                            if (authToken != null) {
                                val sendDetails = SendUserDetails(
                                    enterNameString,
                                    enterEmailString,
                                    enterDobString,
                                    authToken
                                )
//                        val sendDetails = SendUserDetails("aaaaaa","aa@bb","12345","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJudW1iZXIiOiI3OTkyOTgyMDM4IiwiaWF0IjoxNjAzODA5MTI2fQ.XyVd0uiTLCDFRHu3dwu4SsyUEes35Vzxb-QjMiROYV0")
                                createDetailsApi.userDetails(sendDetails).enqueue(object :
                                    Callback<List<UserDetailsApiResponse>> {
                                    override fun onResponse(
                                        call: Call<List<UserDetailsApiResponse>>,
                                        response: Response<List<UserDetailsApiResponse>>
                                    ) {
                                        if (response.isSuccessful) {
                                            Log.d(
                                                "RetrofitUserDetails",
                                                "OnResponse: ${response.body()?.get(0)?.number} \n"
                                                        + "Response Code: ${response.code()}\n"
                                            )
                                            val mUserData = UserDataModel(
                                                response.body()?.get(0)?.number.toString(),
                                                response.body()?.get(0)?.first_name.toString(),
                                                response.body()?.get(0)?.last_name.toString(),
                                                response.body()?.get(0)?.dob.toString(),
                                                response.body()?.get(0)?.picture_url.toString(),
                                                response.body()?.get(0)?.account_status.toString(),
                                                response.body()?.get(0)?.max_devices!!.toInt(),
                                                response.body()?.get(0)?.user_name.toString(),
                                                response.body()?.get(0)?.student_id!!.toInt(),
                                                response.body()?.get(0)?.institute_id.toString(),
                                                response.body()?.get(0)?.email.toString()
                                            )

                                            if (isUserEmpty == 1) {
                                                userViewModel.addUserData(mUserData)
                                                Log.d("CoroutineUserData", "Successfully added!")

//                                                val i = Intent(activity, HomeActivity::class.java)
//                                                startActivity(i)
//                                                (activity as Activity?)!!.overridePendingTransition(
//                                                    0,
//                                                    0
//                                                )
                                                val signUp4Fragment = SignUp4Fragment()
//                                                val args = Bundle()
//                                                args.putString("phNo", phoneNumberEditText.text.toString())
//                                                signUp2Fragment.arguments = args
                                                fragmentManager!!.beginTransaction()
                                                    .replace(R.id.signup_fragment_container, signUp4Fragment).commit()


                                                userViewModel.readAllDataModel?.observe(
                                                    viewLifecycleOwner,
                                                    Observer { user ->
                                                        if (!user.isEmpty()) {
                                                            Log.d(
                                                                "CoroutineUserData",
                                                                user[0].id.toString() + user[0].number.toString() + user[0].firstName.toString() + user[0].lastName.toString() + user[0].dob.toString() + user[0].pictureUrl.toString() + user[0].accountStatus.toString() + user[0].maxDevices.toString() + user[0].userName.toString() + user[0].studentId.toString() + user[0].instituteId.toString() + user[0].email.toString()
                                                            )
//                                                    Toast.makeText(requireContext(),user[0].id.toString() + user[0].number.toString() + user[0].firstName.toString() + user[0].lastName.toString() + user[0].dob.toString() + user[0].pictureUrl.toString() + user[0].accountStatus.toString() + user[0].maxDevices.toString() + user[0].userName.toString() + user[0].studentId.toString() + user[0].instituteId.toString() + user[0].email.toString() , Toast.LENGTH_SHORT).show()
                                                        } else {
                                                            Log.d(
                                                                "CoroutineUserData",
                                                                "User Data Not added"
                                                            )
                                                        }
                                                    })
                                                activity?.finish()
                                            } else {
                                                userViewModel.updateUserData(
                                                    response.body()?.get(0)?.number.toString(),
                                                    response.body()?.get(0)?.user_name.toString(),
                                                    response.body()?.get(0)?.email.toString(),
                                                    response.body()?.get(0)?.dob.toString(),
                                                    storeUserId
                                                )
                                                Log.d("CoroutineUserData", "Successfully updated!")
//
//                                                val i = Intent(activity, HomeActivity::class.java)
//                                                startActivity(i)
//                                                (activity as Activity?)!!.overridePendingTransition(
//                                                    0,
//                                                    0
//                                                )
                                                val signUp4Fragment = SignUp4Fragment()
//                                                val args = Bundle()
//                                                args.putString("phNo", phoneNumberEditText.text.toString())
//                                                signUp2Fragment.arguments = args
                                                fragmentManager!!.beginTransaction()
                                                    .replace(R.id.signup_fragment_container, signUp4Fragment).commit()


                                                userViewModel.readAllDataModel?.observe(
                                                    viewLifecycleOwner,
                                                    Observer { user ->
                                                        Log.d(
                                                            "CoroutineUserData",
                                                            user[0].id.toString() + user[0].number.toString() + user[0].firstName.toString() + user[0].lastName.toString() + user[0].dob.toString() + user[0].pictureUrl.toString() + user[0].accountStatus.toString() + user[0].maxDevices.toString() + user[0].userName.toString() + user[0].studentId.toString() + user[0].instituteId.toString() + user[0].email.toString()
                                                        )
//                                                Toast.makeText(requireContext(),user[0].id.toString() + user[0].number.toString() + user[0].firstName.toString() + user[0].lastName.toString() + user[0].dob.toString() + user[0].pictureUrl.toString() + user[0].accountStatus.toString() + user[0].maxDevices.toString() + user[0].userName.toString() + user[0].studentId.toString() + user[0].instituteId.toString() + user[0].email.toString() , Toast.LENGTH_SHORT).show()
                                                    })
                                                activity?.finish()
                                            }
                                        } else {
                                            toastMessage.visibility = View.VISIBLE
                                            toastMessage.text = "Server Error"
                                            toastMessage.setBackgroundResource(R.color.toast_background)
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<List<UserDetailsApiResponse>>,
                                        t: Throwable
                                    ) {
                                        Log.d(
                                            "RetrofitUserDetails",
                                            "OnFailure \n" + "Response Code:" + t.message + "\n" + t.cause + "\n" + t.printStackTrace()
                                                .toString()
                                        )
                                        toastMessage.visibility = View.VISIBLE
                                        toastMessage.text = "Try after some time "
                                        toastMessage.setBackgroundResource(R.color.toast_background)
                                    }
                                })
                            }
                            else {
                                Log.d("CoroutineToken", "Auth token is empty")
                            }

//                        else {
//                            toastMessage.visibility = View.VISIBLE
//                            toastMessage.text = "Password did not match"
//                            toastMessage.setBackgroundResource(R.color.toast_background)
//                        }

//                    } else {
//                        toastMessage.visibility = View.VISIBLE
//                        toastMessage.text = "Minimum password length should be 8"
//                        toastMessage.setBackgroundResource(R.color.toast_background)
//                    }
                } else {
                    toastMessage.visibility = View.VISIBLE
                    toastMessage.text = "Enter a valid Email"
                    toastMessage.setBackgroundResource(R.color.toast_background)
                }
            } else {
                toastMessage.visibility = View.VISIBLE
                toastMessage.text = "Please enter all details"
                toastMessage.setBackgroundResource(R.color.toast_background)
            }
        }
        return rootView
    }

    fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}