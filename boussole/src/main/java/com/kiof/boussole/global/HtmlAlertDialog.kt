package com.kiof.boussole.global

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import com.kiof.boussole.R
import java.io.IOException
import java.io.InputStreamReader

class HtmlAlertDialog(
    context: Context, resourceId: Int, title: String,
    iconId: Int
) : AlertDialog(context) {

    private fun loadRawResourceString(
        resources: Resources,
        resourceId: Int
    ): String {
        val builder = StringBuilder()
        val `is` = resources.openRawResource(resourceId)
        val reader = InputStreamReader(`is`)
        val buf = CharArray(1024)
        var numRead = 0
        try {
            while (reader.read(buf).also { numRead = it } != -1) {
                builder.append(buf, 0, numRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return builder.toString()
    }

    init {
        val webView = WebView(context)
        webView.setBackgroundColor(0) // transparent
        val htmlString = loadRawResourceString(
            context.resources, resourceId
        )
//        webView.loadData(htmlString, "text/html", "UTF-8")
        webView.loadDataWithBaseURL("", htmlString, "text/html", "UTF-8", "")
//        webView.settings.useWideViewPort = true
//        webView.settings.loadWithOverviewMode = true
        if (title != "") this.setTitle(title)
        if (iconId != 0) this.setIcon(iconId)
        this.setView(webView)
        this.setButton(
            DialogInterface.BUTTON_POSITIVE,
            context.resources.getString(R.string.Ok)
        ) { dialog, id -> dialog.cancel() }
    }
}