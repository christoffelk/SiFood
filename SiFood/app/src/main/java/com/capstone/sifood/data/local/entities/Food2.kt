package com.capstone.sifood.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "foodLocation")
data class Food2(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "province")
    var province: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "imgUrl")
    var imgUrl: String? = null,

    @field:JvmField
    @ColumnInfo(name = "popular")
    var popular: Boolean? = null,

    @ColumnInfo(name = "provinceEng")
    var provinceEng:String? = null,

    @ColumnInfo(name = "imageLicence")
    var imageLicence: String? = null,

    @ColumnInfo(name = "contentLicance")
    var contentLicence: String? = null

) : Parcelable
