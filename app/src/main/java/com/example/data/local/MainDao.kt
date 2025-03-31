package com.example.data.local

import androidx.room.*

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: DataCache)

    @Transaction
    @Query(
        "SELECT * FROM ${DataCache.TABLE_NAME}"
    )
    fun getAllData(): List<DataCache>

    @Transaction
    @Query(
        "SELECT * FROM ${DataCache.TABLE_NAME} ORDER BY ${DataCache.COLUMN_UPDATED_TIME_ISO} DESC LIMIT :limit"
    )
    fun getLatestData(limit: Int): List<DataCache>

    @Transaction
    @Query(
        "SELECT * FROM ${DataCache.TABLE_NAME} WHERE ${DataCache.COLUMN_UPDATED_TIME_ISO} BETWEEN :startDate AND :endDate"
    )
    fun getDataWithDate(startDate: String, endDate: String): List<DataCache>

    @Transaction
    @Query("UPDATE ${DataCache.TABLE_NAME} SET latitude=:latitude, longitude=:longitude WHERE id =:id")
    fun updateLatLong(
        id: String,
        latitude: String,
        longitude: String
    )

    @Transaction
    @Query("DELETE FROM ${DataCache.TABLE_NAME} WHERE ${DataCache.COLUMN_ID} = :id")
    fun removeData(id: String)

    @Transaction
    fun removeAllData(dataModel: List<DataCache>) {
        dataModel.forEach {
            removeData(it.id)
        }
    }
}