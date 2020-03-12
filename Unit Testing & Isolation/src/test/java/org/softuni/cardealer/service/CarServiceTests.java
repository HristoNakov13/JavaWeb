package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTests {

    private final String TEST_MAKE = "Opel";
    private final String TEST_MODEL = "a5";
    private final long TEST_TRAVEL_DISTANCE = 500L;
    private final String INVALID_ID = "qwe";

    @Autowired
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private CarService carService;

    @Before
    public void testSetup() {
        this.modelMapper = new ModelMapper();
        this.carService = new CarServiceImpl(this.carRepository, this.modelMapper);

        CarServiceModel testCar = new CarServiceModel();
        testCar.setMake("Mercedes");
        testCar.setModel("Benz");
        testCar.setTravelledDistance(100L);

        this.carService.saveCar(testCar);
    }

    private void assertAllFieldsAreEqual(CarServiceModel expected, CarServiceModel actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getMake(), actual.getMake());
        Assert.assertEquals(expected.getModel(), actual.getModel());
        Assert.assertEquals(expected.getTravelledDistance(), actual.getTravelledDistance());
        Assert.assertEquals(expected.getParts(), actual.getParts());
    }

    @Test
    public void saveCar_savingWithValidValues_shouldReturnCorrectCar() {
        CarServiceModel toBeSaved = new CarServiceModel();
        toBeSaved.setModel(TEST_MODEL);
        toBeSaved.setMake(TEST_MAKE);
        toBeSaved.setTravelledDistance(TEST_TRAVEL_DISTANCE);

        CarServiceModel expected = this.carService.saveCar(toBeSaved);
        CarServiceModel actual = this.modelMapper.map(this.carRepository.findAll().get(1), CarServiceModel.class);

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveCar_savingWithNullModel_shouldThrow() {
        CarServiceModel toBeSaved = new CarServiceModel();
        toBeSaved.setModel(null);
        toBeSaved.setMake(TEST_MAKE);
        toBeSaved.setTravelledDistance(TEST_TRAVEL_DISTANCE);

        this.carService.saveCar(toBeSaved);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveCar_savingWithNullMake_shouldThrow() {
        CarServiceModel toBeSaved = new CarServiceModel();
        toBeSaved.setModel(TEST_MODEL);
        toBeSaved.setMake(null);
        toBeSaved.setTravelledDistance(TEST_TRAVEL_DISTANCE);

        this.carService.saveCar(toBeSaved);
    }

    @Test(expected = NullPointerException.class)
    public void editCar_editingCarObjectAsNull_shouldThrow() {
        CarServiceModel car = null;

        this.carService.editCar(car);
    }

    @Test(expected = NullPointerException.class)
    public void editCar_editingCarWithInvalidIdNotPresentInDb_shouldThrow() {
        CarServiceModel toBeEdited = new CarServiceModel();
        toBeEdited.setId(INVALID_ID);

        this.carService.editCar(toBeEdited);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editCar_editingCarWithNullId_shouldThrow() {
        CarServiceModel toBeEdited = new CarServiceModel();

        this.carService.saveCar(toBeEdited);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editCar_editingCarWithNullModel_shouldThrow() {
        CarServiceModel tobeEdited = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        tobeEdited.setModel(null);

        this.carService.editCar(tobeEdited);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editCar_editingCarWithNullMake_shouldThrow() {
        CarServiceModel tobeEdited = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        tobeEdited.setMake(null);

        this.carService.editCar(tobeEdited);
    }

    @Test
    public void editCar_editingCarWithValidValues_shouldReturnCorrectCar() {
        CarServiceModel tobeEdited = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        tobeEdited.setMake(TEST_MAKE + "a");
        tobeEdited.setModel(TEST_MODEL + "a");
        tobeEdited.setTravelledDistance(TEST_TRAVEL_DISTANCE + 1L);

        CarServiceModel expected = this.carService.editCar(tobeEdited);
        CarServiceModel actual = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteCar_deleteWithNullId_shouldThrow() {
        String carId = null;

        this.carService.deleteCar(carId);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteCar_deleteWithInvalidIdNotPresentInDb_shouldThrow() {
        this.carService.deleteCar(INVALID_ID);
    }

    @Test
    public void deleteCar_deleteWithValidId_shouldReturnCorrectCar() {
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        CarServiceModel actual = this.carService.deleteCar(expected.getId());

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test
    public void deleteCar_deleteWithValidId_shouldRemoveCarFromDb() {
        CarServiceModel toDelete = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        this.carService.deleteCar(toDelete.getId());

        Assert.assertTrue(this.carRepository.findAll().isEmpty());
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findCarById_findWithNullId_shouldThrow() {
        String carId = null;

        this.carService.findCarById(carId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCarById_findWithInvalidIdNotPresentInDb_shouldThrow() {
        this.carService.findCarById(INVALID_ID);
    }

    @Test
    public void findCarById_findWithValidId_shouldReturnCorrectCar() {
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);
        CarServiceModel actual = this.carService.findCarById(expected.getId());

        this.assertAllFieldsAreEqual(expected, actual);
    }
}