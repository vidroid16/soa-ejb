package andrey.shalya.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto implements Serializable {
    Long id;
    String creationDate;
    String name;
    Integer wheelsNumber;
    Integer mileage;
    Integer enginePower;
    Double fuelConsumption;
    CoordinatesDto coordinates;
    String vehicleType;
    String fuelType;
}
