package com.bonaventurajason.consumerapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bonaventurajason.consumerapp.databinding.FragmentFavouriteBinding
import com.bonaventurajason.consumerapp.ui.MainActivity
import com.bonaventurajason.consumerapp.ui.adapter.UserAdapter
import com.bonaventurajason.consumerapp.ui.viewmodel.UserViewModel

class FavouriteFragment : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserViewModel
    lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        getFavouriteData()
        setupRecyclerView()
    }


    private fun setupRecyclerView() = binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter()
        adapter = userAdapter
    }

    private fun getFavouriteData() {
        viewModel.getFavouriteUser(requireContext()).observe(viewLifecycleOwner, { userResponse ->
            userAdapter.submitList(userResponse)
        })
    }

}