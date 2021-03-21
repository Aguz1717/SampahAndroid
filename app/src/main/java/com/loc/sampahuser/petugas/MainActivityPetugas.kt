package com.loc.sampahuser.petugas

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.loc.sampahuser.*
import com.loc.sampahuser.R


class MainActivityPetugas : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Lapors>
    lateinit var listView: ListView
    val mAuth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_petugas)

        val firebaseUser = mAuth!!.currentUser
        val uid = firebaseUser!!.uid

        ref = FirebaseDatabase.getInstance().getReference("LAPOR")
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    list.clear()
                    for (h in p0.children){
                        val lapor = h.getValue(Lapors::class.java)
                        if(lapor!!.atasi.equals("") && lapor!!.tanggalatasi.equals("")) {
                            list.add(lapor!!)
                        }
                    }
                    val adapter = Adapter(this@MainActivityPetugas,R.layout.item_lapor, list)
                    listView.adapter = adapter

                    listView.setOnItemClickListener{ adapter, view, position, id ->
                        val itemAtPos = adapter.getItemAtPosition(position)
                        val itemIdAtPos = adapter.getItemIdAtPosition(position)
                        Toast.makeText(this@MainActivityPetugas, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
                    }

//                    listView.onItemClickListener =
//                        AdapterView.OnItemClickListener { parent, view, position, id ->
//                            val intent = Intent(this@MainActivityPetugas, MapsActivity::class.java)
////                            intent.putExtra("latitude", )
//                            startActivity(intent)
//
//                        }
                }
            }
        })
    }

//    override fun onBackPressed() {
//        return onBackPressed()
//    }

    private fun logout() {
        mAuth.signOut()
        val intent = Intent(this, LoginScreen::class.java)
        startActivityForResult(intent,0)
    }

}