package com.bonaventurajason.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bonaventurajason.githubuser.R
import com.bonaventurajason.githubuser.data.model.User
import com.bonaventurajason.githubuser.data.model.UserDetailResponse
import com.bonaventurajason.githubuser.databinding.FragmentUserDetailBinding
import com.bonaventurajason.githubuser.helper.Resource
import com.bonaventurajason.githubuser.ui.MainActivity
import com.bonaventurajason.githubuser.ui.adapter.SectionPagerAdapter
import com.bonaventurajason.githubuser.ui.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.item_user.view.*
import timber.log.Timber

class UserDetailFragment : Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    var isFavourite = false

    lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val user = UserDetailFragmentArgs.fromBundle(arguments as Bundle).user

        viewModel.getUserDetail(user.login)

        viewModel.detailUserLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { userResponse ->
                        setUserDetailLayout(userResponse)
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
        setTabLayout(user.login)
        checkIsUserFavourite(user.login)

        clickToFavouriteUser(user, view)
    }

    private fun checkIsUserFavourite(username: String) {
        viewModel.checkFavouriteUser(username).observe(viewLifecycleOwner, Observer {
            Timber.d("Is favourite $isFavourite")
            isFavourite = if(it != null){
                binding.fab.setImageResource(R.drawable.ic_thumb_down)
                true
            } else{
                binding.fab.setImageResource(R.drawable.ic_thumb_up)
                false
            }
        })
    }

    private fun clickToFavouriteUser(user: User, view: View) {
        binding.fab.setOnClickListener {
            if(isFavourite){
                viewModel.deleteFavouriteUser(user)
                Snackbar.make(view, "This user become your un-favourite", Snackbar.LENGTH_SHORT).show()
                binding.fab.setImageResource(R.drawable.ic_thumb_up)
                isFavourite = false
            }
            else{
                viewModel.saveFavouriteUser(user)
                Snackbar.make(view, "This user become your favourite", Snackbar.LENGTH_SHORT).show()
                binding.fab.setImageResource(R.drawable.ic_thumb_down)
                isFavourite = true
            }
        }
    }

    private fun setTabLayout(username: String) {

        binding.viewPager.adapter = SectionPagerAdapter(
            this@UserDetailFragment,
            childFragmentManager,
            username
        )
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setUserDetailLayout(userResponse: UserDetailResponse) {
        Glide.with(this)
            .load(userResponse.avatar_url)
            .into(binding.photo)
        binding.name.text = userResponse.name
        binding.location.text = userResponse.location
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}