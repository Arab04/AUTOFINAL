package com.auto.service.searchEngine;



import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.entity.enums.AvtoServiceStatusEnum;
import com.auto.service.entity.enums.DistanceUnitEnum;

import java.util.ArrayList;
import java.util.List;

public class QueryHandler {

    // SINGLETON Implementation ------------------------------------------
    private static QueryHandler INSTANCE = new QueryHandler();
    private QueryHandler() {}
    public static QueryHandler getInstance() {return INSTANCE;}
    // -------------------------------------------------------------------

    /**
     *
     * Checks query inputs and
     * returns a list of food trucks that provides the given query conditions
     *
     * @param statusStr Food Track status in String format
     * @param latitudeStr Latitude of the center in String format
     * @param longitudeStr Longitude of the center in String format
     * @param radiusStr Radius of the circle in String format
     * @param radiusUnitStr Radius unit in String format
     * @return
     */
    public List<ServiceEntityProvider> getFoodTrucksByQuery(
            String statusStr,
            String latitudeStr,
            String longitudeStr,
            String radiusStr,
            String radiusUnitStr)
    {
        // Query Results
        List<ServiceEntityProvider> queryResult = null;

        // Food truck status
        AvtoServiceStatusEnum statusEnum = AvtoServiceStatusEnum.ALL;

        // If status is specified
        if (statusStr != null)
        {
            // Obtain the status
            statusEnum = AvtoServiceStatusEnum.getFromStringValue(statusStr);
        }

        // Update the query results by querying on 'status'
        queryResult = DataAccessor.getInstance().getFoodTrucks(statusEnum);

        System.out.println(statusStr);
        System.out.println(statusEnum.getStrValue());

        // If latitude and longitude is specified
        if (latitudeStr != null && longitudeStr != null && radiusStr != null && radiusUnitStr != null)
        {
            try
            {
                // Obtain the latitude, longitude, radius and radius unit
                double latitude = Double.parseDouble(latitudeStr);
                double longitude = Double.parseDouble(longitudeStr);
                double radius = Double.parseDouble(radiusStr);
                DistanceUnitEnum radiusUnit = DistanceUnitEnum.getFromStringValue(radiusUnitStr);

                // Update the query results
                queryResult =
                        getFoodTrucksInsideCircle(queryResult, latitude, longitude, radius, radiusUnit);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // Return the query result
        return queryResult;
    }

    /**
     * Returns the food trucks that reside in the specified circle.
     * Center of the circle: ['latitude', 'longitude'],
     * Radius of the circle: 'radius', Unit of the radius: 'radiusUnit'
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param foodTrucks List that contains trucks that will be checked
     * @param latitude Latitude of the center of the circle
     * @param longitude Longitude of the center of the circle
     * @param radius Radius of the circle
     * @param radiusUnit Unit of the radius
     * @return The food trucks that reside in the specified circle.
     */
    public List<ServiceEntityProvider>getFoodTrucksInsideCircle(
            List<ServiceEntityProvider> foodTrucks,
            double latitude,
            double longitude,
            double radius,
            DistanceUnitEnum radiusUnit)
    {
        synchronized (DataAccessor.class)
        {
            // Initialize the result list
            List<ServiceEntityProvider> foodTrucksInsideCircle = new ArrayList<ServiceEntityProvider>();

            // Traverse through the food trucks
            for (ServiceEntityProvider foodTruck:foodTrucks)
            {
                // Calculate the distance between current food truck and the center of the circle
                double distance =
                        GeodesicDistanceCalculator.getInstance().distance(
                                foodTruck.getLat(), foodTruck.getLan(),
                                latitude, longitude, radiusUnit);

                // If distance is smaller than the radius,
                if (distance < radius)
                {
                    // Then it is in the circle, add it to the result list.
                    foodTrucksInsideCircle.add(foodTruck);
                }
            }

            // Return the resulting list.
            return foodTrucksInsideCircle;
        }
    }
}
