package nz.co.test.transactions.test

import androidx.recyclerview.widget.RecyclerView
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import nz.co.test.transactions.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.UninstallModules
import nz.co.test.transactions.data.TransactionRepository
import nz.co.test.transactions.di.NetworkModule
import nz.co.test.transactions.di.RepositoryModule
import nz.co.test.transactions.services.TransactionApi
import nz.co.test.transactions.ui.activities.MainActivity
import javax.inject.Inject

@UninstallModules(NetworkModule::class, RepositoryModule::class)
@HiltAndroidTest
class TransactionTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Inject
    lateinit var transactionApi: TransactionApi

    @Inject
    lateinit var repository: TransactionRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun clickFirstItem_navigatesToTransactionDetailFragment() {
        // Select the first item of the transaction list
       onView(withId(R.id.recyclerView))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Check if TransactionDetailFragment is displayed
        onView(withId(R.id.transactionDetailFragmentRoot))
            .check(matches(isDisplayed()))
    }
}