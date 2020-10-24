package co.app.dnaapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.app.dnaapplication.databinding.DnaItemBinding
import co.app.dnaapplication.model.DnaResponse
import co.app.dnaapplication.model.Docsresponse


class HomeAdapter() :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    var list: List<Docsresponse> = emptyList()
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val binding = DnaItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.dnaId.text = list[position].id
        holder.binding.dnaDate.text = list[position].publication_date
        holder.binding.dnaType.text = list[position].article_type
        //holder.binding.dnaAbstract.text = list[position].response.docs[position].abstract

    }

    inner class ViewHolder(binding: DnaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    fun setData(list: List<Docsresponse>?) {
        this.list = list!!
        notifyDataSetChanged()
    }
}