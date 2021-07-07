package com.auto.service.searchEngine;



import com.auto.service.entity.ServiceEntityProvider;
import com.auto.service.entity.enums.AvtoServiceStatusEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataAccessor {

    // Attributes --------------------------------------------------------
    private static HashMap<UUID, ServiceEntityProvider> foodTruckMap;
    private static HashMap<AvtoServiceStatusEnum, List<ServiceEntityProvider>> statusMap;
    // -------------------------------------------------------------------

    // SINGLETON Implementation ------------------------------------------
    private static DataAccessor INSTANCE = new DataAccessor();
    private DataAccessor() {initialize();}
    public static DataAccessor getInstance() {return INSTANCE;}
    // -------------------------------------------------------------------

    /**
     * Initializes the mappings
     */
    private static void initialize()
    {
        foodTruckMap = new HashMap<UUID, ServiceEntityProvider>();
        statusMap = new HashMap<AvtoServiceStatusEnum, List<ServiceEntityProvider>>();
    }

    /**
     * Returns all the food trucks in the storage
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @return All the food trucks in the storage
     */
    public List<ServiceEntityProvider> getAllFoodTrucks()
    {
        synchronized (DataAccessor.class)
        {
            return new ArrayList<ServiceEntityProvider>(foodTruckMap.values());
        }
    }

    /**
     * Returns the food trucks that have the specified status.
     * If the specified status is ALL, then all food trucks are returned.
     * If the specified status is NO_STATEMENT, then an empty list is returned.
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param status Food Truck Status for querying
     * @return The food trucks which have the specified status
     */
    public List<ServiceEntityProvider>getFoodTrucks(AvtoServiceStatusEnum status)
    {
        synchronized (DataAccessor.class)
        {
            if (status == AvtoServiceStatusEnum.ALL)
            {
                return getAllFoodTrucks();
            }
            else if (status == AvtoServiceStatusEnum.NO_STATEMENT)
            {
                return new ArrayList<ServiceEntityProvider>();
            }
            else
            {
                return statusMap.get(status);
            }
        }
    }

    /**
     * Returns true if a food truck with given id exist, false otherwise
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param id Object id of a food truck
     * @return True if a food truck with given id exist, false otherwise
     */
    public boolean foodTruckExist(long id)
    {
        synchronized (DataAccessor.class)
        {
            return foodTruckMap.containsKey(id);
        }
    }

    /**
     * Returns the food truck with the given id
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param id Object id of a food truck
     * @return The food truck with the given id
     */
    public ServiceEntityProvider getFoodTruck(long id)
    {
        synchronized (DataAccessor.class)
        {
            return foodTruckMap.get(id);
        }
    }

    /**
     * Adds the given food truck to the storage
     * Also populates the status map
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param foodTruck new food truck
     */
    public void addFoodTruck(ServiceEntityProvider foodTruck)
    {
        synchronized (DataAccessor.class)
        {
            foodTruckMap.put(foodTruck.getId(), foodTruck);
            addToStatusMap(foodTruck);
        }
    }

    /**
     * Updates the given food truck.
     * If there no food truck with the object id of the given food truck, then does nothing.
     * Also update the status map if the status is changed.
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param foodTruck updated food truck
     */
    public void updateFoodTruck(ServiceEntityProvider foodTruck)
    {
        synchronized (DataAccessor.class)
        {
            // Update the status map first.
            updateStatusChange(foodTruck);
            foodTruckMap.put(foodTruck.getId(), foodTruck);
        }
    }

    /**
     * Removes and returns the food truck with the given id.
     * Also updates the status map
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param id
     * @return The removed food truck
     */
    public ServiceEntityProvider removeFoodTruck(long id)
    {
        synchronized (DataAccessor.class)
        {
            removeFromStatusMap(foodTruckMap.get(id));
            return foodTruckMap.remove(id);
        }
    }

    /**
     * Adds the given food truck to the related list in the status map.
     * Example: If the status of the given food truck is 'REQUESTED';
     * then, the given truck is added to the list of the 'REQUESTED' map item.
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param foodTruck new food truck
     */
    private void addToStatusMap(ServiceEntityProvider foodTruck)
    {
        synchronized (DataAccessor.class)
        {
            AvtoServiceStatusEnum status = foodTruck.getStatusEnum();
            List<ServiceEntityProvider> foodTrucks;

            // If an existing entry will be updated.
            if (statusMap.containsKey(status))
            {
                foodTrucks = statusMap.get(status);
            }
            // If a new entry is created.
            else
            {
                foodTrucks = new ArrayList<ServiceEntityProvider>();
                statusMap.put(status, foodTrucks);
            }

            foodTrucks.add(foodTruck);
        }
    }

    /**
     * Updates the status map if the status of the given food truck is changed.
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param updatedFoodTruck Updated food truck
     */
    private void updateStatusChange(ServiceEntityProvider updatedFoodTruck)
    {
        synchronized (DataAccessor.class)
        {
            // Continue only if the given food truck already exist
            if (foodTruckMap.containsKey(updatedFoodTruck.getId()))
            {
                ServiceEntityProvider existingFoodTruck = foodTruckMap.get(updatedFoodTruck.getId());

                // Check if the status is changed.
                if (existingFoodTruck.getStatusEnum() != updatedFoodTruck.getStatusEnum())
                {
                    // Remove the food truck from the map of previous status
                    removeFromStatusMap(existingFoodTruck);
                    // Add the food truck to the map of current status
                    addToStatusMap(updatedFoodTruck);
                }
            }
        }
    }

    /**
     * Updates the status map by removing the given food truack from the related status map item.
     * Example: If the status of the given food truck is 'REQUESTED';
     * then, the given truck is from the list of the 'REQUESTED' map item.
     * This method is synchronized on this class (Manipulation on storage elements is prevented.)
     *
     * @param foodTruck Removed food truck
     */
    private void removeFromStatusMap(ServiceEntityProvider foodTruck)
    {
        synchronized (DataAccessor.class)
        {
            // Continue only if the given food truck already exist
            if (foodTruckMap.containsKey(foodTruck.getId()))
            {
                if (statusMap.containsKey(foodTruck.getStatusEnum()))
                {
                    statusMap.get(foodTruck.getStatusEnum()).remove(foodTruck);
                }
            }
        }
    }

}
