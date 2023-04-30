package com.example.itsum

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_join.*


class NewJoin : AppCompatActivity() {
    private lateinit var binding: NewJoin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_join)
        createClubBtn.setOnClickListener{
            Log.d("tag", password.toString())

        }

    }
}