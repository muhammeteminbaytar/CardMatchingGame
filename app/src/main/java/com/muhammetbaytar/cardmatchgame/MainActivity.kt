package com.muhammetbaytar.cardmatchgame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickControl()
        sharedPrefControl()

    }
    fun clickControl(){
        main_btn_level1.setOnClickListener(){
            val intent=Intent(this,level1Act::class.java)
            startActivity(intent)
        }
        main_btn_level2.setOnClickListener(){
            val intent=Intent(this,level2Act::class.java)
            startActivity(intent)
        }
        main_btn_level3.setOnClickListener(){
            val intent=Intent(this,level3Act::class.java)
            startActivity(intent)
        }
    }
    fun sharedPrefControl(){

        val sharedPreferences=this.getSharedPreferences("com.muhammetbaytar.cardmatchgame", Context.MODE_PRIVATE)
           txt_score1.text="Best Score : "+ sharedPreferences.getInt("h1Score",0).toString()

        val sharedPreferences2=this.getSharedPreferences("com.muhammetbaytar.cardmatchgame", Context.MODE_PRIVATE)
        txt_score2.text="Best Score : "+ sharedPreferences.getInt("h2Score",0).toString()

        val sharedPreferences3=this.getSharedPreferences("com.muhammetbaytar.cardmatchgame", Context.MODE_PRIVATE)
        txt_score3.text="Best Score : "+ sharedPreferences.getInt("h3Score",0).toString()
    }

}