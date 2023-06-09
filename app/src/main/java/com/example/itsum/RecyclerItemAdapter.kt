package com.example.itsum

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itsum.retrofit.PostsListResponseDto
import kotlinx.android.synthetic.main.item_view.view.*

class RecyclerUserAdapter(private val items: List<PostsListResponseDto>?) : RecyclerView.Adapter<RecyclerUserAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items!!.size

    override fun onBindViewHolder(holder: RecyclerUserAdapter.ViewHolder, position: Int) {

        val item = items?.get(position)
        val listener = View.OnClickListener { it ->
            val clubScreenIntent = Intent(it.context, Clubscreen::class.java)
            clubScreenIntent.putExtra("id", item?.id)
            clubScreenIntent.run { it.context.startActivity(this) }

        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return RecyclerUserAdapter.ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: PostsListResponseDto?) {
            view.createrName.text = item?.title
            view.createdAt.text = item?.contents
            view.setOnClickListener(listener)
        }
    }

}