package core.service;

import core.domain.Car;
import core.domain.Fueling;
import core.domain.GasStation;
import core.domain.RentalFirm;
import core.dtos.CarDto;
import core.dtos.FuelingDto;
import core.dtos.GasStationDto;
import core.dtos.RentalFirmDto;

import java.util.concurrent.CompletableFuture;

public interface RentalService {
    int PORT = 1234;
    String HOST  = "localhost";
    String GETCARS = "findAllCars";
    String ADDCAR = "addCar";
    String UPDATECAR = "updateCar";
    String DELETECAR = "deleteCar";
    String GETRENTALFIRMS = "findAllRentalFirms";
    String ADDRENTALFIRM = "addRentalFirm";
    String UPDATERENTALFIRM = "updateRentalFirm";
    String DELETERENTALFIRM = "deleteRentalFirm";
    String GETFUELINGS = "findAllFuelings";
    String ADDFUELING = "addRentalFueling";
    String UPDATEFUELING = "updateFueling";
    String DELETEFUELING = "deleteFueling";
    String GETGASSTATIONS = "findAllGasStations";
    String ADDGASSTATION = "addGasStation";
    String UPDATEGASSTATION = "updateGasStation";
    String DELETEGASSTATION = "deleteGasStation";

    CompletableFuture<Iterable<CarDto>> findAllCars();

    CompletableFuture<String> addCar(CarDto car);

    CompletableFuture<String> updateCar(CarDto car);

    CompletableFuture<String> deleteCar(Integer id);

    CompletableFuture<Iterable<RentalFirmDto>> findAllRentalFirms();

    CompletableFuture<String> addRentalFirm(RentalFirmDto rentalFirm);

    CompletableFuture<String> updateRentalFirm(RentalFirmDto rentalFirm);

    CompletableFuture<String> deleteRentalFirm(Integer id);

    CompletableFuture<Iterable<FuelingDto>> findAllFueling();

    CompletableFuture<String> addFueling(FuelingDto fueling);

    CompletableFuture<String> deleteFueling(Integer carid, Integer gasstationid);

    CompletableFuture<Iterable<GasStationDto>> findAllGasStation();

    CompletableFuture<String> addGasStation(GasStationDto gasStation);

    CompletableFuture<String> updateGasStation(GasStationDto gasStation);

    CompletableFuture<String> deleteGasStation(Integer id);

    CompletableFuture<GasStationDto> findGasStationByName(String name);

}
