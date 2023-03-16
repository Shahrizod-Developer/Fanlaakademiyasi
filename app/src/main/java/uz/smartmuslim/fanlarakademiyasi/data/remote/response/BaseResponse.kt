package uz.smartmuslim.fanlarakademiyasi.data.remote.response

data class BaseResponse<T>(
    val massage: String,
    val data: T
)