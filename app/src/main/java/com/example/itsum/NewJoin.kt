package com.example.itsum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.itsum.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_new_join.*


class NewJoin : AppCompatActivity() {
    private lateinit var binding: NewJoin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_join)
        button.setOnClickListener{
            Log.d("tag", password.toString())

        }

    }
}