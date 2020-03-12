package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.CarSaleServiceModel;
import org.softuni.cardealer.domain.models.service.PartSaleServiceModel;
import org.softuni.cardealer.repository.CarSaleRepository;
import org.softuni.cardealer.repository.PartSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SaleServiceTests {

    private final Double TEST_DISCOUNT = 20.5;
    private final Integer TEST_QUANTITY = 500;

    @Autowired
    private CarSaleRepository carSaleRepository;

    @Autowired
    private PartSaleRepository partSaleRepository;

    private ModelMapper modelMapper;
    private SaleService saleService;

    @Before
    public void setupTests() {
        this.modelMapper = new ModelMapper();
        this.saleService = new SaleServiceImpl(this.carSaleRepository, this.partSaleRepository, this.modelMapper);
    }

    @Test
    public void saleCar_carWithValidValues_shouldReturnCorrectCar() {
        CarSaleServiceModel carSale = new CarSaleServiceModel();
        carSale.setDiscount(TEST_DISCOUNT);

        CarSaleServiceModel expected = this.saleService.saleCar(carSale);
        CarSaleServiceModel actual = this.modelMapper.map(this.carSaleRepository.findAll().get(0), CarSaleServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getDiscount(), actual.getDiscount());
        Assert.assertEquals(expected.getCar(), actual.getCar());
        Assert.assertEquals(expected.getCustomer(), actual.getCustomer());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saleCar_carWithNullDiscount_shouldThrow() {
        CarSaleServiceModel carSale = new CarSaleServiceModel();

        this.saleService.saleCar(carSale);
    }

    @Test
    public void salePart_partWithValidValues_shouldReturnCorrectPart() {
        PartSaleServiceModel partSale = new PartSaleServiceModel();
        partSale.setDiscount(TEST_DISCOUNT);
        partSale.setQuantity(TEST_QUANTITY);

        PartSaleServiceModel expected = this.saleService.salePart(partSale);
        PartSaleServiceModel actual = this.modelMapper.map(this.partSaleRepository.findAll().get(0), PartSaleServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getDiscount(), actual.getDiscount());
        Assert.assertEquals(expected.getPart(), actual.getPart());
        Assert.assertEquals(expected.getCustomer(), actual.getCustomer());
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void salePart_partWithNullDiscount_shouldThrow() {
        PartSaleServiceModel partSale = new PartSaleServiceModel();
        partSale.setQuantity(TEST_QUANTITY);

        this.saleService.salePart(partSale);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void salePart_partWithNullQuantity_shouldThrow() {
        PartSaleServiceModel partSale = new PartSaleServiceModel();
        partSale.setDiscount(TEST_DISCOUNT);

        this.saleService.salePart(partSale);
    }
}
