package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTests {

    private final String TEST_NAME = "wheel";
    private final BigDecimal TEST_PRICE = BigDecimal.TEN;
    private final String INVALID_ID = "qwe";

    @Autowired
    private PartRepository partRepository;
    private ModelMapper modelMapper;
    private PartService partService;

    @Before
    public void setupTests() {
        this.modelMapper = new ModelMapper();
        this.partService = new PartServiceImpl(this.partRepository, this.modelMapper);

        PartServiceModel testPart = new PartServiceModel();
        testPart.setName("hood");
        testPart.setPrice(BigDecimal.ONE);

        this.partService.savePart(testPart);
    }

    private void assertAllFieldsAreEqual(PartServiceModel expected, PartServiceModel actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
        Assert.assertEquals(expected.getSupplier(), actual.getSupplier());
    }

    @Test
    public void savePart_partWithValidValues_shouldReturnCorrectPart() {
        PartServiceModel toBeSaved = new PartServiceModel();
        toBeSaved.setPrice(TEST_PRICE);
        toBeSaved.setName(TEST_NAME);

        PartServiceModel expected = this.partService.savePart(toBeSaved);
        PartServiceModel actual = this.modelMapper.map(this.partRepository.findAll().get(1), PartServiceModel.class);

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void savePart_partWithNullName_shouldThrow() {
        PartServiceModel toBeSaved = new PartServiceModel();
        toBeSaved.setPrice(TEST_PRICE);
        toBeSaved.setName(null);

        this.partService.savePart(toBeSaved);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void savePart_partWithNullPrice_shouldThrow() {
        PartServiceModel toBeSaved = new PartServiceModel();
        toBeSaved.setPrice(null);
        toBeSaved.setName(TEST_NAME);

        this.partService.savePart(toBeSaved);
    }

    @Test
    public void editPart_partWithValidValues_shouldReturnCorrectPart() {
        PartServiceModel toBeEdited = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        toBeEdited.setName(TEST_NAME);
        toBeEdited.setPrice(TEST_PRICE);

        PartServiceModel expected = this.partService.editPart(toBeEdited);
        PartServiceModel actual = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editPart_partWithNullPrice_shouldThrow() {
        PartServiceModel toBeEdited = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        toBeEdited.setPrice(null);

        this.partService.editPart(toBeEdited);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editPart_partWithNullName_shouldThrow() {
        PartServiceModel toBeEdited = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        toBeEdited.setName(null);

        this.partService.editPart(toBeEdited);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void editPart_partWithNullId_shouldThrow() {
        PartServiceModel toBeEdited = new PartServiceModel();

        this.partService.editPart(toBeEdited);
    }

    @Test(expected = NullPointerException.class)
    public void editPart_partWithInvalidIdNotPresentInDb_shouldThrow() {
        PartServiceModel toBeEdited = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        toBeEdited.setId(INVALID_ID);

        this.partService.editPart(toBeEdited);
    }

    @Test(expected = NoSuchElementException.class)
    public void deletePart_partWithInvalidIdNotPresentInDb_shouldThrow() {
        this.partService.deletePart(INVALID_ID);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deletePart_partWithNullId_shouldThrow() {
        this.partService.deletePart(null);
    }

    @Test
    public void deletePart_partWithValidId_shouldReturnCorrectPart() {
        PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        PartServiceModel actual = this.partService.deletePart(expected.getId());

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test
    public void findPartById_partWitValidId_shouldReturnCorrectPart() {
        PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
        PartServiceModel actual = this.partService.findPartById(expected.getId());

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findPartById_partWithNullId_shouldThrow() {
        this.partService.findPartById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findPartById_partWithInvalidId_shouldThrow() {
        this.partService.findPartById(INVALID_ID);
    }
}
