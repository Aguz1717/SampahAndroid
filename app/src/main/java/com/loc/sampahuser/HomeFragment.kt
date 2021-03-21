package com.loc.sampahuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var vv: View
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    var mAuth = FirebaseAuth.getInstance()

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        vv = inflater.inflate(R.layout.fragment_home, container, false)


        vv.addSampah.setOnClickListener {
            val intent = Intent(context, LaporSampah::class.java)
            startActivity(intent)
        }

        vv.showdata.setOnClickListener {
            val intent = Intent(context, Show::class.java)
            startActivity(intent)
        }

        vv.btnlogout.setOnClickListener{
            logout()
        }

        return vv
    }

    private fun logout() {
        mAuth.signOut()
        val intent = Intent(context, LoginScreen::class.java)
        startActivityForResult(intent,0)
//        mGoogleSignInClient.signOut().addOnCompleteListener {
//            val intent= Intent(context, LoginScreen::class.java)
//            startActivity(intent)
////                finish()
//        }
    }

    companion object {
        fun newInstance() : HomeFragment = HomeFragment()
    }

}