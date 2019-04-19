package br.com.anderltda.funapp.activity

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import br.com.anderltda.funapp.R

open class BaseActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var title: TextView

    fun changeTitle(toolbarId: Int, titlePage: String) {
        toolbar = findViewById<Toolbar>(toolbarId)

        setSupportActionBar(toolbar)

        title = toolbar.findViewById(R.id.tv_title) as TextView
        title.text = titlePage
        supportActionBar!!.setTitle("")
    }

    fun setupToolbar(toolbarId: Int, titlePage: String) {
        toolbar = findViewById<Toolbar>(toolbarId)
        setSupportActionBar(toolbar)

        title = toolbar.findViewById(R.id.tv_title) as TextView
        title.text = titlePage

        supportActionBar!!.setTitle("")
    }

    fun setupToolbarWithUpNav(toolbarId: Int, titlePage: String, @DrawableRes res: Int) {
        toolbar = findViewById<Toolbar>(toolbarId)
        setSupportActionBar(toolbar)

        title = toolbar.findViewById(R.id.tv_title) as TextView
        title.text = titlePage

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(res)
        supportActionBar!!.setTitle("")
    }

}
