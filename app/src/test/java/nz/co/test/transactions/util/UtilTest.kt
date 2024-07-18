package nz.co.test.transactions.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class UtilTest {
    @Test
    fun testGetDisplayedDateString_validDate() {
        val inputDate = "2021-08-31T15:47:10"
        val expectedOutput = "August 31, 2021 15:47 pm"

        val result = Util.getDisplayedDateString(inputDate)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun testGetDisplayedDateString_invalidDate() {
        val inputDate = "invalid-date"
        val expectedOutput = ""

        val result = Util.getDisplayedDateString(inputDate)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun testGetDisplayedAmount_validAmount() {
        val amount = BigDecimal("1234.56")
        val expectedOutput = "$1,234.56"

        val result = Util.getDisplayedAmount(amount)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun testGetDisplayedGST_validAmount() {
        val transactionAmount = BigDecimal("100.00")
        val expectedOutput = "$15.00"

        val result = Util.getDisplayedGST(transactionAmount)

        assertEquals(expectedOutput, result)
    }
}
