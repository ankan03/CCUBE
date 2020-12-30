package com.studgenie.app.data.remote.response

data class SigninApiResponse(
    val authToken: String,
    val data: List<UserData>
) {
    data class UserData(
        val number: String,
        val first_name: String,
        val last_name: String,
        val dob: String,
        val picture_url: String,
        val account_status: String,
        val max_devices: Int,
        val user_name: String,
        val student_id: Int,
        val institute_id: String,
        val email: String
    )
}