package org.adaschool.tdd.repository.document;

public class GeoLocation
{
    private final double lat;

    private final double lng;

    public GeoLocation( double lat, double lng )
    {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat()
    {
        return lat;
    }

    public double getLng()
    {
        return lng;
    }

    public double getDistanceFromLatLngInKm(GeoLocation targetLocation) {
        double earthRadiusInKm = 6371;
        double resultLatInRadians = degreesToRadians(targetLocation.getLat() - this.lat);
        double resultLngInRadians = degreesToRadians(targetLocation.getLng() - this.lng);
        double a =
                Math.sin(resultLatInRadians/2) * Math.sin(resultLngInRadians/2) +
                Math.cos(degreesToRadians(this.lat)) * Math.cos(degreesToRadians(targetLocation.getLat())) *
                Math.sin(resultLngInRadians/2) * Math.sin(resultLngInRadians/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return  earthRadiusInKm * c;
    }

    private double degreesToRadians(double degrees) {
        return degrees * (Math.PI/180);
    }
}
