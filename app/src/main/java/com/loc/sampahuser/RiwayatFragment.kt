package com.loc.sampahuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_riwayat.*
import kotlinx.android.synthetic.main.fragment_riwayat.view.*

class RiwayatFragment : Fragment() {

    private lateinit var vv: View

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Lapors>
    lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        vv = inflater.inflate(R.layout.fragment_riwayat, container, false)


         ref = FirebaseDatabase.getInstance().getReference("LAPOR")
         list = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    list.clear()
                    for (h in p0.children){
                        val lapor = h.getValue(Lapors::class.java)
                        list.add(lapor!!)
                    }
                    val adapter = Adapter(context!!, R.layout.item_lapor,list)
                    listView.adapter = adapter
                }
            }
        })


        return vv
    }
    companion object {
        fun newInstance() : RiwayatFragment = RiwayatFragment()
    }

}