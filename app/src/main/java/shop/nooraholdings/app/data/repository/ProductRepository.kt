package shop.nooraholdings.app.data.repository

import shop.nooraholdings.app.R
import shop.nooraholdings.app.data.model.Product
import shop.nooraholdings.app.data.model.ProductCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepository {
    private val products: List<Product> = listOf(
        Product(
            id = 1,
            title = "Premium Coffee Machine",
            description = "Professional espresso maker with built-in grinder and milk frother. Crafted for the discerning coffee lover who demands barista-quality results at home. Features 19-bar pressure, programmable settings and a sleek stainless steel finish.",
            category = ProductCategory.KITCHEN_APPLIANCES,
            price = 349.99,
            imageRes = R.drawable.product_coffee_machine,
        ),
        Product(
            id = 2,
            title = "Smart Air Purifier",
            description = "360° HEPA filtration system with real-time air quality monitoring and app control. Whisper-quiet operation at just 22dB keeps your home fresh and allergen-free around the clock. Covers up to 50m² with a single unit.",
            category = ProductCategory.SMART_HOME,
            price = 199.99,
            imageRes = R.drawable.product_air_purifier,
        ),
        Product(
            id = 3,
            title = "Luxury Velvet Sofa",
            description = "Three-seater sofa upholstered in deep emerald velvet with solid brass-finished legs. A statement piece that elevates any living room with timeless elegance. High-density foam cushioning for lasting comfort.",
            category = ProductCategory.LIVING_ROOM,
            price = 1299.99,
            imageRes = R.drawable.product_velvet_sofa,
        ),
        Product(
            id = 4,
            title = "Ceramic Table Lamp",
            description = "Hand-crafted minimalist lamp with a matte ceramic base and warm 2700K LED bulb. Adds a refined, organic touch to any bedside table or sideboard. Includes a linen shade and dimmer-compatible fitting.",
            category = ProductCategory.HOME_DECOR,
            price = 89.99,
            imageRes = R.drawable.product_table_lamp,
        ),
        Product(
            id = 5,
            title = "Memory Foam Mattress",
            description = "King-size orthopedic mattress with a cooling gel-infused memory foam layer for pressure-point relief and undisturbed sleep. CertiPUR-US certified foam, 7-zone support structure, and a removable bamboo cover.",
            category = ProductCategory.BEDROOM,
            price = 799.99,
            imageRes = R.drawable.product_mattress,
        ),
        Product(
            id = 6,
            title = "Smart Robot Vacuum",
            description = "Laser navigation with multi-floor mapping and an auto-empty dock that holds up to 30 days of dirt. Schedule, monitor and control from anywhere via the companion app. Powerful 2700Pa suction handles carpets and hard floors.",
            category = ProductCategory.SMART_HOME,
            price = 449.99,
            imageRes = R.drawable.product_robot_vacuum,
        ),
        Product(
            id = 7,
            title = "Artisan Knife Set",
            description = "7-piece Damascus steel chef knife collection with hand-forged 67-layer blades and ergonomic walnut handles. The ultimate toolkit for serious home cooks. Includes chef's knife, bread knife, santoku, paring knife, and oak block.",
            category = ProductCategory.KITCHEN_APPLIANCES,
            price = 249.99,
            imageRes = R.drawable.product_knife_set,
        ),
        Product(
            id = 8,
            title = "Silk Bedding Set",
            description = "100% 22-momme mulberry silk duvet cover and pillowcase set in ivory white. Temperature-regulating and hypoallergenic for a luxuriously cool night's sleep. Available in king and super-king sizes.",
            category = ProductCategory.BEDROOM,
            price = 329.99,
            imageRes = R.drawable.product_silk_bedding,
        ),
        Product(
            id = 9,
            title = "Decorative Wall Mirror",
            description = "Large round mirror with a hand-sculpted antique-gold resin frame. 90cm diameter reflects light beautifully to make any room feel brighter and more spacious. Ready to hang with heavy-duty wall fixings included.",
            category = ProductCategory.HOME_DECOR,
            price = 179.99,
            imageRes = R.drawable.product_wall_mirror,
        ),
        Product(
            id = 10,
            title = "Induction Cooktop",
            description = "Portable dual-zone smart cooktop with precision temperature control from 60°C to 240°C and child-lock safety. Heats up 50% faster than gas or electric hobs. Compatible with all induction-ready cookware.",
            category = ProductCategory.KITCHEN_APPLIANCES,
            price = 159.99,
            imageRes = R.drawable.product_induction_cooktop,
        ),
        Product(
            id = 11,
            title = "Ambient Floor Lamp",
            description = "Arc floor lamp with a brushed brass finish and dimmable 3000K warm-white bulb. Creates a cosy reading nook or accentuates any corner of your living room. 210cm height with a weighted marble base for stability.",
            category = ProductCategory.LIVING_ROOM,
            price = 219.99,
            imageRes = R.drawable.product_floor_lamp,
        ),
        Product(
            id = 12,
            title = "Smart Thermostat",
            description = "Wi-Fi enabled smart thermostat with a learning algorithm that adapts to your schedule, cutting energy bills by up to 23%. Works with all boiler types and most smart home systems. Easy 30-minute DIY installation.",
            category = ProductCategory.SMART_HOME,
            price = 129.99,
            imageRes = R.drawable.product_smart_thermostat,
        ),
    )

    fun observeById(id: Int): Flow<Product?> {
        val item = products.find { it.id == id }
        return flowOf(item)
    }

    fun getById(id: Int): Product? {
        return products.find { it.id == id }
    }

    fun observeAll(): Flow<List<Product>> {
        return flowOf(products)
    }
}
