package com.loc.sampahuser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.rootFragment, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        if(savedInstanceState==null && auth!!.currentUser != null){
            menu_bottom.setItemSelected(R.id.home, true)
            val fragment = HomeFragment.newInstance()
            addFragment(fragment)
        }

        menu_bottom.setOnItemSelectedListener { id ->
            when (id) {
                R.id.home -> {
                    val fragment = HomeFragment.newInstance()
                    addFragment(fragment)
                    return@setOnItemSelectedListener
                }
//                R.id.riwayat -> {
//                    val fragment = RiwayatFragment.newInstance()
//                    addFragment(fragment)
//                    return@setOnItemSelectedListener
//                }

//                R.id.account -> {
//                    val fragment = AccountFragment.newInstance()
//                    addFragment(fragment)
//                    return@setOnItemSelectedListener
//                }
            }
        }


//        logout.setOnClickListener {
//            mGoogleSignInClient.signOut().addOnCompleteListener {
//                val intent= Intent(this, LoginScreen::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }

    }


    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }


}