package co.app.dnaapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.app.dnaapplication.model.Docsresponse
import co.app.dnaapplication.model.repo.DnaRepository
import co.app.dnaapplication.networkContracter.MainContractor


import java.util.ArrayList

class DnaViewModel(application: Application) :
    AndroidViewModel(application) {//},MainContract.View {

    private val mRepository: DnaRepository = DnaRepository(application)
    internal val docs: LiveData<List<Docsresponse>>

   // private var presenter: MainContractor.Presenter? = null



    init {
        docs = mRepository.getdnaAll()


//        presenter = MainActivityPresenter(this)
    }

  fun getDnaAll(): LiveData<List<Docsresponse>>{
      return docs
  }

    fun insertAll(list: List<Docsresponse>){
        mRepository.insert(list)

    }

    internal fun deleteAll() {
        mRepository.deleteAll()
    }





}
