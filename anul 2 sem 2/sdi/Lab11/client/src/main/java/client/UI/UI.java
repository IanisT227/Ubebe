package client.UI;

import client.service.RentalServiceImpl;
import core.domain.Car;
import core.domain.Fueling;
import core.domain.GasStation;
import core.domain.RentalFirm;
import core.dtos.CarDto;
import core.dtos.FuelingDto;
import core.dtos.GasStationDto;
import core.dtos.RentalFirmDto;
import core.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class UI {

    @Autowired
    private RentalService clientService;
    private Map<Integer, Runnable> menu;

    public UI() {
        this.menu = new HashMap<>();
    }
    int readInt(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        return scanner.nextInt();
    }
    String readString(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        return scanner.nextLine();
    }


    static void printMenu()
    {
        System.out.println("MENU");
        System.out.println("1. Get all Cars");
        System.out.println("2. Add a car");
        System.out.println("3. Update a car");
        System.out.println("4. Delete a car");
        System.out.println("----------------");
        System.out.println("5. Get all Rental Firms");
        System.out.println("6. Add a Rental Firm");
        System.out.println("7. Update a Rental Firm");
        System.out.println("8. Delete a Rental Firm");
        System.out.println("----------------");
        System.out.println("9. Get all Fuelings");
        System.out.println("10. Add a Fueling");
        System.out.println("11. Update a Fueling");
        System.out.println("12. Delete a Fueling");
        System.out.println("----------------");
        System.out.println("13. Get all Gas Stations");
        System.out.println("14. Add a Gas Station");
        System.out.println("15. Update a Gas Station");
        System.out.println("16. Delete a Gas Station");
        System.out.println("17. Find Gas Station by Name");
        System.out.println("----------------");
        System.out.println("0. Exit");
    }

    private void showCars()
    {
        clientService.findAllCars().whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void showRentalFirms()
    {
        clientService.findAllRentalFirms().whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void showFuelings()
    {
        clientService.findAllFueling().whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void showGasStations()
    {
        clientService.findAllGasStation().whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void addCar()
    {
        String brand = readString("Brand:");
        Integer rentalfirmid = readInt("Rental Firm ID:");
        CarDto car = new CarDto(brand, rentalfirmid);

        this.clientService.addCar(car).whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void addRentalFirm()
    {
        String name = readString("Name:");
        RentalFirmDto rentalFirm = new RentalFirmDto(name);

        this.clientService.addRentalFirm(rentalFirm).whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void addGasStation()
    {
        String name = readString("Name:");
        GasStationDto gasStation = new GasStationDto(name);

        this.clientService.addGasStation(gasStation).whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void addFueling()
    {
        Integer carid = readInt("Car ID:");
        Integer gasstationid = readInt("Gas Station ID:");
        FuelingDto fueling = new FuelingDto(carid, gasstationid);

        this.clientService.addFueling(fueling).whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }


    private void updateCar()
    {
        Integer id = readInt("ID:");
        String brand = readString("Brand:");
        Integer rentalfirmid = readInt("Rental Firm ID:");
        CarDto car = new CarDto(brand, rentalfirmid);

        car.setId(id);

        this.clientService.updateCar(car).whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void updateRentalFirm()
    {
        Integer id = readInt("ID:");
        String name = readString("Name:");
        RentalFirmDto rentalFirm = new RentalFirmDto(name);
        rentalFirm.setId(id);

        this.clientService.updateRentalFirm(rentalFirm).whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

    private void updateGasStation()
    {
        Integer id = readInt("ID:");
        String name = readString("Name:");
        GasStationDto gasStation = new GasStationDto(name);
        gasStation.setId(id);

        this.clientService.updateGasStation(gasStation).whenComplete((res, err)-> {
            if (err == null)
            {
                System.out.println(res);
            }
            else
            {
                System.out.println(err.getMessage());
            }
        });
    }

//    private void updateFueling()
//    {
//        Integer id = readInt("ID:");
//        Integer carid = readInt("Car ID:");
//        Integer gasstationid = readInt("Gas Station ID:");
//        FuelingDto fueling = new FuelingDto(carid, gasstationid);
//        fueling.setId(id);
//
//        this.clientService.updateFueling(fueling).whenComplete((res, err)-> {
//            if (err == null)
//            {
//                System.out.println(res);
//            }
//            else
//            {
//                System.out.println(err.getMessage());
//            }
//        });
//    }


    private void deleteCar() {
        Integer id = readInt("ID:");

        this.clientService.deleteCar(id).whenComplete((res, err) -> {
            if (err == null) {
                System.out.println(res);
            } else {
                System.out.println(err.getMessage());
            }
        });
    }

    private void deleteRentalFirm() {
        Integer id = readInt("ID:");

        this.clientService.deleteRentalFirm(id).whenComplete((res, err) -> {
            if (err == null) {
                System.out.println(res);
            } else {
                System.out.println(err.getMessage());
            }
        });
    }
//    private void deleteFueling() {
//        Integer id = readInt("ID:");
//
//        this.clientService.deleteFueling(id).whenComplete((res, err) -> {
//            if (err == null) {
//                System.out.println(res);
//            } else {
//                System.out.println(err.getMessage());
//            }
//        });
//    }
    private void deleteGasStation() {
        Integer id = readInt("ID:");

        this.clientService.deleteGasStation(id).whenComplete((res, err) -> {
            if (err == null) {
                System.out.println(res);
            } else {
                System.out.println(err.getMessage());
            }
        });
    }

    private void findGasStationByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name: ");
        String name = scanner.next();

        clientService.findGasStationByName(name).whenComplete((result, exception) -> {
            if (exception == null) {
                System.out.println("Found: " + result);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });
    }

    public void startUI()
    {
        menu.put(1, this::showCars);
        menu.put(2, this::addCar);
        menu.put(3, this::updateCar);
        menu.put(4, this::deleteCar);
        menu.put(5, this::showRentalFirms);
        menu.put(6, this::addRentalFirm);
        menu.put(7, this::updateRentalFirm);
        menu.put(8, this::deleteRentalFirm);
        menu.put(9, this::showFuelings);
        menu.put(10, this::addFueling);
//        menu.put(11, this::updateFueling);
//        menu.put(12, this::deleteFueling);
        menu.put(13, this::showGasStations);
        menu.put(14, this::addGasStation);
        menu.put(15, this::updateGasStation);
        menu.put(16, this::deleteGasStation);
        menu.put(17, this::findGasStationByName);

        while (true)
        {
            printMenu();
            try
            {
                Scanner myInput = new Scanner( System.in );
                int option = myInput.nextInt();
                if(option==0)
                    break;
                Runnable function = menu.get(option);
                if (function == null)
                {
                    System.out.println("Invalid option!");
                    continue;
                }
                function.run();

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
