package com.application.kotproject.profile.update

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.application.kotproject.R
import com.application.kotproject.model.User

class UpdateProfileActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USER = "EXTRA_USER"
        fun createIntent(context: Context, user: User): Intent  {
            var intent = Intent(context, UpdateProfileActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }
}
