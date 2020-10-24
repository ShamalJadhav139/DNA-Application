package co.app.dnaapplication.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.app.dnaapplication.model.DnaResponse
import co.app.dnaapplication.model.Docsresponse


@Dao
interface DnaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(docsresponse: Docsresponse)

    @Query("DELETE FROM dna")
    fun deleteAll()

    @Query("SELECt * from dna")
    fun  getDnaResponse():LiveData<List<Docsresponse>>

    @Insert
    fun insert(projects: List<Docsresponse>)


}
