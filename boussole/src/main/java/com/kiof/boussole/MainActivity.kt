package com.kiof.boussole

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.location.FusedLocationProviderClient
import com.kiof.boussole.global.AppUtils
import com.kiof.boussole.global.HtmlAlertDialog
import com.kiof.boussole.global.PrefUtil
import com.kiof.boussole.global.solar.SunriseSunsetCalculator
import com.tfb.fbtoast.FBToast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mMainView: View
    private lateinit var mGreetingTextView: TextView
    private lateinit var mPositionTextView: TextView
    private lateinit var mMenuItem: MenuItem
    private lateinit var mAdView: AdView
    private lateinit var mRewardedAd: RewardedAd

    companion object {
        const val BACKGROUND = "background"
        const val GREETING_MESSAGE = "greeting_message"
        const val LOCATION = "location"
        private val TAG = MainActivity::class.java.simpleName
        private const val REQUEST_CHECK_GOOGLE_SETTINGS = 0x99

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
//        supportActionBar?.setIcon(R.drawable.icon)
        supportActionBar?.title = getString(R.string.app_name)

        PreferenceManager.getDefaultSharedPreferences(this.applicationContext)

        mMainView = findViewById(R.id.main_layout)
        mMainView.background = ContextCompat.getDrawable(
            applicationContext,
            R.drawable.bg_default
        )

        mAdView = findViewById(R.id.adView)
        // Initialize and load Ads
        MobileAds.initialize(this) {}
        if (nowSeconds > PrefUtil.getRemoveAds(this)) {
            mAdView.loadAd(AdRequest.Builder().build())
            mRewardedAd = createAndLoadRewardedAd()
        }

        mGreetingTextView = findViewById(R.id.tv_greeting)
        mGreetingTextView.setText(R.string.standard_greeting)
        findViewById<View>(R.id.iv_location).setOnClickListener(this)
        mPositionTextView = findViewById(R.id.tv_position)
        val bundle = intent.extras
        if (bundle != null && bundle.containsKey(BACKGROUND) && bundle.containsKey(
                GREETING_MESSAGE
            ) && bundle.containsKey(LOCATION)
        ) {
            val background = bundle.getInt(BACKGROUND)
            val greetingMessage = bundle.getInt(GREETING_MESSAGE)
            if (background != -1 && greetingMessage != -1) {
                mMainView.background = ContextCompat.getDrawable(
                    applicationContext,
                    background
                )
                mGreetingTextView.setText(greetingMessage)
                val location =
                    bundle.getParcelable<Location>(LOCATION)
                if (location != null) {
                    mPositionTextView.visibility = View.VISIBLE
                    mPositionTextView.text = AppUtils.convert(
                        location.latitude,
                        location.longitude
                    )
                } else {
                    mPositionTextView.visibility = View.GONE
                }
            } else {
                mMainView.background = ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.bg_default
                )
                mGreetingTextView.setText(R.string.standard_greeting)
            }
        } else {
            mMainView.background = ContextCompat.getDrawable(
                applicationContext,
                R.drawable.bg_default
            )
            mGreetingTextView.setText(R.string.standard_greeting)
        }
    }

    /**
     *
     */
    private fun initialize() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val fusedLocationProviderClient =
            FusedLocationProviderClient(applicationContext)
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    updateDayState(location)
                    mPositionTextView.visibility = View.VISIBLE
                    mPositionTextView.text = AppUtils.convert(
                        location.latitude,
                        location.longitude
                    )
                }
            }
    }

    /**
     * @param location
     */
    private fun updateDayState(location: Location) {
        val location1 =
            com.kiof.boussole.global.solar.Location(location.latitude, location.longitude)
        val sunriseSunsetCalculator =
            SunriseSunsetCalculator(location1, Calendar.getInstance().timeZone)
        val sunrise =
            sunriseSunsetCalculator.getOfficialSunriseCalendarForDate(Calendar.getInstance())
                ?.time
        val sunset =
            sunriseSunsetCalculator.getOfficialSunsetCalendarForDate(Calendar.getInstance())
                ?.time
        val current = Calendar.getInstance().time
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 12
        calendar[Calendar.MINUTE] = 30
        calendar[Calendar.SECOND] = 0
        val noon = calendar.time
        val calendar1 = Calendar.getInstance()
        calendar1[Calendar.HOUR_OF_DAY] = 0
        calendar1[Calendar.MINUTE] = 0
        calendar1[Calendar.SECOND] = 0
        val midNight = calendar1.time
        if (current.after(midNight)) {
            mMainView.background = ContextCompat.getDrawable(
                applicationContext,
                R.drawable.bg_night
            )
            mGreetingTextView.setText(R.string.night_greeting)
            if (current.after(sunrise)) {
                mMainView.background = ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.bg_morning
                )
                mGreetingTextView.setText(R.string.morning_greeting)
                if (current.after(noon)) {
                    mMainView.background = ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.bg_evening
                    )
                    mGreetingTextView.setText(R.string.afternoon_greeting)
                    if (current.after(sunset)) {
                        mMainView.background = ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.bg_night
                        )
                        mGreetingTextView.setText(R.string.night_greeting)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        mMenuItem = menu.findItem(R.id.remove_ads)
        if (nowSeconds > PrefUtil.getRemoveAds(this)) {
            mAdView.visibility = View.VISIBLE
        } else {
            mMenuItem.isVisible = false
            mAdView.visibility = View.GONE
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.settings_layout, SettingsFragment()).commit()
                true
            }
            R.id.remove_ads -> {
                if (this::mRewardedAd.isInitialized && mRewardedAd.isLoaded) {
                    val adCallback = object : RewardedAdCallback() {
                        override fun onRewardedAdOpened() {
                            // Ad opened.
                        }

                        override fun onRewardedAdClosed() {
                            mRewardedAd = createAndLoadRewardedAd()
                        }

                        override fun onUserEarnedReward(
                            p0: com.google.android.gms.ads.rewarded.RewardItem
                        ) {
                            // User earned reward.
                            mMenuItem.isVisible = false
                            mAdView.visibility = View.GONE
                            PrefUtil.setRemoveAds(
                                nowSeconds + 1000 * 60 * 60 * 24,
                                applicationContext
                            )
                            FBToast.infoToast(
                                applicationContext,
                                getString(R.string.RewardMessage),
                                FBToast.LENGTH_SHORT
                            )

                        }

                        override fun onRewardedAdFailedToShow(errorCode: Int) {
                            // Ad failed to display.
                        }
                    }
                    mRewardedAd.show(this, adCallback)
                } else {
                    FBToast.warningToast(
                        applicationContext,
                        "The rewarded ad wasn't loaded yet.",
                        FBToast.LENGTH_SHORT
                    )
                }
                true
            }
            R.id.share -> {
                val sendIntent = Intent().apply {
                    type = "text/plain"
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TITLE, getString(R.string.share_title))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_title))
                    putExtra(Intent.EXTRA_TEMPLATE, getString(R.string.share_link))
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.share_link))

                }
                val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_with))
                startActivity(shareIntent)
                true
            }
            R.id.about -> {
                HtmlAlertDialog(
                    this,
                    R.raw.about,
                    getString(R.string.about_title),
                    android.R.drawable.ic_menu_info_details
                ).show()
                true
            }
            R.id.other -> {
                val otherIntent = Intent(Intent.ACTION_VIEW)
                otherIntent.data = Uri.parse(getString(R.string.other_link))
                startActivity(otherIntent)
                true
            }
            R.id.quit -> {
                // Create out AlterDialog
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.quit_title)
                builder.setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                builder.setMessage(R.string.quit_message)
                builder.setCancelable(true)
                builder.setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { _, _ -> finish() }
                )
                builder.setNegativeButton(
                    R.string.no
                ) { _, _ ->
                    FBToast.successToast(
                        applicationContext,
                        getString(R.string.goingon),
                        FBToast.LENGTH_SHORT
                    )
                }
                builder.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_location -> if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                askForLocationDialog()
            } else {
                locationProvidedDialog()
            }
            else -> {
            }
        }
    }

    /**
     *
     */
    private fun askForLocationDialog() {
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle(R.string.location_dialog_title)
        builder.setMessage(R.string.allow_location_permission)
        builder.setPositiveButton(
            R.string.allow
        ) { _, _ ->
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CHECK_GOOGLE_SETTINGS
            )
        }
        builder.setNegativeButton(
            R.string.dont_allow
        ) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    /**
     *
     */
    private fun locationProvidedDialog() {
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle(R.string.location_on_title)
        builder.setMessage(R.string.location_permission_allowed)
        builder.setPositiveButton(
            R.string.ok
        ) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CHECK_GOOGLE_SETTINGS -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                initialize()
            }
        }
    }

    fun createAndLoadRewardedAd(): RewardedAd {
        val rewardedAd = RewardedAd(this, getString(R.string.pub_reward))
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                // Ad failed to load.
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        return rewardedAd
    }
}