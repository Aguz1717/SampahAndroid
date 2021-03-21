package com.loc.sampahuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Show : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Lapors>
    lateinit var listView: ListView
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        val firebaseUser = mAuth!!.currentUser

        ref = FirebaseDatabase.getInstance().getReference("LAPOR")
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    for (h in p0.children){
                        val lapor = h.getValue(Lapors::class.java)
                        if(lapor!!.iduser == firebaseUser!!.uid) {
                            list.add(lapor!!)
                        }
                    }
                    val adapter = AdapterPelapor(applicationContext,R.layout.item_pelapor,list)
                    listView.adapter = adapter
                }
            }
        })
    }
}