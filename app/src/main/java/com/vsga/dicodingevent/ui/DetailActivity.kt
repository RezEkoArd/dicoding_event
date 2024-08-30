package com.vsga.dicodingevent.ui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.vsga.dicodingevent.R
import com.vsga.dicodingevent.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    // variable getter untuk kirim id ke detail activity
    companion object{
        const val EXTRA_ID = "extra_id"
    }


    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: UpcomingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        viewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]
        //get Function detail response
        id.let {
            viewModel.getDetailEvent(it.toString())
        }

        // menampilkan data dari viewModel
        viewModel.detailEvent.observe(this ) {
            if (it != null) {
                it.event?.let { detail ->
                    binding.tvDetailTitle.text = detail.name
                    binding.tvOwnerName.text = detail.ownerName
                    binding.beginTime.text = convertDate(detail.beginTime ?: "-")
                    binding.quota.text = detail.quota.toString()
                    binding.registrant.text = detail.registrants.toString()

                    // contoh penggunaan Spanned HTML
//                    val spannedHtml: Spanned =
//                    Html.fromHtml(detail.description ?: "", Html.FROM_HTML_MODE_LEGACY)

                    binding.tvDesciption.loadData(detail.description ?: "", "text/html", "UTF-8")
                    binding.tvDesciption.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                            view.loadUrl(detail.description ?: "")
                            return true
                        }
                    }

                    // bindingImage Using Glide
                    Glide.with(this)
                        .load(detail.mediaCover)
                        .into(binding.imgDetail)



                }
            }
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun convertDate(date: String): String {
        try{
            // formatSatuan
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", Locale.getDefault())

            val oldDate =
                date.let { it1 -> inputFormat.parse(it1) } // Parsing waktu dari String
            val newDate = oldDate?.let { outputFormat.format(it) }

            return newDate ?: "-"
        } catch (ex: Exception){
            return ""
        }
    }
}