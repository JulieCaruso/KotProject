package com.application.kotproject.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.application.kotproject.App
import com.application.kotproject.R
import com.application.kotproject.model.User
import com.application.kotproject.network.FishManager
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val user = App.getUser()
        if (user != null)
            loadDepartment(user)
    }

    private fun loadDepartment(user: User) {
        FishManager.getDepartment(user.departmentid)
                .subscribe(
                        { response ->
                            setUser(user, response.data.name)
                        },
                        { e ->
                            Toast.makeText(this, "error " + e.message, Toast.LENGTH_SHORT).show()
                            setUser(user, "-")
                        }
                )
    }

    private fun setUser(user: User, department: String) {
        progress.visibility = View.GONE
        content.visibility = View.VISIBLE
        username.text = user.username
        firstname.text = user.firstname
        lastname.text = user.lastname
        department_name.text = department
    }
}
