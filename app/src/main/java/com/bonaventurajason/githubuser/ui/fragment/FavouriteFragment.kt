package com.bonaventurajason.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonaventurajason.githubuser.databinding.FragmentFavouriteBinding
import com.bonaventurajason.githubuser.ui.MainActivity
import com.bonaventurajason.githubuser.ui.adapter.UserAdapter
import com.bonaventurajason.githubuser.ui.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar

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
        clickUserDetail()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favouriteUser = userAdapter.differ.currentList[position]
                viewModel.deleteFavouriteUser(favouriteUser)

                Snackbar.make(view, "Successfully deleted favourite user", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveFavouriteUser(favouriteUser)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    private fun clickUserDetail() {
        userAdapter.setOnItemClickListener {
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToUserDetailFragment(it))
        }
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter()
        adapter = userAdapter
    }

    private fun getFavouriteData() {
        viewModel.getFavouriteUser().observe(viewLifecycleOwner, Observer {userResponse ->
            userAdapter.submitList(userResponse)
        })
    }

}