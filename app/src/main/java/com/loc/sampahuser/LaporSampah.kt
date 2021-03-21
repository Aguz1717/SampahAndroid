package com.loc.sampahuser

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_lapor_sampah.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class LaporSampah : AppCompatActivity() {


    private var mFusedLocationClient: FusedLocationProviderClient? = null


    protected var mLastLocation: Location? = null

    private var mLatitudeText: TextView? = null
    private var mLongitudeText: TextView? = null

    val mAuth = FirebaseAuth.getInstance()
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null



    lateinit var ref : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_lapor_sampah)


        mLatitudeText = findViewById<View>(R.id.lat) as TextView
        mLongitudeText = findViewById<View>(R.id.lang) as TextView

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLokasi.setOnClickListener {
            if (!checkPermissions()) {
                requestPermissions()
            } else {
                getLastLocation()
            }
        }


        btn_add.setOnClickListener {
            savedata()
        }

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        ref = FirebaseDatabase.getInstance().getReference("LAPOR")

    }



    private fun savedata() {
        val firebaseUser = mAuth!!.currentUser
        val iduser = firebaseUser!!.uid
        val nama = firebaseUser.displayName.toString()
        val keterangan = etketerangan.text.toString()
        val lokasi = lokasi.text.toString()
        val lat = lat.text.toString().toDouble()
        val lang = lang.text.toString().toDouble()
        val atasi = ""

        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = Date()
        val tanggallapor = dateFormat.format(date).toString()

        val laporId = ref.push().key.toString()
        val lapor = Lapors(laporId,iduser,nama,keterangan,lokasi,lat,lang,atasi,tanggallapor,"","")


        if (keterangan.isNotEmpty() && lokasi.isNotEmpty()){
            ref.child(laporId).setValue(lapor).addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
//                    startActivity(MainActivity.getLaunchIntent(this))
                    val intent = Intent (this, Show::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this, "Lengkapi Data di Atas", Toast.LENGTH_SHORT).show()
        }
    }



    public override fun onStart() {
        super.onStart()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result

                    mLatitudeText!!.setText(""+(mLastLocation )!!.latitude)
                    mLongitudeText!!.setText(""+(mLastLocation)!!.longitude)
                    mLatitudeText!!.isEnabled = false
                    mLongitudeText!!.isEnabled = false
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    showMessage(getString(R.string.no_location_detected))
                }
            }
    }

    private fun showMessage(text: String) {
        val container = findViewById<View>(R.id.main_activity_container)
        if (container != null) {
            Toast.makeText(this@LaporSampah, text, Toast.LENGTH_LONG).show()
        }
    }

    private fun showSnackbar(mainTextStringId: Int, actionStringId: Int,
                             listener: View.OnClickListener) {

        Toast.makeText(this@LaporSampah, getString(mainTextStringId), Toast.LENGTH_LONG).show()
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this@LaporSampah,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                View.OnClickListener {
                    // Request permission
                    startLocationPermissionRequest()
                })

        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package",
                            BuildConfig.APPLICATION_ID, null)
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
            }
        }
    }

    companion object {

        private val TAG = "LocationProvider"

        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

}