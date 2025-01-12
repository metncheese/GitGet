package com.rie.githubuser.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rie.githubuser.R
import com.rie.githubuser.adapter.ListSearchAdapter
import com.rie.githubuser.databinding.ActivityDetailBinding
import com.rie.githubuser.databinding.ActivityFavoriteBinding
import com.rie.githubuser.response.ItemsSearch
import com.rie.githubuser.viewmodel.DetailViewModel
import com.rie.githubuser.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvFavorite.layoutManager = LinearLayoutManager(applicationContext)
        detailViewModel.getFavoriteAllUser().observe(this) { users ->
            val items = arrayListOf<ItemsSearch>()
            users.map {
                val item = ItemsSearch(login = it.login, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            setUserData(items)
        }
    }

    private fun setUserData(users: List<ItemsSearch>?) {
        val listUserAdapter = ListSearchAdapter(users as ArrayList<ItemsSearch>)
        binding.rvFavorite.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsSearch) {
                showSelectedUser(data)
            }
        })
    }
    private fun showSelectedUser(user: ItemsSearch) {
        val detailUserIntent = Intent(this, DetailActivity::class.java)
        detailUserIntent.putExtra(DetailActivity.EXTRA_USER_NAME, user.login)
        detailUserIntent.putExtra(DetailActivity.EXTRA_USER_AVATAR, user.avatarUrl)
        startActivity(detailUserIntent)
    }
}