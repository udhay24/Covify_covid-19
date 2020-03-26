package com.udhay.helfycovid_19.ui.guidelines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udhay.helfycovid_19.R
import kotlinx.android.synthetic.main.guidelines_view_holder.view.*

class GuideLinesAdapter(
    private val guideLines: List<String>
): RecyclerView.Adapter<GuideLinesAdapter.GuideLinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideLinesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.guidelines_view_holder, parent, false)
        return GuideLinesViewHolder(view)
    }

    override fun getItemCount(): Int = guideLines.size

    override fun onBindViewHolder(holder: GuideLinesViewHolder, position: Int) {
        holder.bindView(guideLines[position])
    }

    inner class GuideLinesViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bindView(text: String) {
            view.guide_line_text_view.text = text
        }
    }
}