package com.kiof.boussole.global.solar

import java.util.*

class SunriseSunsetCalculator {
    /**
     * Returns the location where the sunrise/sunset is calculated for.
     *
     * @return `Location` object representing the location of the computed sunrise/sunset.
     */
    val location: Location? = null
    private var calculator: SolarEventCalculator

    /**
     * Constructs a new `SunriseSunsetCalculator` with the given `Location`
     *
     * @param location           `Location` object containing the Latitude/Longitude of the location to compute
     * the sunrise/sunset for.
     * @param timeZoneIdentifier String identifier for the timezone to compute the sunrise/sunset times in. In the form
     * "America/New_York". Please see the zi directory under the JDK installation for supported
     * time zones.
     */
    constructor(
        location: Location?,
        timeZoneIdentifier: String?
    ) {
        calculator = SolarEventCalculator(location!!, timeZoneIdentifier)
    }

    /**
     * Constructs a new `SunriseSunsetCalculator` with the given `Location`
     *
     * @param location `Location` object containing the Latitude/Longitude of the location to compute
     * the sunrise/sunset for.
     * @param timeZone timezone to compute the sunrise/sunset times in.
     */
    constructor(location: Location?, timeZone: TimeZone?) {
        calculator = SolarEventCalculator(location!!, timeZone!!)
    }

    /**
     * Returns the astronomical (108deg) sunrise for the given date.
     *
     * @param date `Calendar` object containing the date to compute the astronomical sunrise for.
     * @return the astronomical sunrise time in HH:MM (24-hour clock) form.
     */
    fun getAstronomicalSunriseForDate(date: Calendar?): String {
        return calculator.computeSunriseTime(Zenith.ASTRONOMICAL, date!!)
    }

    /**
     * Returns the astronomical (108deg) sunrise for the given date.
     *
     * @param date `Calendar` object containing the date to compute the astronomical sunrise for.
     * @return the astronomical sunrise time as a Calendar
     */
    fun getAstronomicalSunriseCalendarForDate(date: Calendar?): Calendar? {
        return calculator.computeSunriseCalendar(Zenith.ASTRONOMICAL, date!!)
    }

    /**
     * Returns the astronomical (108deg) sunset for the given date.
     *
     * @param date `Calendar` object containing the date to compute the astronomical sunset for.
     * @return the astronomical sunset time in HH:MM (24-hour clock) form.
     */
    fun getAstronomicalSunsetForDate(date: Calendar?): String {
        return calculator.computeSunsetTime(Zenith.ASTRONOMICAL, date!!)
    }

    /**
     * Returns the astronomical (108deg) sunset for the given date.
     *
     * @param date `Calendar` object containing the date to compute the astronomical sunset for.
     * @return the astronomical sunset time as a Calendar
     */
    fun getAstronomicalSunsetCalendarForDate(date: Calendar?): Calendar? {
        return calculator.computeSunsetCalendar(Zenith.ASTRONOMICAL, date!!)
    }

    /**
     * Returns the nautical (102deg) sunrise for the given date.
     *
     * @param date `Calendar` object containing the date to compute the nautical sunrise for.
     * @return the nautical sunrise time in HH:MM (24-hour clock) form.
     */
    fun getNauticalSunriseForDate(date: Calendar?): String {
        return calculator.computeSunriseTime(Zenith.NAUTICAL, date!!)
    }

    /**
     * Returns the nautical (102deg) sunrise for the given date.
     *
     * @param date `Calendar` object containing the date to compute the nautical sunrise for.
     * @return the nautical sunrise time as a Calendar
     */
    fun getNauticalSunriseCalendarForDate(date: Calendar?): Calendar? {
        return calculator.computeSunriseCalendar(Zenith.NAUTICAL, date!!)
    }

    /**
     * Returns the nautical (102deg) sunset for the given date.
     *
     * @param date `Calendar` object containing the date to compute the nautical sunset for.
     * @return the nautical sunset time in HH:MM (24-hour clock) form.
     */
    fun getNauticalSunsetForDate(date: Calendar?): String {
        return calculator.computeSunsetTime(Zenith.NAUTICAL, date!!)
    }

    /**
     * Returns the nautical (102deg) sunset for the given date.
     *
     * @param date `Calendar` object containing the date to compute the nautical sunset for.
     * @return the nautical sunset time as a Calendar
     */
    fun getNauticalSunsetCalendarForDate(date: Calendar?): Calendar? {
        return calculator.computeSunsetCalendar(Zenith.NAUTICAL, date!!)
    }

    /**
     * Returns the civil sunrise (twilight, 96deg) for the given date.
     *
     * @param date `Calendar` object containing the date to compute the civil sunrise for.
     * @return the civil sunrise time in HH:MM (24-hour clock) form.
     */
    fun getCivilSunriseForDate(date: Calendar?): String {
        return calculator.computeSunriseTime(Zenith.CIVIL, date!!)
    }

