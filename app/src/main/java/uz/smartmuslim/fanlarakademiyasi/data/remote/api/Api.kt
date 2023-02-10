package uz.smartmuslim.fanlarakademiyasi.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.AppealResponse
import uz.smartmuslim.fanlarakademiyasi.data.remote.response.UserResponse

interface Api {

    @GET("/api/users/v1")
    suspend fun getAllUsers(): Response<List<UserResponse>>

    @GET("/api/messages/v1")
    suspend fun getAllAppeal(): Response<List<AppealResponse>>

//    @GET("/api/messages/v1/completed")
//    suspend fun getAllIsCompleteAppeal(): Response<List<AppealResponse>>
//
//    @GET("/api/messages/v1/not_completed")
//    suspend fun getAllIsNotCompleteAppeal(): Response<List<AppealResponse>>

    @POST("/api/admins/v1/send/{userId}")
    suspend fun sendMessage(): Response<List<UserResponse>>

}