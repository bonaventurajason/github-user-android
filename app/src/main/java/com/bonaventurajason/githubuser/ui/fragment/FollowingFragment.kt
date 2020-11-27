package com.bonaventurajason.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bonaventurajason.githubuser.databinding.FragmentFollowingBinding
import com.bonaventurajason.githubuser.helper.Constant

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!


    companion object{
        fun newInstance(username: String) : FollowingFragment{
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(Constant.ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(Constant.ARG_USERNAME)

    }
}