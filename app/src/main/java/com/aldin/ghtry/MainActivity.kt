package com.aldin.ghtry

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.aldin.ghtry.Adapter.HomeAdapter
import com.aldin.ghtry.Interfaces.GithubService
import com.aldin.ghtry.Interfaces.githubAPI
import com.aldin.ghtry.Models.SearchResponse
import com.aldin.ghtry.Models.UserData
import com.aldin.ghtry.View.DetailActivity
import com.aldin.ghtry.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var bd: ActivityMainBinding
    private lateinit var adapter: HomeAdapter
    var names = arrayListOf<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)


//        make array list of 10 random name

//        for (i in 1..10){
//            names.add("User $i")
//        }


//        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)
// get data from github api
        adapter = HomeAdapter(this, names)
        bd.lvUserName.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserData) {
                Log.d(TAG, "onItemClicked: ${data.login}")
//                intent put extra into DetailActivity
                var i = Intent(this@MainActivity, DetailActivity::class.java)
                    i.putExtra("username", data.login)
                    i.putExtra("id", data.id)
                    i.putExtra("avatar", data.avatar_url)
                startActivity(i)


            }

        })

        githubAPI.service.getListUsers().enqueue(object : Callback<List<UserData>> {
            override fun onResponse(
                call: retrofit2.Call<List<UserData>>,
                response: retrofit2.Response<List<UserData>>
            ) {

                Log.d(TAG, "onResponse: ${response.body()!![0].login}")
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        for (i in data) {
                            names.add(i)
                        }
                        bd.lvUserName.adapter = adapter

                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<List<UserData>>, t: Throwable) {
                println("Error: ${t.message}")
            }

        })

//        insert names into bd.lv_list
//        bd.lvList.adapter = UserAdapter(this, names)

// SearchView get text
        bd.svUserName.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                var result = p0
                Log.d(TAG, "onQueryTextSubmit: $result")
                searchUser(result!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })



    }


    private fun searchUser(uname : String){
        githubAPI.service.getSearchUsers(uname).enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

//                Log.d(TAG, "onResponse: ${response.body()!!.item[0].login}")
                if (response.isSuccessful) {
                    val data = response.body()!!.items
                    names.clear()
                    if (data != null) {
                        for (i in data) {
                            names.add(i)
                        }
                        bd.lvUserName.adapter = adapter
                        adapter.notifyDataSetChanged()

                    }
                }

            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                println("Error: ${t.message}")

            }

        })
    }

    private fun setList(){

    }


}