package com.loc.sampahuser.petugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loc.sampahuser.LoginScreen
import com.loc.sampahuser.R
import kotlinx.android.synthetic.main.activity_menu_petugas.*

class MenuPetugas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_petugas)

        belumatasi.setOnClickListener {
            val intent = Intent(this, MainActivityPetugas::class.java)
            startActivity(intent)
        }

        atasi.setOnClickListener {
            val intent = Intent(this, DataSudahAtasi::class.java)
            startActivity(intent)
        }
    }

        override fun onBackPressed() {
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
        finish()
    }
}