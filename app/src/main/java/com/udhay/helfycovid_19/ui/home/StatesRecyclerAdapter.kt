package com.udhay.helfycovid_19.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.data.model.StateModel
import kotlinx.android.synthetic.main.state_recycler_view_item.view.*
import kotlinx.android.synthetic.main.state_recycler_view_item.view.cases_text_view

class StatesRecyclerAdapter(
    private val statesList: List<StateModel.StateModelItem>,
    private val stateClickListener: StateClickListener
): RecyclerView.Adapter<StatesRecyclerAdapter.StatesViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatesViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.state_recycler_view_item, parent, false)
        return StatesViewModel(view)
    }

    override fun getItemCount(): Int = statesList.size

    override fun onBindViewHolder(holder: StatesViewModel, position: Int) {
        val pos = position
        holder.populateDetails(state = statesList[position], position = pos)
    }

    inner class StatesViewModel(private val view: View): RecyclerView.ViewHolder(view) {
        fun populateDetails(state: StateModel.StateModelItem, position: Int) {

            view.let {

                if (position == 0) {
                    it.constraint_Layout.background = it.context.getDrawable(R.drawable.state_card_gradient)
                    it.state_title_text_view.setTextColor(it.context.getColor(R.color.white))
                    it.cases_text_view.setTextColor(it.context.getColor(R.color.white))
                    it.death_text_view.setTextColor(it.context.getColor(R.color.white))
                    it.recovered_text_view.setTextColor(it.context.getColor(R.color.white))
                    it.detail_button.setTextColor(it.context.getColor(R.color.white))
                    it.imageView4.setColorFilter(it.context.getColor(R.color.pink))
                    it.imageView5.setColorFilter(it.context.getColor(R.color.pink))
                    it.imageView6.setColorFilter(it.context.getColor(R.color.pink))
                    it.view.setBackgroundColor(it.context.getColor(R.color.white))
                    it.view.alpha = 0.2f
                    it.view2.setBackgroundColor(it.context.getColor(R.color.white))
                    it.view2.alpha = 0.1f
                    it.view3.setBackgroundColor(it.context.getColor(R.color.white))
                    it.view3.alpha = 0.1f
                }

                it.state_title_text_view.text = state.state
                it.cases_text_view.text = state.confirmed_case.toString()
                it.death_text_view.text = state.death.toString()
                it.recovered_text_view.text = state.recovered.toString()
            }
            view.detail_button.setOnClickListener {
                stateClickListener.stateClicked(state)
            }
        }
    }

    interface StateClickListener {
        fun stateClicked(state: StateModel.StateModelItem)
    }
}