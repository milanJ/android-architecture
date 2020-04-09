package milan.common.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun toCompactString(currency: String, amount: BigDecimal): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    format.currency = Currency.getInstance(currency)
    format.minimumFractionDigits = 2
    format.maximumFractionDigits = 2
    return format.format(amount)
}

fun googlePlayFormatedPriceToBigDecimal(googlePlayPrice: Int): BigDecimal {
    var txtPrice = googlePlayPrice.toString()
    if (txtPrice.length < 7) {
        val txtPriceBuilder = StringBuilder(7)

        var counter = txtPrice.length
        while (counter < 7) {
            txtPriceBuilder.append(0)
            counter++;
        }

        txtPriceBuilder.append(txtPrice)
        txtPrice = txtPriceBuilder.toString()
    }

    val txtPriceBuilder = StringBuilder(txtPrice.length + 1)
    txtPriceBuilder.append(txtPrice)
    txtPriceBuilder.insert(txtPrice.length - 6, '.')

    return BigDecimal(txtPriceBuilder.toString())
}
