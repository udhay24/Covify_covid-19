package com.udhay.helfy.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udhay.helfy.R
import com.udhay.helfy.data.model.StateModel
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
        holder.populateDetails(state = statesList[position], position = position)
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
                } else {
                    it.constraint_Layout.background =  it.context.getDrawable(R.drawable.round_purple_border)
                    it.state_title_text_view.setTextColor(it.context.getColor(R.color.textGrey))
                    it.cases_text_view.setTextColor(it.context.getColor(R.color.textGrey))
                    it.death_text_view.setTextColor(it.context.getColor(R.color.textGrey))
                    it.recovered_text_view.setTextColor(it.context.getColor(R.color.textGrey))
                    it.detail_button.setTextColor(it.context.getColor(R.color.textGrey))
                    it.imageView4.clearColorFilter()
                    it.imageView5.clearColorFilter()
                    it.imageView6.clearColorFilter()
                    it.view.setBackgroundColor(it.context.getColor(R.color.textGrey))
                    it.view.alpha = 0.2f
                    it.view2.setBackgroundColor(it.context.getColor(R.color.textGrey))
                    it.view2.alpha = 0.1f
                    it.view3.setBackgroundColor(it.context.getColor(R.color.textGrey))
                    it.view3.alpha = 0.1f
                }

                it.state_title_text_view.text = state.state
                it.cases_text_view.text = state.confirmed_case.toString()
                it.death_text_view.text = state.death.toString()
                it.recovered_text_view.text = state.recovered.toString()
            }
            view.setOnClickListener {
                stateClickListener.stateClicked(state)
            }
        }
    }

    interface StateClickListener {
        fun stateClicked(state: StateModel.StateModelItem)
    }
}