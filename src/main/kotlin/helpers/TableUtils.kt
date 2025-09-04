package org.example.helpers

import de.m3y.kformat.Table
import de.m3y.kformat.table
import org.example.CITIES
import org.example.HUMIDITY
import org.example.MAX_TEMPERATURE
import org.example.MIN_TEMPERATURE
import org.example.WIND_DIRECTION
import org.example.WIND_SPEED
import org.example.models.ForecastResponse

// builds and returns the table with data for all cities
fun buildTable(results: List<ForecastResponse?>) : Table {
    val table = table {
        header(CITIES, MIN_TEMPERATURE, MAX_TEMPERATURE, HUMIDITY, WIND_SPEED, WIND_DIRECTION)

        // options for formatting and designing the table
        hints {
            defaultAlignment = Table.Hints.Alignment.CENTER
            precision(MIN_TEMPERATURE, 2)
            precision(MAX_TEMPERATURE, 2)
            precision(WIND_SPEED, 2)
            borderStyle = Table.BorderStyle.SINGLE_LINE
        }
    }

    // adding rows for table
    for(result in results) {
        if(result != null) {
            val resultDay = result.forecastData.forecastDayData[0].day
            val resultHour = result.forecastData.forecastDayData[0].hour[0]
            table.row(result.location.name, resultDay.minTemperature, resultDay.maxTemperature,
                resultHour.humidityPercentage, resultHour.windSpeed, resultHour.windDirection)
        }
    }
    return table
}