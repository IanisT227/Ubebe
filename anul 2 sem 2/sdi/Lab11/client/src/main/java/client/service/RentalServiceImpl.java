package client.service;

//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import core.domain.GasStation;
import core.dtos.*;
import core.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@Service
public class RentalServiceImpl implements RentalService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExecutorService executorService;

//    @Autowired
//    XmlMapper xmlMapper;



    private static String BASE_URL = "http://localhost:8080/api/";

    @Override
    public CompletableFuture<Iterable<CarDto>> findAllCars() {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "cars";
            System.out.println("HELLO HERE");
            var result = restTemplate.exchange(url, HttpMethod.GET,
                    null, CarsDto.class);

            return result.getBody().getCars();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> addCar(CarDto car) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "cars";
            var result = restTemplate.postForObject(url, car, String.class);

            return result;
        }, executorService);
    }

    @Override
    public CompletableFuture<String> deleteCar(Integer id) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "cars";
            restTemplate.delete(url + "/{id}", id);

            return "Deleted";
        }, executorService);
    }

    @Override
    public CompletableFuture<String> updateCar(CarDto car) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "cars";
            restTemplate.put(url, car);

            return "Updated";
        }, executorService);
    }

    @Override
    public CompletableFuture<String> addGasStation(GasStationDto gasstation) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "gasstations";
            var result = restTemplate.postForObject(url, gasstation, String.class);

            return result;
        }, executorService);
    }

    @Override
    public CompletableFuture<Iterable<GasStationDto>> findAllGasStation() {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "gasstations";
            var result = restTemplate.exchange(url, HttpMethod.GET,
                    null, GasStationsDto.class);

            return result.getBody().getGasStations();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> deleteGasStation(Integer id) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "gasstations";
            restTemplate.delete(url + "/{id}", id);

            return "Deleted";
        }, executorService);
    }

    @Override
    public CompletableFuture<String> updateGasStation(GasStationDto gasstation) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "gasstations";
            restTemplate.put(url, gasstation);

            return "Updated";
        }, executorService);
    }

    @Override
    public CompletableFuture<String> addRentalFirm(RentalFirmDto rentalfirm) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "rentalfirms";
            var result = restTemplate.postForObject(url, rentalfirm, String.class);

            return result;
        }, executorService);
    }

    @Override
    public CompletableFuture<Iterable<RentalFirmDto>> findAllRentalFirms() {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "rentalfirms";
            var result = restTemplate.exchange(url, HttpMethod.GET,
                    null, RentalFirmsDto.class);

            return result.getBody().getRentalFirms();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> deleteRentalFirm(Integer id) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "rentalfirms";
            restTemplate.delete(url + "/{id}", id);

            return "Deleted";
        }, executorService);
    }

    @Override
    public CompletableFuture<String> updateRentalFirm(RentalFirmDto rentalfirm) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "rentalfirms";
            restTemplate.put(url, rentalfirm);

            return "Updated";
        }, executorService);
    }

    @Override
    public CompletableFuture<String> addFueling(FuelingDto fuelingDto) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "fuelings";
            var result = restTemplate.postForObject(url, fuelingDto, String.class);

            return result;
        }, executorService);
    }

    @Override
    public CompletableFuture<Iterable<FuelingDto>> findAllFueling() {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "fuelings";
            var result = restTemplate.exchange(url, HttpMethod.GET,
                    null, FuelingsDto.class);

            return result.getBody().getFuelings();
        }, executorService);
    }

    @Override
    public CompletableFuture<String> deleteFueling(Integer carId, Integer gasstationId) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "fuelings";
            restTemplate.delete(url + "/{carId}/{gasstationId}", carId, gasstationId);

            return "Deleted";
        }, executorService);
    }

    @Override
    public CompletableFuture<GasStationDto> findGasStationByName(String name) {
        return CompletableFuture.supplyAsync(() -> {
            String url = BASE_URL + "gasstations/find_by_name";

            var result = restTemplate.getForObject(url + "/{name}", GasStationDto.class, name);

            return result;
        });
    }

//    @Override
//    public CompletableFuture<CarDto> findCarByName(String name) {
//        return CompletableFuture.supplyAsync(() -> {
//            String url = BASE_URL + "cars/find_by_name";
//
//            var result = restTemplate.getForObject(url + "/{name}", CarDto.class, name);
//
//            return result;
//        });
//    }
//
//    @Override
//    public CompletableFuture<Iterable<ArticleDto>> findArticlesByCarName(String carName) {
//        return CompletableFuture.supplyAsync(() -> {
//            String url = BASE_URL + "articles";
//            var result = restTemplate.getForObject(
//                    url + "/{carName}", ArticlesDto.class, carName);
//
//            return result.getArticles();
//        }, executorService);
//    }
}