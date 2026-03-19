package shop.nooraholdings.app.di

import androidx.room.Room
import shop.nooraholdings.app.data.database.NORHDDatabase
import org.koin.dsl.module

private const val DB_NAME = "skeleton_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = NORHDDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<NORHDDatabase>().cartItemDao() }

    single { get<NORHDDatabase>().orderDao() }
}