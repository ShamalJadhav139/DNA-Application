package co.app.dnaapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.app.dnaapplication.adapter.HomeAdapter
import co.app.dnaapplication.constants.ApiConstants
import co.app.dnaapplication.databinding.FragmentHomeBinding
import co.app.dnaapplication.model.DnaResponse
import co.app.dnaapplication.model.Docsresponse
import co.app.dnaapplication.networkContracter.MainActivityPresenter
import co.app.dnaapplication.networkContracter.MainContractor
import co.app.dnaapplication.viewmodel.DnaViewModel


import com.google.gson.Gson



class HomeFragment : Fragment(), MainContractor.View {
    private var homeFragmentBinding: FragmentHomeBinding? = null
    private lateinit var presenter: MainContractor.Presenter
    var homeAdapter = HomeAdapter()

    var dnaViewModel : DnaViewModel?=null




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeFragmentBinding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)
        presenter = MainActivityPresenter(this)
        return homeFragmentBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dnaViewModel = ViewModelProviders.of(this).get(DnaViewModel::class.java)

        homeFragmentBinding!!.dnaList.layoutManager = LinearLayoutManager(context)
        homeAdapter = HomeAdapter()
        //new project adapters
        homeFragmentBinding!!.dnaList.adapter = homeAdapter
        dnaViewModel!!.getDnaAll().observe(this,
            Observer<List<Docsresponse>> { docs ->
                homeAdapter.setData(docs)
            })
       homeFragmentBinding!!.btn.setOnClickListener {
           callGetCurrentPriceApi(homeFragmentBinding!!.search.text.toString())
       }


    }

    fun callGetCurrentPriceApi(searchText :String) {
        presenter.onClick(ApiConstants.dna, arrayOf("title:$searchText"), context!!, true)
    }



    override fun setViewData(data: String, view: ApiConstants) {
        when (view) {
            ApiConstants.dna -> {
                val res = Gson().fromJson(data, DnaResponse::class.java)
                if (res != null) {
                    dnaViewModel!!.deleteAll()
                    dnaViewModel!!.insertAll(res.response.docs)

                } else {
                    Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}