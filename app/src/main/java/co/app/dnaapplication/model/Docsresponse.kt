package co.app.dnaapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dna")
class Docsresponse : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dna_id")
    var dna = 0

    @ColumnInfo(name = "id")
    var id = ""


    @ColumnInfo(name = "publication_date")
    var publication_date: String? = null

    @ColumnInfo(name = "article_type")
    var article_type: String? = null


}