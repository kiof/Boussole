package com.kiof.boussole.global.solar

import java.math.BigDecimal

class Zenith(degrees: Double) {
    private val degrees: BigDecimal = BigDecimal.valueOf(degrees)
    fun degrees(): BigDecimal {
        return degrees
    }

    companion object {
        /**
         * Astronomical sunrise/set is when the sun is 18 degrees below the horizon.
         */
        val ASTRONOMICAL = Zenith(108.toDouble())

        /**
         * Nautical sunrise/set is when the sun is 12 degrees below the horizon.
         */
        val NAUTICAL = Zenith(102.toDouble())

        /**
         * Civil sunrise/set (dawn/dusk) is when the sun is 6 degrees below the horizon.
         */
        val CIVIL = Zenith(96.toDouble())

        /**
         * Official sunrise/set is when the sun is 50' below the horizon.
         */
        val OFFICIAL = Zenith(90.8333.toDouble())
    }

}