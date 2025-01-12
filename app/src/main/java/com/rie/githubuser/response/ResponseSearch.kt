package com.rie.githubuser.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseSearch(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<ItemsSearch?>? = null
)

@Entity
@Parcelize
data class ItemsSearch(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String,

    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    ) : Parcelable