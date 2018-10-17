package com.iamwee.tamboon.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.iamwee.tamboon.R
import com.iamwee.tamboon.data.Charities
import com.iamwee.tamboon.http.HttpManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        HttpManager.tamboonService.getCharities().enqueue(object : Callback<List<Charities>> {
            override fun onFailure(call: Call<List<Charities>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Charities>>, response: Response<List<Charities>>) {

            }
        })
    }
}
