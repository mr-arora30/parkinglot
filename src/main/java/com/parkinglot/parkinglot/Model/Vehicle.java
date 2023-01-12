package com.parkinglot.parkinglot.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.parkinglot.parkinglot.Enums.VehicleSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;




@Data
public class Vehicle {
    @NotBlank(message = "LicenseNo is Mandatory")
    @JsonProperty("licenseNo")
    private String licenseNo;
    @NotNull(message = "Vehicle size is Missing")
    @JsonProperty("size")
    private VehicleSize size;

}
