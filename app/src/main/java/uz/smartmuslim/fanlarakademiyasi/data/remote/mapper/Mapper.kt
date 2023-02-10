package uz.smartmuslim.fanlarakademiyasi.data.remote.mapper

import com.google.firebase.firestore.DocumentSnapshot
import uz.smartmuslim.fanlarakademiyasi.data.model.Admin

object Mapper {

    fun DocumentSnapshot.toAdmin() = Admin(
        username = this["username"].toString(),
        password = this["password"].toString()
    )

}