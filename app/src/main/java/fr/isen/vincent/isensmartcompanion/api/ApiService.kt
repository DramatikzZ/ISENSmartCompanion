package fr.isen.vincent.isensmartcompanion.api

import fr.isen.vincent.isensmartcompanion.models.EventModel
import fr.isen.vincent.isensmartcompanion.utils.constants.Constants
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET(Constants.EVENTS_ENDPOINT)
    fun getEvents() : Call<List<EventModel>>
}