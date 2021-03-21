package com.loc.sampahuser

import java.sql.Date
import java.text.DateFormat

class Lapors(var uid: String, var iduser: String, var nama: String, var keterangan : String, var lokasi: String, var latitude: Double, var langitude: Double, var atasi: String, var tanggallapor: String, var tanggalatasi: String, var petugas: String) {
    constructor() : this("","","","","",0.0,0.0,"", "","","") {

    }
}