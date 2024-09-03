package com.vsga.dicodingevent.ui.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.databinding.FragmentHomeBinding
import com.vsga.dicodingevent.ui.AdapterEventList
import com.vsga.dicodingevent.ui.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter : AdapterEventList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        adapter = AdapterEventList()
        binding.apply {
            rvEvent.layoutManager = LinearLayoutManager(context)
            rvEvent.setHasFixedSize(true)
            rvEvent.adapter = adapter
        }

        viewModel.allEvent.observe(viewLifecycleOwner) {
            adapter.getEvent(it)
        }

        with(binding) {
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let{
                        viewModel.getAllEvent(it)
                    }
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

            viewModel.getAllEvent()

        }



        adapter.setOnItemClick(object: AdapterEventList.OnItemClickCallback {
            override fun onItemClicked(data: ListEventsItem) {
                Intent(context, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}