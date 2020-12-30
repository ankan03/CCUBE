package com.studgenie.app.data.remote.request

import com.studgenie.app.data.model.CountryItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiCountryService {
    @GET("/constants/country_code")
    fun fetchAllCountries(): Call<List<CountryItem>>
}