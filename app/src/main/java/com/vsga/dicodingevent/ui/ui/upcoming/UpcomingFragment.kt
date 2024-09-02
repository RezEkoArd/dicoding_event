package com.vsga.dicodingevent.ui.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.databinding.FragmentUpcomingBinding
import com.vsga.dicodingevent.ui.AdapterEventList
import com.vsga.dicodingevent.ui.DetailActivity

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel : UpcomingViewModel
    private lateinit var adapter : AdapterEventList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this).get(UpcomingViewModel::class.java)
        viewModel.getUpcomingEvent()

        adapter = AdapterEventList()
        binding.apply {
            rvEvent.layoutManager = LinearLayoutManager(context)
            rvEvent.setHasFixedSize(true)
            rvEvent.adapter = adapter
        }

        adapter.setOnItemClick(object: AdapterEventList.OnItemClickCallback {
            override fun onItemClicked(data: ListEventsItem) {
                Intent(context, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })


        viewModel.upcomingEvent.observe(viewLifecycleOwner) {
            adapter.getEvent(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}