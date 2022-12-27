package andrey.shalya.repositories;

import andrey.shalya.models.Vehicle;

import java.util.List;

public interface VehicleRepository {
    void addVehicle(Vehicle vehicle);
    void updateVehicle(Vehicle vehicle, Long id);
    void deleteVehicle(Long id);
    Vehicle getById(Long id);
    List<Vehicle> getAll();
    List<Vehicle> getPage(String sort, String sortType, int page, int pageSize,
                          List<Long> filterId,
                          List<String> filterName,
                          List<String> filterDate,
                          List<Integer> filterWheels,
                          List<Integer> filterMileage,
                          List<Integer> filterEngine,
                          List<Double> filterFuelCons,
                          List<Double> filterX,
                          List<Long> filterY,
                          List<String> filterVehicleType,
                          List<String> filterFuelType);

    Long getEnginePowerSum();

    long getTotalCount();

    Long getEnginePowerMoreThan(int p);

    List<Vehicle> getAllWithNameLike(String substring);
}
