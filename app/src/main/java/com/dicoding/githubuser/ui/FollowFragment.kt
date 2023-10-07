package com.dicoding.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.GithubResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.data.retrofit.ApiService
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.databinding.FragmentFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragment : Fragment() {

    private lateinit var binding : FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inisiasi Binding
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inisiasi View Model
        val fragmentViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory())
            .get(FragmentViewModel::class.java)
        val userName = arguments?.getString(USERNAME).toString()

        //Inisiasi RecyclerView
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser2.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvUser2.addItemDecoration(itemDecoration)

        //Get Index
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        //Kondisi untuk berpindah tab
        if (index == 0){
            fragmentViewModel.getFollowers(userName)
            fragmentViewModel.follow.observe(viewLifecycleOwner){
                setData(it)
            }
        } else{
            fragmentViewModel.getFollowing(userName)
            fragmentViewModel.follow.observe(viewLifecycleOwner){
                setData(it)
            }
        }

        //Observe data loading
        fragmentViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }

    //Fungsi set data ke View
    fun setData(data : List<ItemsItem>){
        val adapter = ListUserAdapter()
        adapter.submitList(data)
        binding.rvUser2.adapter = adapter
    }

    //Mengatur Loading di view
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val USERNAME = "user_name"
    }
}