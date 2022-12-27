package andrey.shalya.services;

import andrey.shalya.dtos.TotalCountDto;
import andrey.shalya.dtos.VehicleDto;
import andrey.shalya.dtos.VehicleToModifyDto;
import andrey.shalya.mappers.VehicleMapper;
import andrey.shalya.models.Vehicle;
import andrey.shalya.repositories.VehicleRepository;
import andrey.shalya.repositories.impl.VehicleRepositoryImpl;
import andrey.shalya.utils.Sortfields;
import jakarta.inject.Inject;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.validation.ValidationException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Remote(VehiclesService.class)
@Dependent
public class VehiclesServiceImpl implements VehiclesService, Serializable {
    public VehiclesServiceImpl(){
        this.vehicleRepository = new VehicleRepositoryImpl();
    }

    private final VehicleRepository vehicleRepository;

    @Override
    public void addVehicle(VehicleToModifyDto dto) throws ValidationException {
        Vehicle vehicle = VehicleMapper.modifiedDtoToVehicle(dto);
        vehicleRepository.addVehicle(vehicle);
    }

    @Override
    public void updateVehicle(VehicleToModifyDto dto, Long id) {
        Vehicle vehicle = VehicleMapper.modifiedDtoToVehicle(dto);
        vehicleRepository.updateVehicle(vehicle,id);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteVehicle(id);
    }

    @Override
    public VehicleDto getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.getById(id);
        return VehicleMapper.vehicleToDto(vehicle);
    }

    @Override
    public TotalCountDto getAllVehicles() {
        List<Vehicle> vehicleList = vehicleRepository.getAll();
        List<VehicleDto> vehicleDtoList = new ArrayList<>();

        vehicleList.forEach(v -> vehicleDtoList.add(VehicleMapper.vehicleToDto(v)));

        TotalCountDto res = new TotalCountDto();
        res.setVehicles(vehicleDtoList);
        res.setTotalCount(vehicleRepository.getTotalCount());
        return res;
    }

    @Override
    public TotalCountDto getVehicles(String sort, String sortType, int page, int pageSize,
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
                                        List<String> filterFuelType) {
        String sortfield = Sortfields.ID.fieldname;
        try {
            if(sort.equals("coordinates")){
                sortfield = "coordinates";
            }else {
                sortfield = Sortfields.ID.getByFieldname(sort).fieldname;
            }
        }catch (Exception e){
            sortfield = Sortfields.ID.fieldname;
        }

        if(sortType==null){
            sortType = "asc";
        }else {
            if(!sortType.equals("asc") && !sortType.equals("desc")){
                sortType = "asc";
            }
        }

        List<Vehicle> vehicleList = vehicleRepository.getPage(sortfield,sortType,page,pageSize,filterId,filterName,
                filterDate,filterWheels,filterMileage,filterEngine,filterFuelCons,filterX,filterY,filterVehicleType,
                filterFuelType);
        List<VehicleDto> vehicleDtoList = new ArrayList<>();

        vehicleList.forEach(v -> vehicleDtoList.add(VehicleMapper.vehicleToDto(v)));

        TotalCountDto res = new TotalCountDto();
        res.setVehicles(vehicleDtoList);
        res.setTotalCount(vehicleRepository.getTotalCount());
        return res;
    }

    @Override
    public Long getEnginePowerSum() {
        return vehicleRepository.getEnginePowerSum();
    }

    @Override
    public Long getEnginePowerMoreThan(int p) {
        return vehicleRepository.getEnginePowerMoreThan(p);
    }

    @Override
    public TotalCountDto getAllWithNameLike(String substring) {
        List<Vehicle> vehicleList = vehicleRepository.getAllWithNameLike(substring);
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();

        vehicleList.forEach(v->vehicleDtos.add(VehicleMapper.vehicleToDto(v)));

        TotalCountDto res = new TotalCountDto();
        res.setVehicles(vehicleDtos);
        res.setTotalCount(vehicleRepository.getTotalCount());

        return res;
    }
}
