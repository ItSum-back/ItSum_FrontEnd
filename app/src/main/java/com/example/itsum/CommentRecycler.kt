package com.example.itsum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itsum.retrofit.Comment
import kotlinx.android.synthetic.main.comment_view.view.*
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.android.synthetic.main.item_view.view.createdAt
import kotlinx.android.synthetic.main.item_view.view.createrName

class CommentRecyclerAdapter(private val items: List<Comment>?) : RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items!!.size

    override fun onBindViewHolder(holder: CommentRecyclerAdapter.ViewHolder, position: Int) {

        val item = items?.get(position)

        holder.apply {
            bind(item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return CommentRecyclerAdapter.ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(item: Comment?) {
            view.createrName.text = item?.creatorName
            view.createdAt.text = item?.createdAt
            view.contents.text = item?.contents
        }
    }

}