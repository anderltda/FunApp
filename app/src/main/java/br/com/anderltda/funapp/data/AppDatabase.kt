package br.com.anderltda.funapp.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import br.com.anderltda.funapp.data.converter.DateConverter
import br.com.anderltda.funapp.data.dao.AddressDao
import br.com.anderltda.funapp.data.entity.Address

@Database(entities = [Address::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun addressDao(): AddressDao

    companion object {
        private val INSTANCE : AppDatabase? = null
    }
}