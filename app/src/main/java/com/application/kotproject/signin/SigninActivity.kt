package com.application.kotproject.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.application.kotproject.R
import com.application.kotproject.login.LoginActivity
import com.application.kotproject.model.Department
import com.application.kotproject.model.User
import com.application.kotproject.network.FishManager
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    lateinit var departmentAdapter: ArrayAdapter<Department>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        departmentAdapter = ArrayAdapter<Department>(this, R.layout.support_simple_spinner_dropdown_item, ArrayList())
        signin_department.adapter = departmentAdapter

        signin_button.setOnClickListener(onSignin)

        loadDepartments()
    }

    private fun loadDepartments() {
        FishManager.getDepartments()
                .subscribe(
                        { response ->
                            departmentAdapter.clear()
                            departmentAdapter.addAll(response.data)
                            departmentAdapter.notifyDataSetChanged()
                            signin_content_progress.visibility = View.GONE
                            signin_content.visibility = View.VISIBLE
                        },
                        { e ->
                            signin_content_progress.visibility = View.GONE
                            Toast.makeText(this, "error while loading departments " + e.message, Toast.LENGTH_SHORT).show()
                        }
                )
    }

    val onSignin = View.OnClickListener {
        signin_button.animate().alpha(0f)
        var valid = true

        if (signin_firstname.text.toString().isEmpty()) {
            valid = false
            signin_firstname.setError(getString(R.string.error_empty))
        }
        if (signin_lastname.text.toString().isEmpty()) {
            valid = false
            signin_lastname.setError(getString(R.string.error_empty))
        }
        if (signin_username.text.toString().isEmpty()) {
            valid = false
            signin_username.setError(getString(R.string.error_empty))
        }
        if (signin_password.text.toString().isEmpty()) {
            valid = false
            signin_password.setError(getString(R.string.error_empty))
        }
        if (signin_password_conf.text.toString().isEmpty()) {
            valid = false
            signin_password_conf.setError(getString(R.string.error_empty))
        }
        if (!signin_password_conf.text.toString().equals(signin_password.text.toString())) {
            valid = false
            signin_password_conf.setError(getString(R.string.error_equal_password))
        }

        if (valid) {
            val user = User(signin_firstname,
                    signin_lastname,
                    signin_username,
                    signin_password,
                    if (signin_sex.checkedRadioButtonId == R.id.signin_sex_m) getString(R.string.sex_m) else getString(R.string.sex_f),
                    (signin_department.selectedItem as Department).id)
            FishManager.signin(user)
                    .subscribe(
                            { response ->
                                signinSucess(user.username)
                                signin_button.animate().alpha(1f)
                            },
                            { e ->
                                Toast.makeText(this, "error " + e.message, Toast.LENGTH_SHORT).show()
                                signin_button.animate().alpha(1f)
                            }
                    )
        } else
            signin_button.animate().alpha(1f)
    }

    private fun signinSucess(username: String) {
        val intent = Intent()
        intent.putExtra(LoginActivity.EXTRA_USERNAME, username)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
