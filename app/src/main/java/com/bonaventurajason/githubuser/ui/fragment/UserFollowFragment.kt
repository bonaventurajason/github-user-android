package com.bonaventurajason.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bonaventurajason.githubuser.databinding.FragmentUserFollowBinding
import com.bonaventurajason.githubuser.helper.Constant.ARG_POSITION
import com.bonaventurajason.githubuser.helper.Constant.ARG_USERNAME
import com.bonaventurajason.githubuser.helper.Resource
import com.bonaventurajason.githubuser.ui.MainActivity
import com.bonaventurajason.githubuser.ui.adapter.UserAdapter
import com.bonaventurajason.githubuser.ui.viewmodel.UserViewModel
import timber.log.Timber

class UserFollowFragment : Fragment() {
    private var _binding: FragmentUserFollowBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserViewModel
    lateinit var userAdapter: UserAdapter

    companion object{
        fun newInstance(position: Int, username: String) : UserFollowFragment{
            val bundle = Bundle().apply {
                putInt(ARG_POSITION, position)
                putString(ARG_USERNAME, username)

            }
            return UserFollowFragment().apply {
                arguments = bundle
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        arguments?.apply {
            if(getInt(ARG_POSITION) == 0){
                getString(ARG_USERNAME)?.let { viewModel.getUserFollower(it) }
                viewModel.followerUserLiveData.observe(viewLifecycleOwner, Observer {response ->
                    when (response) {
                        is Resource.Success -> {
//                            hideProgressBar()
                            response.data?.let { userResponse ->
                                Timber.d("List user: $userResponse")
                                userAdapter.submitList(userResponse)
                            }
                        }
                        is Resource.Error -> {
//                            hideProgressBar()
                            response.message?.let { message ->
                                Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                        is Resource.Loading -> {
//                            showProgressBar()
                        }
                    }
                })
            }
            else{
                getString(ARG_USERNAME)?.let { viewModel.getUserFollowing(it) }
                viewModel.followingUserLiveData.observe(viewLifecycleOwner, Observer {response ->
                    when (response) {
                        is Resource.Success -> {
//                            hideProgressBar()
                            response.data?.let { userResponse ->
                                Timber.d("List user: $userResponse")
                                userAdapter.submitList(userResponse)
                            }
                        }
                        is Resource.Error -> {
//                            hideProgressBar()
                            response.message?.let { message ->
                                Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                        is Resource.Loading -> {
//                            showProgressBar()
                        }
                    }
                })
            }
        }
//        val username = arguments?.getString(ARG_USERNAME)
//        if (username != null) {
//            viewModel.getUserFollower(username)
//        }
        setupRecyclerView()



    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter()
        adapter = userAdapter
    }

//    private fun hideProgressBar() {
//        binding.progressBar.visibility = View.GONE
//    }
//
//    private fun showProgressBar() {
//        binding.progressBar.visibility = View.VISIBLE
//    }
}