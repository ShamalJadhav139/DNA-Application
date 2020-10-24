package co.app.dnaapplication.model.repo

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import co.app.dnaapplication.database.AppDatabase
import co.app.dnaapplication.model.Docsresponse
import co.app.dnaapplication.model.dao.DnaDao


class DnaRepository(application: Application) {
    private val dnaDao: DnaDao
    var listLiveData: LiveData<List<Docsresponse>>? = null


    init {
        val database = AppDatabase.getDatabase(application)
        dnaDao = database.dnaDao()
        listLiveData = dnaDao.getDnaResponse()

    }

    fun getdnaAll(): LiveData<List<Docsresponse>>{
        return listLiveData!!
    }

    fun insert(list: List<Docsresponse>){
       dnaDao.insert(list)
    }

    fun deleteAll() {
        dnaDao.deleteAll()
    }








}


