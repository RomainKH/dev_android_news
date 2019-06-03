package fr.romainkhanoyan.droidnewsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsFeedButton.setOnClickListener {
            val intent = Intent(this, NewsFeedActivity::class.java)

            startActivity(intent)
        }

        val options = arrayListOf("France", "lol","United Kingdom")
        countrySelector.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,options)
        countrySelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "not selected", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@MainActivity, options[p2], Toast.LENGTH_LONG).show()
            }
        }
    }
}