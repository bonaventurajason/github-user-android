package com.bonaventurajason.githubuser.ui.fragment


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.bonaventurajason.githubuser.R
import com.bonaventurajason.githubuser.databinding.FragmentHomeBinding
import com.bonaventurajason.githubuser.helper.Resource
import com.bonaventurajason.githubuser.helper.Utils.hideKeyboard
import com.bonaventurajason.githubuser.ui.MainActivity
import com.bonaventurajason.githubuser.ui.adapter.UserAdapter
import com.bonaventurajason.githubuser.ui.viewmodel.UserViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserViewModel
    lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        searchUser()

        clickUserDetail()

        viewModel.searchUserLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { userResponse ->
                        userAdapter.submitList(userResponse.items)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun clickUserDetail() {
        userAdapter.setOnItemClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserDetailFragment(it))
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun searchUser() {
        binding.searchUser.apply {
            queryHint = resources.getString(R.string.search_hint)
            isIconified = false
            isFocusable = false
            isFocusableInTouchMode = true
            clearFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        viewModel.searchUser(query)
                        context?.hideKeyboard(requireView())
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchUser.apply {
            clearFocus()
        }
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter()
        adapter = userAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)

    }
}