package com.aldin.ghtry.View

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aldin.ghtry.Interfaces.githubAPI
import com.aldin.ghtry.Models.UserData
import com.aldin.ghtry.R
import com.aldin.ghtry.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

class DetailActivity : AppCompatActivity() {

    lateinit var bd : ActivityDetailBinding
    var followers = arrayListOf<UserData>()
    var following = arrayListOf<UserData>()
    var userId : Int = 0
    var avatar : String? = ""
    var username : String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bd.root)

//        get data from intent
        username = intent.getStringExtra("username")
        userId = intent.getIntExtra("id", 0)
        avatar = intent.getStringExtra("avatar")


        bd.tvNameDetail.text = username
        bd.tvIdDetail.text = userId.toString()


        Glide.with(this)
            .load(avatar)
            .into(bd.imgDetail)



        if (!username.isNullOrBlank()){
            getFollowers()
            getFollowing()
        }


    }

    private fun getFollowers() {
        githubAPI.service.getFollowers(username!!).enqueue(object : Callback<List<UserData>>{
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {
                for (i in response.body()!!){
                    followers?.add(i)
                }
                setView()
            }
            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.localizedMessage}" )
            }
        })
    }


    private fun getFollowing() {
        githubAPI.service.getFollowing(username!!).enqueue(object : Callback<List<UserData>>{
            override fun onResponse(
                call: Call<List<UserData>>,
                response: Response<List<UserData>>
            ) {

                for (i in response.body()!!){
                    following?.add(i)
                }

                setView()
            }
            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.localizedMessage}" )

            }
        })
    }


    private fun setView(){
        bd.tvFollowersDetail.text = followers.size.toString()
        bd.tvFollowingDetail.text = following.size.toString()
    }
}