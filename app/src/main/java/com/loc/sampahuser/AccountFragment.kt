package com.loc.sampahuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {
    private lateinit var vv: View

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        vv = inflater.inflate(R.layout.fragment_account, container, false)


        vv.logout.setOnClickListener{
            logout()
        }

        return vv
    }


    private fun logout() {
//        auth.signOut()
//        val intent = Intent(context, LoginScreen::class.java)
//        startActivity(intent)
        mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(context, LoginScreen::class.java)
                startActivity(intent)
//                finish()
            }
    }
    companion object {
        fun newInstance() : AccountFragment = AccountFragment()
    }

}