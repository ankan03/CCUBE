package com.studgenie.app.data.model

data class SendUserDetails(
    val name: String,
    val email: String,
    val password: String,
    val auth_token: String
)
//"auth_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJudW1iZXIiOiI3OTkyOTgyMDM4IiwiaWF0IjoxNjAzODA5MTI2fQ.XyVd0uiTLCDFRHu3dwu4SsyUEes35Vzxb-QjMiROYV0",
//"name":"Ankan",
//"password":"12345",
//"email":null