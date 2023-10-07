package com.dicoding.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ItemRowBinding

class ListUserAdapter : ListAdapter<ItemsItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder (val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user : ItemsItem){
            binding.tvNameUser.text = "${user.login}"
            Glide.with(itemView.context)
                .load("${user.avatarUrl}")
                .into(binding.profilePicture)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra("USERNAME", data.login.toString())
            holder.itemView.context.startActivity(intentDetail)
        }

    }

}