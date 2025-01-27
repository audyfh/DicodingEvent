package com.example.dicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dicodingevent.R
import com.example.dicodingevent.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args = DetailActivityArgs.fromBundle(intent.extras!!)
        val event = args.event
        val remainingQuota = event.quota-event.registrants

        with(binding){
            textEventName.text = event.name
            textEventTime.text = getString(R.string.starting_from, event.beginTime)
            textEventOwner.text = event.ownerName
            textEventQuota.text = getString(R.string.remaining_quota, remainingQuota.toString())
            textEventDescription.text = stripHtmlTags(event.description)
            Glide.with(this@DetailActivity)
                .load(event.mediaCover)
                .into(imageEventCover)

            buttonOpenLink.setOnClickListener{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(event.link)))
            }

            buttonBack.setOnClickListener {
                finish()
            }
        }

    }

    private fun stripHtmlTags(htmlText: String): Spanned {
        return Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    }
}