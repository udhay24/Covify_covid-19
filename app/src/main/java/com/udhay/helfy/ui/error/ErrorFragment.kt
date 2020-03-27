package com.udhay.helfy.ui.error

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.udhay.helfy.R
import com.udhay.helfy.util.isNetworkAvailable
import kotlinx.android.synthetic.main.fragment_error.*

class ErrorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retry_button.setOnClickListener {
            if (isNetworkAvailable(requireContext())) {
                findNavController().navigate(R.id.action_errorFragment_to_homeFragment)
            } else {
                Toast.makeText(this.requireContext(), "No internet", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
