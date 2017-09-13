package com.application.kotproject.network

import com.application.kotproject.model.Department
import com.application.kotproject.model.RestResponse
import com.application.kotproject.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FishManager {

    companion object {

        @JvmStatic
        val api by lazy {
            FishApi.create()
        }

        @JvmStatic
        fun login(user: User): Observable<RestResponse<User>> {
            return api.login(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun signin(user: User): Observable<RestResponse<User>> {
            return api.signin(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun getDepartments(): Observable<RestResponse<List<Department>>> {
            return api.getDepartments()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun getDepartment(id: Int): Observable<RestResponse<Department>> {
            return api.getDepartment(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}