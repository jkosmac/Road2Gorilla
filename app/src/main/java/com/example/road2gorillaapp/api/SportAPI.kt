package com.example.road2gorillaapp.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class SportAPI {
    companion object {
        suspend fun getDataFromSportAPI(Url: String): Any? {
            return withContext(Dispatchers.IO) {
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url(Url)
                    .get()
                    .addHeader("x-rapidapi-key", "6ceed6b026mshfc4a0f9f36ef7e0p14cf2bjsncf5a60c39f12")
                    .addHeader("x-rapidapi-host", "sportapi7.p.rapidapi.com")
                    .build()


                val response = client.newCall(request).execute()
                //var list = mutableListOf<String>();

                val jsonData = response.body?.string()
                var jsonObject = JSONObject(jsonData)
                val arrayOfData = jsonObject?.getJSONArray("events")

                arrayOfData
            }
        }

    }
}