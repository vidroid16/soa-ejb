package andrey.shalya.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TotalCountDto implements Serializable {
    List<VehicleDto> vehicles;
    long totalCount;
}
