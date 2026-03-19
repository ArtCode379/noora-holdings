package shop.nooraholdings.app.data.model

import androidx.annotation.StringRes
import shop.nooraholdings.app.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    KITCHEN_APPLIANCES(R.string.category_kitchen_appliances),
    LIVING_ROOM(R.string.category_living_room),
    BEDROOM(R.string.category_bedroom),
    HOME_DECOR(R.string.category_home_decor),
    SMART_HOME(R.string.category_smart_home),
}
