package uz.smartmuslim.fanlarakademiyasi.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


fun <T> flow() = MutableSharedFlow<T>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

fun Fragment.toast(it:String) = Toast.makeText(this.requireContext(),it, Toast.LENGTH_SHORT ).show()

fun Fragment.hasPermissionApp(permission: List<String>, block: (Int) -> Unit) {
    Dexter.withContext(requireContext()).withPermissions(permission)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                block.invoke(1)
            }
            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                block.invoke(-1)
                p1?.continuePermissionRequest()
            }
        }).check()
}