package com.application.kotproject.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.application.kotproject.App
import com.application.kotproject.MainActivity
import com.application.kotproject.R
import com.application.kotproject.model.User
import com.application.kotproject.network.FishManager
import com.application.kotproject.signin.SigninActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        val REQUEST_CODE_SIGNIN = 10
        val EXTRA_USERNAME = "EXTRA_USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_login_button.setOnClickListener(onLogin)
        login_signin_button.setOnClickListener { startActivityForResult(Intent(this, SigninActivity::class.java), REQUEST_CODE_SIGNIN) }
    }

    val onLogin = View.OnClickListener {
        login_login_button.animate().alpha(0f)
        var valid = true

        if (login_username.text.toString().isEmpty()) {
            valid = false
            login_username.setError(getString(R.string.error_empty))
        }
        if (login_password.text.toString().isEmpty()) {
            valid = false
            login_password.setError(getString(R.string.error_empty))
        }

        if (valid) {
            FishManager.login(User(login_username, login_password))
                    .subscribe(
                            { response ->
                                App.setUser(response.data)
                                loginSuccess()
                                login_login_button.animate().alpha(1f)
                            },
                            { e ->
                                Toast.makeText(this, "error " + e.message, Toast.LENGTH_SHORT).show()
                                login_login_button.animate().alpha(1f)
                            })
        } else
            login_login_button.animate().alpha(1f)
    }

    private fun loginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SIGNIN && resultCode == Activity.RESULT_OK) {
            if (data != null && data.hasExtra(EXTRA_USERNAME))
                login_username.setText(data.getStringExtra(EXTRA_USERNAME))
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }
}
