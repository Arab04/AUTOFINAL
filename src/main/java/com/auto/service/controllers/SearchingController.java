package com.auto.service.controllers;

import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.searchEngine.DataAccessor;
import com.auto.service.searchEngine.QueryHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value= "/food-trucks")
public class SearchingController {


    @GetMapping(
            value = "/getData",
            produces = "application/json")
    public List<ServiceEntityProvider> getFoodTrucks()
    {
        return DataAccessor.getInstance().getAllFoodTrucks();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/json" media type.
     *
     * @return Food Trucks in JSON format
     */
    @GetMapping(
            value = "/query",
            produces = "application/json")
    public List<ServiceEntityProvider> getFoodTrucksByQuery(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude,
            @RequestParam(value = "radius", required = false) String radius,
            @RequestParam(value = "radius_unit", required = false) String radius_unit)
    {
        return QueryHandler.getInstance().getFoodTrucksByQuery(
                status, latitude, longitude, radius, radius_unit);
    }
}
