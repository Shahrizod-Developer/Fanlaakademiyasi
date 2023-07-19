package uz.smartmuslim.fanlarakademiyasi.data.remote.api

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import uz.smartmuslim.fanlarakademiyasi.data.remote.request.AdminRequest
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.*
import java.util.UUID

interface Api {

    @GET("/api/messages/v1/completed")
    suspend fun getAllAppeal(): Response<List<AppealResponse>>

    @GET("/api/files/v1/all")
    suspend fun getAllFiles(): Response<List<FileResponse>>

    @Multipart
    @POST("/api/admins/v1/send/document/{userId}")
    suspend fun uploadFile(
        @Path(value = "userId") userId: String,
        @Part file: MultipartBody.Part,
        @Part("messageId") messageId: String
    ): Response<MessageResponse>

    @POST("/api/admins/v1/send/{userId}")
    suspend fun sendMessage(
        @Path(value = "userId") userId: String,
        @Body adminRequest: AdminRequest
    ): Response<MessageResponse>
}