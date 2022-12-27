package andrey.shalya.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleToModifyDto implements Serializable {
    String name;
    Integer wheelsNumber;
    Integer mileage;
    Integer enginePower;
    Double fuelConsumption;
    CoordinatesDto coordinates;
    String vehicleType;
    String fuelType;
}