    /**
     * Returns the civil sunrise (twilight, 96deg) for the given date.
     *
     * @param date `Calendar` object containing the date to compute the civil sunrise for.
     * @return the civil sunrise time as a Calendar
     */
    fun getCivilSunriseCalendarForDate(date: Calendar?): Calendar? {
        return calculator.computeSunriseCalendar(Zenith.CIVIL, date!!)
    }

    /**
     * Returns the civil sunset (twilight, 96deg) for the given date.
     *
     * @param date `Calendar` object containing the date to compute the civil sunset for.
     * @return the civil sunset time in HH:MM (24-hour clock) form.
     */
    fun getCivilSunsetForDate(date: Calendar?): String {
        return calculator.computeSunsetTime(Zenith.CIVIL, date!!)
    }

    /**
     * Returns the civil sunset (twilight, 96deg) for the given date.
     *
     * @param date `Calendar` object containing the date to compute the civil sunset for.
     * @return the civil sunset time as a Calendar
     */
    fun getCivilSunsetCalendarForDate(date: Calendar?): Calendar? {
        return calculator.computeSunsetCalendar(Zenith.CIVIL, date!!)
    }

    /**
     * Returns the official sunrise (90deg 50', 90.8333deg) for the given date.
     *
     * @param date `Calendar` object containing the date to compute the official sunrise for.
     * @return the official sunrise time in HH:MM (24-hour clock) form.
     */
    fun getOfficialSunriseForDate(date: Calendar?): String {
        return calculator.computeSunriseTime(Zenith.OFFICIAL, date!!)
    }

    /**
     * Returns the official sunrise (90deg 50', 90.8333deg) for the given date.
     *
     * @param date `Calendar` object containing the date to compute the official sunrise for.
     * @return the official sunrise time as a Calendar
     */
    fun getOfficialSunriseCalendarForDate(date: Calendar?): Calendar? {
        return calculator.computeSunriseCalendar(Zenith.OFFICIAL, date!!)
    }

    /**
     * Returns the official sunrise (90deg 50', 90.8333deg) for the given date.
     *
     * @param date `Calendar` object containing the date to compute the official sunset for.
     * @return the official sunset time in HH:MM (24-hour clock) form.
     */
    fun getOfficialSunsetForDate(date: Calendar?): String {
        return calculator.computeSunsetTime(Zenith.OFFICIAL, date!!)
    }

    /**
     * Returns the official sunrise (90deg 50', 90.8333deg) for the given date.
     *
     * @param date `Calendar` object containing the date to compute the official sunset for.
     * @return the official sunset time as a Calendar
     */
    fun getOfficialSunsetCalendarForDate(date: Calendar?): Calendar? {
        return calculator.computeSunsetCalendar(Zenith.OFFICIAL, date!!)
    }

    companion object {
        /**
         * Computes the sunrise for an arbitrary declination.
         *
         * @param latitude
         * @param longitude Coordinates for the location to compute the sunrise/sunset for.
         * @param timeZone  timezone to compute the sunrise/sunset times in.
         * @param date      `Calendar` object containing the date to compute the official sunset for.
         * @param degrees   Angle under the horizon for which to compute sunrise. For example, "civil sunrise"
         * corresponds to 6 degrees.
         * @return the requested sunset time as a `Calendar` object.
         */
        fun getSunrise(
            latitude: Double,
            longitude: Double,
            timeZone: TimeZone?,
            date: Calendar?,
            degrees: Double
        ): Calendar? {
            val solarEventCalculator = SolarEventCalculator(
                Location(latitude, longitude),
                timeZone!!
            )
            return solarEventCalculator.computeSunriseCalendar(Zenith(90 - degrees), date!!)
        }

        /**
         * Computes the sunset for an arbitrary declination.
         *
         * @param latitude
         * @param longitude Coordinates for the location to compute the sunrise/sunset for.
         * @param timeZone  timezone to compute the sunrise/sunset times in.
         * @param date      `Calendar` object containing the date to compute the official sunset for.
         * @param degrees   Angle under the horizon for which to compute sunrise. For example, "civil sunset"
         * corresponds to 6 degrees.
         * @return the requested sunset time as a `Calendar` object.
         */
        fun getSunset(
            latitude: Double,
            longitude: Double,
            timeZone: TimeZone?,
            date: Calendar?,
            degrees: Double
        ): Calendar? {
            val solarEventCalculator = SolarEventCalculator(
                Location(latitude, longitude),
                timeZone!!
            )
            return solarEventCalculator.computeSunsetCalendar(Zenith(90 - degrees), date!!)
        }
    }
}