package com.example.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DataCache.TABLE_NAME)
data class DataCache(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) var id: String = "",
    @ColumnInfo(name = COLUMN_NAME) var name: String = "",
    @ColumnInfo(name = COLUMN_DISCLAIMER) var disclaimer: String = "",
    @ColumnInfo(name = COLUMN_UPDATED_TIME) var updatedTime: String = "",
    @ColumnInfo(name = COLUMN_UPDATED_TIME_ISO) var updatedTimeISO: String = "",
    @ColumnInfo(name = COLUMN_UPDATED_TIME_UK) var updatedTimeUK: String = "",
    @ColumnInfo(name = COLUMN_LAT) var latitude: String = "",
    @ColumnInfo(name = COLUMN_LONG) var longitude: String = "",
    @ColumnInfo(name = COLUMN_BPI) var bpi: String = ""
) {
    companion object {
        const val TABLE_NAME = "data"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DISCLAIMER = "disclaimer"
        const val COLUMN_UPDATED_TIME = "updated"
        const val COLUMN_UPDATED_TIME_ISO = "updatedISO"
        const val COLUMN_UPDATED_TIME_UK = "updatedUK"
        const val COLUMN_LAT = "latitude"
        const val COLUMN_LONG = "longitude"
        const val COLUMN_BPI = "bpi"
    }
}