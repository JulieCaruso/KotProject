package com.application.kotproject.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.application.kotproject.App
import com.application.kotproject.R
import com.application.kotproject.model.User
import com.application.kotproject.network.FishManager
import com.application.kotproject.profile.update.UpdateProfileActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        user = App.getUser()
        if (user != null)
            loadDepartment(user!!)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.update_profile -> {
                startActivity(UpdateProfileActivity.createIntent(this, user!!))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
