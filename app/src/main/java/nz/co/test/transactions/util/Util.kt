package nz.co.test.transactions.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Util {
    fun processDateString(inputDateString: String): String {
        var readableDateString = ""
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
}