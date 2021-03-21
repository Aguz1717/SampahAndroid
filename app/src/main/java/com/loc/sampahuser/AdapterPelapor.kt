package com.loc.sampahuser

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.loc.sampahuser.petugas.MapsActivity

class AdapterPelapor(val mCtx: Context, val layoutResId: Int, val list: List<Lapors> )
    : ArrayAdapter<Lapors>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val tvpengguna = view.findViewById<TextView>(R.id.tvpengguna)
        val tvlokasi = view.findViewById<TextView>(R.id.tvlokasi)
        val tvketerangan = view.findViewById<TextView>(R.id.tvketerangan)
        val tvatasi = view.findViewById<TextView>(R.id.tvatasi)
        val tvTanggalLapor = view.findViewById<TextView>(R.id.tvTanggalLapor)
        val tvTanggalAtasi = view.findViewById<TextView>(R.id.tvTanggalAtasi)
        val btnhapus = view.findViewById<TextView>(R.id.btnhapus)
        val tvPetugas = view.findViewById<TextView>(R.id.tvPetugas)

        val lapor = list[position]

        tvpengguna.text = "Nama : "+lapor.nama
        tvlokasi.text = "Lokasi : " +lapor.lokasi
        tvketerangan.text = "Keterangan : "+lapor.keterangan
        tvTanggalLapor.text = "Tgl Lapor : "+lapor.tanggallapor

        if(lapor.atasi != ""){
            tvatasi.text = lapor.atasi
            tvTanggalAtasi.visibility = VISIBLE
            tvPetugas.visibility = VISIBLE
            tvTanggalAtasi.text = " Tgl : "+lapor.tanggalatasi
            tvPetugas.text = "Petugas : "+lapor.petugas
            tvTanggalAtasi.setTextColor(Color.parseColor("#ffbb33"))
            tvatasi.setTextColor(Color.parseColor("#ffbb33"))
            btnhapus.visibility = GONE
        }else{
            tvatasi.text = "Belum di atasi"
            tvTanggalAtasi.visibility = GONE
            tvPetugas.visibility = GONE
            tvatasi.setTextColor(Color.parseColor("#fc0303"))
            btnhapus.visibility = VISIBLE
        }

        btnhapus.setOnClickListener {
            Deleteinfo(lapor)
        }

        return view

    }

    private fun Deleteinfo(lapor: Lapors) {
        val mydatabase = FirebaseDatabase.getInstance().getReference("LAPOR")
        mydatabase.child(lapor.uid).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
    }

}