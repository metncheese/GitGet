package com.rie.githubuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rie.githubuser.R
import com.rie.githubuser.adapter.SectionPageAdapter
import com.rie.githubuser.databinding.ActivityDetailBinding
import com.rie.githubuser.response.ItemsSearch
import com.rie.githubuser.response.ResponseSearchDetail
import com.rie.githubuser.viewmodel.DetailViewModel
import com.rie.githubuser.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.app_name2)

        val username = intent.getStringExtra(EXTRA_USER_NAME) as String
        val avatar = intent.getStringExtra(EXTRA_USER_AVATAR) as String

        detailViewModel.getUser(username)
        val sectionsPagerAdapter = SectionPageAdapter(this)
        sectionsPagerAdapter.username = username

        binding.viewPager.adapter = sectionsPagerAdapter
        supportActionBar?.elevation = 0f

        detailViewModel.userDetail.observe(this) { user ->
            setUserData(user)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
            }
        }

        detailViewModel.getFavoriteUserByUsername(username).observe(this) { users ->
            if (users == null) {
                binding.fabFavorite.setImageDrawable(getDrawable(R.drawable.ic_unfavorite))
                binding?.fabFavorite?.setOnClickListener {
                    var user = ItemsSearch(username, avatar)
                    detailViewModel.insert(user)
                }
            } else {
                binding.fabFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite))
                binding?.fabFavorite?.setOnClickListener {
                    var user = ItemsSearch(username, avatar)
                    detailViewModel.delete(user)
                }
            }
        }
    }

    private fun setUserData(user: ResponseSearchDetail) {
        binding.apply {
            tvName.text = user.name
            tvUsername.text = user.login
            tvCompany.text = user.company
            tvLocation.text = user.location
            tvRepository.text = resources.getString(R.string.publicrepos, user.publicRepos)
        }

        Glide.with(this)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.imgAvatar)
        val countFollow = arrayOf(user.followers, user.following)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position], countFollow[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER_NAME = "extra_user_name"
        const val EXTRA_USER_AVATAR = "extra_user_avatar"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

}