package com.loc.sampahuser

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.loc.sampahuser.petugas.MapsActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AdapterAtasi(val mCtx: Context, val layoutResId: Int, val list: List<Lapors> )
    : ArrayAdapter<Lapors>(mCtx,layoutResId,list){
    val mAuth = FirebaseAuth.getInstance()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val tvpengguna = view.findViewById<TextView>(R.id.tvpengguna)
        val tvlokasi = view.findViewById<TextView>(R.id.tvlokasi)
        val tvketerangan = view.findViewById<TextView>(R.id.tvketerangan)
        val tvatasi = view.findViewById<TextView>(R.id.tvatasi)
        val btndetail = view.findViewById<TextView>(R.id.btndetail)
        val btnmaps = view.findViewById<TextView>(R.id.btnmaps)
        val btnhapus = view.findViewById<TextView>(R.id.btnhapus)
        val tvTanggalLapor = view.findViewById<TextView>(R.id.tvTanggalLapor)
        val tvTanggalAtasi = view.findViewById<TextView>(R.id.tvTanggalAtasi)

        val lapor = list[position]

        tvpengguna.text = "Nama : "+lapor.nama
        tvlokasi.text = "Lokasi : " +lapor.lokasi
        tvketerangan.text = "Keterangan : "+lapor.keterangan
        tvTanggalLapor.text = "Tgl Lapor : "+lapor.tanggallapor

        if(lapor.atasi != ""){
            tvatasi.text = lapor.atasi
            tvTanggalAtasi.visibility = View.VISIBLE
            tvTanggalAtasi.text = " Tgl : "+lapor.tanggalatasi
            tvTanggalAtasi.setTextColor(Color.parseColor("#ffbb33"))
            tvatasi.setTextColor(Color.parseColor("#ffbb33"))
            btnhapus.visibility = View.VISIBLE
        }else{
            tvatasi.text = "Belum di atasi"
            tvTanggalAtasi.visibility = View.GONE
            tvatasi.setTextColor(Color.parseColor("#fc0303"))
            btnhapus.visibility = View.GONE
        }

        btnmaps.setOnClickListener {
            val intent = Intent(context, MapsActivity::class.java)
            intent.putExtra("latitude", lapor.latitude)
            intent.putExtra("langitude", lapor.langitude)
            intent.putExtra("lokasi", lapor.lokasi)
            context.startActivity(intent)
        }

        btndetail.setOnClickListener {
            detailShow(lapor)
        }

        btnhapus.setOnClickListener {
            Deleteinfo(lapor)
        }

        return view

    }

    private fun Deleteinfo(lapor: Lapors) {
        val progressDialog = ProgressDialog(context,
            R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting…")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("LAPOR")
        mydatabase.child(lapor.uid).removeValue()
        Toast.makeText(mCtx,"Deleted!!", Toast.LENGTH_SHORT).show()
        progressDialog.dismiss()
    }

    private fun detailShow(lapor: Lapors){
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Detail")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.showdetail, null)

        val tvpelapor = view.findViewById<EditText>(R.id.pelapor)
        val tvketerangan = view.findViewById<EditText>(R.id.keterangan)
        val tvlokasi = view.findViewById<EditText>(R.id.lokasi)
        val tvlatitude = view.findViewById<EditText>(R.id.lat)
        val tvlangitude = view.findViewById<EditText>(R.id.lang)
        val tvstatus = view.findViewById<EditText>(R.id.status)

        tvpelapor.setText(lapor.nama)
        tvketerangan.setText(lapor.keterangan)
        tvlokasi.setText(lapor.lokasi)
        tvlatitude.setText(""+lapor.latitude)
        tvlangitude.setText(""+lapor.langitude)
        tvlangitude.setText(""+lapor.langitude)
        if(lapor.atasi != ""){
            tvstatus.setText(lapor.atasi)
            tvstatus.setTextColor(Color.parseColor("#ffbb33"))
        }else{
            tvstatus.setText("Belum di atasi")
            tvstatus.setTextColor(Color.parseColor("#fc0303"))
        }

        builder.setView(view)

        builder.setPositiveButton("SELESAI") { dialog, which ->

            val dbLapor = FirebaseDatabase.getInstance().getReference("LAPOR").child(lapor.uid)


            val status = "Sudah di atasi".trim()

            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val date = Date()
            val tanggalatasi = dateFormat.format(date).toString()

            val firebaseUser = mAuth!!.currentUser

//            val lapor = Lapors(lapor.id_user, lapor.nama, lapor.keterangan,lapor.lokasi,lapor.latitude,lapor.langitude, status, lapor.tanggallapor, tanggalatasi)

            val map = HashMap<String, Any>()
            map["atasi"] = status
            map["tanggalatasi"] = tanggalatasi
            map["petugas"] = firebaseUser!!.email.toString()
            dbLapor.updateChildren(map).addOnCompleteListener {
                Toast.makeText(mCtx,"Laporan sudah di atasi", Toast.LENGTH_SHORT).show()
            }


        }

        builder.setNegativeButton("Batal") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }
}