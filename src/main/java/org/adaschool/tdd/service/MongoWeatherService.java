package org.adaschool.tdd.service;

import org.adaschool.tdd.controller.weather.dto.WeatherReportDto;
import org.adaschool.tdd.exception.WeatherReportNotFoundException;
import org.adaschool.tdd.repository.WeatherReportRepository;
import org.adaschool.tdd.repository.document.GeoLocation;
import org.adaschool.tdd.repository.document.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MongoWeatherService
    implements WeatherService
{

    private final WeatherReportRepository repository;

    public MongoWeatherService( @Autowired WeatherReportRepository repository )
    {
        this.repository = repository;
    }

    @Override
    public WeatherReport report( WeatherReportDto weatherReportDto )
    {
        return repository.save(new WeatherReport(weatherReportDto));
    }

    @Override
    public WeatherReport findById( String id )
    {
        WeatherReport weatherReport = repository.findById(id).orElse(null);
        if( weatherReport != null ) {
            return weatherReport;
        }
        throw new WeatherReportNotFoundException();
    }

    @Override
    public List<WeatherReport> findNearLocation( GeoLocation geoLocation, float distanceRangeInMeters )
    {
        List<WeatherReport> reports = repository.findAll();
        List<WeatherReport> nearReports = new ArrayList<>();
        for (WeatherReport report : reports) {
            if (distanceRangeInMeters >= report.getGeoLocation().getDistanceFromLatLngInKm(geoLocation)) {
                nearReports.add(report);
            }
        }

        return nearReports;
    }

    @Override
    public List<WeatherReport> findWeatherReportsByName( String reporter )
    {
        return repository.findByReporter(reporter);
    }
}
