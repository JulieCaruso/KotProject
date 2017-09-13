package com.application.kotproject.network

import com.application.kotproject.BuildConfig
import com.application.kotproject.model.Department
import com.application.kotproject.model.RestResponse
import com.application.kotproject.model.User
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface FishApi {

    // AUTHENTIFICATION

    @POST("/api/login")
    fun login(@Body user: User): Observable<RestResponse<User>>

    @POST("/api/signin")
    fun signin(@Body user: User): Observable<RestResponse<User>>

    @PUT("/api/update-profile/{id}")
    fun updateProfile(@Path("id") userId: Int?, @Body user: User): Observable<RestResponse<User>>

    // DEPARTMENTS

    @GET("/api/departments")
    fun getDepartments(): Observable<RestResponse<List<Department>>>

    @GET("/api/departments/{id}")
    fun getDepartment(@Path("id") departmentId: Int?): Observable<RestResponse<Department>>

    @POST("/api/departments")
    fun addDepartment(@Body department: Department): Observable<RestResponse<Department>>

    companion object {
        fun create(): FishApi {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.FISH_API_URL)
                    .build()
            return retrofit.create(FishApi::class.java)
        }
    }
}