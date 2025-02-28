package fr.isen.vincent.isensmartcompanion.api

import fr.isen.vincent.isensmartcompanion.models.EventModel
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("events.json")
    fun getEvents() : Call<List<EventModel>>
}