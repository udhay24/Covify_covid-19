package com.udhay.helfy.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.udhay.helfy.R
import com.udhay.helfy.data.model.HelplineModel
import com.udhay.helfy.ui.guidelines.HelpLineadapter
import kotlinx.android.synthetic.main.fragment_helpline.*

class HelplineFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val helplineData = Gson().fromJson<HelplineModel>(getHelpData(),HelplineModel::class.java)
        val adapter = HelpLineadapter(helplineData.data)
        helpline_rc.adapter = adapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_helpline, container, false)
    }

    private fun getHelpData() :String{
        return """
            {
              "data": [{
                  "state": {
                    "name": "Kerala",
                    "helpline": ["0471-2552056"]
                  }
                },
                {
                  "state": {
                    "name": "Andhra Pradesh",
                    "helpline": ["0866-2410978"]
                  }
                },
                {
                  "state": {
                    "name": "Andhra Pradesh",
                    "helpline": ["0866-2410978"]
                  }
                },
                {
                  "state": {
                    "name": "Arunachal Pradesh",
                    "helpline": ["9436055743"]
                  }
                },
                {
                  "state": {
                    "name": "Assam",
                    "helpline": ["6913347770"]
                  }
                },
                {
                  "state": {
                    "name": "Bihar",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Chhattisgarh",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Goa",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Gujarat",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Haryana",
                    "helpline": ["8558893911"]
                  }
                },
                {
                  "state": {
                    "name": "Himachal Pradesh",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Jharkhand",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Karnataka",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Madhya Pradesh",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Maharashtra",
                    "helpline": ["020-26127394"]
                  }
                },
                {
                  "state": {
                    "name": "Manipur",
                    "helpline": ["3852411668"]
                  }
                },
                {
                  "state": {
                    "name": "Meghalaya",
                    "helpline": ["108"]
                  }
                },
                {
                  "state": {
                    "name": "Mizoram",
                    "helpline": ["102"]
                  }
                },
                {
                  "state": {
                    "name": "Nagaland",
                    "helpline": ["7005539653"]
                  }
                },
                {
                  "state": {
                    "name": "Odisha",
                    "helpline": ["9439994859"]
                  }
                },
                {
                  "state": {
                    "name": "Punjab",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Rajasthan",
                    "helpline": ["0141-2225624"]
                  }
                },
                {
                  "state": {
                    "name": "Sikkim",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Tamil Nadu",
                    "helpline": ["044-29510500"]
                  }
                },
                {
                  "state": {
                    "name": "Telangana",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Tripura",
                    "helpline": ["0381-2315879"]
                  }
                },
                {
                  "state": {
                    "name": "Uttarakhand",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Uttar Pradesh",
                    "helpline": ["18001805145"]
                  }
                },
                {
                  "state": {
                    "name": "West Bengal",
                    "helpline": ["1800313444222", "03323412600"]
                  }
                },
                {
                  "state": {
                    "name": "Andaman and Nicobar Islands",
                    "helpline": ["03192-232102"]
                  }
                },
                {
                  "state": {
                    "name": "Chandigarh",
                    "helpline": ["9779558282"]
                  }
                },
                {
                  "state": {
                    "name": "Dadra and Nagar Haveli and Daman & Diu",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Delhi",
                    "helpline": ["011-22307145"]
                  }
                },
                {
                  "state": {
                    "name": "Jammu & Kashmir",
                    "helpline": ["01912520982", "0194-2440283"]
                  }
                },
                {
                  "state": {
                    "name": "Ladakh",
                    "helpline": ["01982256462"]
                  }
                },
                {
                  "state": {
                    "name": "Lakshadweep",
                    "helpline": ["104"]
                  }
                },
                {
                  "state": {
                    "name": "Puducherry",
                    "helpline": ["104"]
                  }
                }
              ]
            }
        """.trimIndent()
    }
}
