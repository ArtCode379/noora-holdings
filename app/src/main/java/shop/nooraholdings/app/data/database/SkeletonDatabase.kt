package shop.nooraholdings.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import shop.nooraholdings.app.data.dao.CartItemDao
import shop.nooraholdings.app.data.dao.OrderDao
import shop.nooraholdings.app.data.database.converter.Converters
import shop.nooraholdings.app.data.entity.CartItemEntity
import shop.nooraholdings.app.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NORHDDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}