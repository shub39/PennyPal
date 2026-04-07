package shub39.pennypal.domain

import org.jetbrains.compose.resources.DrawableResource
import pennypal.composeapp.generated.resources.Res
import pennypal.composeapp.generated.resources.bills
import pennypal.composeapp.generated.resources.car
import pennypal.composeapp.generated.resources.education
import pennypal.composeapp.generated.resources.entertainment
import pennypal.composeapp.generated.resources.food
import pennypal.composeapp.generated.resources.health
import pennypal.composeapp.generated.resources.housing
import pennypal.composeapp.generated.resources.shopping
import pennypal.composeapp.generated.resources.star
import pennypal.composeapp.generated.resources.travel

enum class CategoryIcon {
    FOOD,
    TRANSPORT,
    SHOPPING,
    HOUSING,
    BILLS,
    ENTERTAINMENT,
    HEALTH,
    EDUCATION,
    TRAVEL,
    MISC;

    companion object {
        fun CategoryIcon.toDisplayString(): String {
            return when (this) {
                FOOD -> "Food"
                TRANSPORT -> "Transport"
                SHOPPING -> "Shopping"
                HOUSING -> "Housing"
                BILLS -> "Bills"
                ENTERTAINMENT -> "Entertainment"
                HEALTH -> "Health"
                EDUCATION -> "Education"
                TRAVEL -> "Travel"
                MISC -> "Miscellaneous"
            }
        }

        fun CategoryIcon.toDrawable(): DrawableResource {
            return when (this) {
                FOOD -> Res.drawable.food
                TRANSPORT -> Res.drawable.car
                SHOPPING -> Res.drawable.shopping
                HOUSING -> Res.drawable.housing
                BILLS -> Res.drawable.bills
                ENTERTAINMENT -> Res.drawable.entertainment
                HEALTH -> Res.drawable.health
                EDUCATION -> Res.drawable.education
                TRAVEL -> Res.drawable.travel
                MISC -> Res.drawable.star
            }
        }
    }
}

