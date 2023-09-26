package com.aldin.ghtry.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aldin.ghtry.Models.UserData
import com.aldin.ghtry.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


//recycleriew adapter
class HomeAdapter(private val context: Context, private val list: ArrayList<UserData>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    //view holder
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_userName)
        var imgPhoto: ImageView = itemView.findViewById(R.id.iv_avatar)
    }

    //on create view holder
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false)
        return ListViewHolder(view)
    }

    //get item count
    override fun getItemCount(): Int {
        return list.size
    }

    //on bind view holder
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = list[position]

        Glide.with(holder.itemView.context)
            .load(user.avatar_url)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPhoto)

        holder.tvName.text = user.login



        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.adapterPosition]) }
    }

    //on item click callback
    interface OnItemClickCallback {
        fun onItemClicked(data: UserData)
    }
}