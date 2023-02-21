package uz.smartmuslim.fanlarakademiyasi.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.smartmuslim.fanlarakademiyasi.data.remote.request.AdminRequest
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.AppealResponse
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.MessageResponse
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.UserResponse
import java.util.UUID

interface Api {

    @GET("/api/users/v1")
    suspend fun getAllUsers(): Response<List<UserResponse>>

    @GET("/api/messages/v1/completed")
    suspend fun getAllAppeal(): Response<List<AppealResponse>>

    @POST("/api/admins/v1/send/{userId}")
    suspend fun sendMessage(
        @Path(value = "userId") userId: String,
        @Body adminRequest: AdminRequest
    ): Response<MessageResponse>

}