package com.example.itsum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itsum.retrofit.PostsListResponseDto
import kotlinx.android.synthetic.main.myclub_view.view.*

class myClubRecyclerAdapter(private val items: List<PostsListResponseDto>?) : RecyclerView.Adapter<myClubRecyclerAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items!!.size

    override fun onBindViewHolder(holder: myClubRecyclerAdapter.ViewHolder, position: Int) {

        val item = items?.get(position)

        holder.apply {
            bind(item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return myClubRecyclerAdapter.ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(item: PostsListResponseDto?) {
            view.myClubTitle.text = item?.title
            view.myClubContents.text = item?.contents
            view.myClubCreatedAt.text = item?.createdAt
            view.myClubThings.text = (item?.view.toString()+"회")
        }
    }

}