package com.vsga.dicodingevent

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.databinding.ActivityMainBinding
import com.vsga.dicodingevent.ui.AdapterEventList
import com.vsga.dicodingevent.ui.DetailActivity
import com.vsga.dicodingevent.ui.UpcomingViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: UpcomingViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdapterEventList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AdapterEventList()
        binding.apply{
            rvEvent.layoutManager = LinearLayoutManager(this@MainActivity)
            rvEvent.setHasFixedSize(true)
            rvEvent.adapter = adapter
        }

        adapter.setOnItemClick(object : AdapterEventList.OnItemClickCallback{
            override fun onItemClicked(data: ListEventsItem) {
                Intent(this@MainActivity, DetailActivity::class.java).also{
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[UpcomingViewModel::class.java]
        viewModel.getUpcomingEvent()

        viewModel.listEvent.observe(this) { data ->
//            binding.event.text = it.toString()
            adapter.getEvent(data)

        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}