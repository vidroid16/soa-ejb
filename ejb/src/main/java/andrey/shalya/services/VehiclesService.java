package andrey.shalya.services;


import andrey.shalya.dtos.TotalCountDto;
import andrey.shalya.dtos.VehicleDto;
import andrey.shalya.dtos.VehicleToModifyDto;

import javax.ejb.Remote;
import java.util.List;


public interface VehiclesService {
    void addVehicle(VehicleToModifyDto dto);
    void updateVehicle(VehicleToModifyDto dto, Long id);
    TotalCountDto getAllVehicles();

    TotalCountDto getVehicles(String sort, String sortType, int page, int pageSize,
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
    void deleteVehicle(Long id);
    VehicleDto getVehicleById(Long id);

    Long getEnginePowerSum();

    Long getEnginePowerMoreThan(int p);

    TotalCountDto getAllWithNameLike(String substring);
}
