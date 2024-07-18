package nz.co.test.transactions.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Util {
    /**
     * Passes a [inputDateString] raw data string to this group, for example "2021-08-31T15:47:10"
     * @return the human friendly string, for example "August 31, 2021 15:47 pm"
     */
    fun getDisplayedDateString(inputDateString: String): String {
        val readableDateString: String
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val dateTime = LocalDateTime.parse(inputDateString, formatter)

            // Format the LocalDateTime into a more readable format
            val readableFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm a")
            readableDateString = dateTime.format(readableFormatter)
        } catch (_: Exception) {
            return ""
        }


        return readableDateString
    }

    /**
     * Passes a [BigDecimal] raw money amount, for example 1234.56
     * @return the human friendly string, for example $1,234.56
     */
    fun getDisplayedAmount(amount: BigDecimal): String {
        return try {
            DecimalFormat.getCurrencyInstance().format(amount)
        } catch (_: Exception) {
            ""
        }
    }

    /**
     * Passes a [BigDecimal] raw money amount, for example 100.00
     * @return the human friendly calculated gst amount string, for example $15.00
     */
    fun getDisplayedGST(transactionAmount: BigDecimal): String {
        return try {
            val gst = transactionAmount.times(BigDecimal(0.15))
            return DecimalFormat.getCurrencyInstance().format(gst)
        } catch (_: Exception) {
            ""
        }
    }

    fun <T> LifecycleOwner.collectStateFlow(
        stateFlow: StateFlow<T>,
        state: Lifecycle.State = Lifecycle.State.RESUMED,
        collect: (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(state) {
                stateFlow.collect { value ->
                    collect(value)
                }
            }
        }
    }
}