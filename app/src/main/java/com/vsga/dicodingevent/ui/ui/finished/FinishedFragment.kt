package com.vsga.dicodingevent.ui.ui.finished

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.databinding.FragmentFinishedBinding
import com.vsga.dicodingevent.ui.AdapterEventList
import com.vsga.dicodingevent.ui.DetailActivity

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: FinishedViewModel
    private lateinit var adapter : AdapterEventList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this).get(FinishedViewModel::class.java)
        viewModel.getFinishedEvent()

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

        viewModel.finishedEvent.observe(viewLifecycleOwner) { data ->
            adapter.getEvent(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}