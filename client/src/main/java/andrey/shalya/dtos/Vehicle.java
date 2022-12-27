package andrey.shalya.dtos;

import andrey.shalya.dtos.Enums.FuelType;
import andrey.shalya.dtos.Enums.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class Vehicle implements Serializable {

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически


    private String name; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null

    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Integer enginePower; //Поле не может быть null, Значение поля должно быть больше 0

    private Double fuelConsumption; //Поле может быть null, Значение поля должно быть больше 0

    private VehicleType type; //Поле может быть null

    private FuelType fuelType;

    private Integer wheelsNumber;

    private Integer mileage;

}
