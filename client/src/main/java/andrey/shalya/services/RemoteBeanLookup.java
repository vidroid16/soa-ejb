package andrey.shalya.services;

import andrey.shalya.dtos.TotalCountDto;
import andrey.shalya.dtos.VehicleDto;
import andrey.shalya.dtos.VehicleToModifyDto;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.Properties;

public class RemoteBeanLookup {

    public static VehiclesService lookupRemoteStatelessBean() {
        try {
            InitialContext ctx = new InitialContext();
            return (VehiclesService) ctx.lookup("java:global/ejbs-jar-with-dependencies/VehiclesService!andrey.shalya.services.VehiclesService");
        } catch (NamingException e) {
            System.out.println("не получилось (");
            e.printStackTrace();
            return new VehiclesService() {
                @Override
                public void addVehicle(VehicleToModifyDto dto) {

                }

                @Override
                public void updateVehicle(VehicleToModifyDto dto, Long id) {

                }

                @Override
                public TotalCountDto getAllVehicles() {
                    return null;
                }

                @Override
                public TotalCountDto getVehicles(String sort, String sortType, int page, int pageSize, List<Long> filterId, List<String> filterName, List<String> filterDate, List<Integer> filterWheels, List<Integer> filterMileage, List<Integer> filterEngine, List<Double> filterFuelCons, List<Double> filterX, List<Long> filterY, List<String> filterVehicleType, List<String> filterFuelType) {
                    return null;
                }

                @Override
                public void deleteVehicle(Long id) {

                }

                @Override
                public VehicleDto getVehicleById(Long id) {
                    return null;
                }

                @Override
                public Long getEnginePowerSum() {
                    return null;
                }

                @Override
                public Long getEnginePowerMoreThan(int p) {
                    return null;
                }

                @Override
                public TotalCountDto getAllWithNameLike(String substring) {
                    return null;
                }
            };
        }
    }
}
