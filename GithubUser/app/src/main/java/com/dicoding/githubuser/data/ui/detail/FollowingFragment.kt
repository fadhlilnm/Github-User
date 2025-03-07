package com.dicoding.githubuser.data.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.ui.UserAdapter
import com.dicoding.githubuser.databinding.FragmentFollowBinding

class FollowingFragment: Fragment(R.layout.fragment_follow) {
    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowers.setHasFixedSize(true)
            rvFollowers.layoutManager = LinearLayoutManager(activity)
            rvFollowers.adapter = adapter

        }
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        showLoading(true)
        viewModel.setListFollowing(username)

        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}