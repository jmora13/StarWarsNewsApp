package com.example.phunapp.DetailActivity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.phunapp.PhunModel.PhunModelItem
import com.example.phunapp.R
import com.example.phunapp.utilities.Utilities
import com.example.phunapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var detailItem: PhunModelItem
    private val REQUEST_CALL_PERMISSIONS = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setSupportActionBar(findViewById(R.id.toolbar))

        var description: TextView? = findViewById(R.id.detail_description)
        var detailHeadline: TextView? = findViewById(R.id.detail_headline)
        var detailLocation: TextView? = findViewById(R.id.detail_location)
        var detailTime: TextView? = findViewById(R.id.detail_time)
        var header: ImageView? = findViewById(R.id.header)


        //ALLOW UP BUTTON TO RETURN TO MAIN ACTIVITY
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //GET ID OF ITEM CLICKED FROM MAIN ACTIVITY
        val id = intent.getIntExtra("id", -1)

        //RETRIEVE ITEM FROM DATABASE USING ID
        lifecycleScope.launchWhenCreated {
            try {
                detailItem = detailViewModel.getPhunItem(id)
            } catch (e: IOException) {
                Log.d("IOEXCEPTION", e.message.toString())
                return@launchWhenCreated
            } finally{
                //INIT TEXTVIEWS FROM DATABASE ITEM
                description?.text = detailItem.description
                detailHeadline?.text = detailItem.title
                detailLocation?.text =
                    "${detailItem?.locationline1}, ${detailItem?.locationline2}"
                detailTime?.text = "${Utilities.parseDate(detailItem?.date)} at ${Utilities.parseTime(detailItem?.date)}"

                //SET UP IMAGEVIEW
                detailItem.apply {
                    Glide.with(this@DetailActivity)
                        .load(detailItem?.image)
                        .placeholder(R.drawable.placeholder_nomoon)
                        .apply(
                            RequestOptions().dontTransform()
                        )
                        .into(header!!)
                }
            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        //CALL BUTTON
        R.id.call ->{
            if(detailItem.phone != null) {
                //GET URI FROM SAVED PHONE NUMBER
                val phoneNumber = Uri.parse("tel:" + detailItem.phone)

                //CREATE INTENT AND REQUEST PERMISSIONS TO CALL PHONE NUMBER
                val i = Intent(Intent.ACTION_CALL, phoneNumber)
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQUEST_CALL_PERMISSIONS
                    )
                }
                try {
                        startActivity(i)
                } catch (e: SecurityException) {
                    Log.d("SecurityException", e.message.toString())
                }
            } else{ //DISPLAY MESSAGE IF NO NUMBER IS SAVED
                Toast.makeText(this,"No Phone Number Associated", Toast.LENGTH_LONG).show()
            }
            true
        }

        //SHARE BUTTON
        R.id.share -> {
            //INTENTS FOR SENDING AND SHARING EVENT TO MULTIPLE TYPES OF APPS
            val sendIntent: Intent = Intent().apply{
                action = Intent.ACTION_SEND
                //TEXT WITH NAME, LOCATION, AND DATE
                putExtra(Intent.EXTRA_TEXT, "Check out this Phun event: ${detailItem.title} " +
                        "in ${detailItem.locationline1}, ${detailItem.locationline2}" +
                        " @  ${Utilities.parseDate(detailItem.date)} ${Utilities.parseTime(detailItem.date)}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
            true
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }

}