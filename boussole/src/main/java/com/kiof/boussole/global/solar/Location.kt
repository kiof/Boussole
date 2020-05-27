package com.kiof.boussole.global.solar

import java.math.BigDecimal

class Location {
    /**
     * @return the latitude
     */
    var latitude: BigDecimal
        private set

    /**
     * @return the longitude
     */
    var longitude: BigDecimal
        private set

    /**
     * Creates a new instance of `Location` with the given parameters.
     *
     * @param latitude  the latitude, in degrees, of this location. North latitude is positive, south negative.
     * @param longitude the longitude, in degrees of this location. East longitude is positive, west negative.
     */
    constructor(latitude: String?, longitude: String?) {
        this.latitude = BigDecimal(latitude)
        this.longitude = BigDecimal(longitude)
    }

    /**
     * Creates a new instance of `Location` with the given parameters.
     *
     * @param latitude  the latitude, in degrees, of this location. North latitude is positive, south negative.
     * @param longitude the longitude, in degrees, of this location. East longitude is positive, east negative.
     */
    constructor(latitude: Double, longitude: Double) {
        this.latitude = BigDecimal(latitude)
        this.longitude = BigDecimal(longitude)
    }

}