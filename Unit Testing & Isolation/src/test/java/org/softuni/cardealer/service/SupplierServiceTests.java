package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
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
public class SupplierServiceTests {

    private final String TEST_NAME = "Pesho";
    private final boolean TEST_IS_IMPORTER = true;

    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private SupplierService supplierService;

    @Before
    public void setupTests() {
        this.modelMapper = new ModelMapper();
        this.supplierService = new SupplierServiceImpl(this.supplierRepository, this.modelMapper);

        SupplierServiceModel testSupplier = new SupplierServiceModel();
        testSupplier.setName(TEST_NAME);
        testSupplier.setImporter(TEST_IS_IMPORTER);

        this.supplierService.saveSupplier(testSupplier);
    }

    @Test
    public void saveSupplier_savingSupplierWithValidValues_ShouldReturnCorrectSupplier() {
        SupplierServiceModel toBeSaved = new SupplierServiceModel();
        toBeSaved.setName("Ivan");
        toBeSaved.setImporter(true);

        SupplierServiceModel expected = this.supplierService.saveSupplier(toBeSaved);
        SupplierServiceModel actual = this.modelMapper.map(this.supplierRepository.findAll().get(1), SupplierServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.isImporter(), actual.isImporter());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveSupplier_savingSupplierWithNullName_shouldThrow() {
        SupplierServiceModel toBeSaved = new SupplierServiceModel();
        toBeSaved.setName(null);

        this.supplierService.saveSupplier(toBeSaved);
    }

    @Test
    public void editSupplier_editWithValidValues_ShouldReturnCorrectSupplier() {
        SupplierServiceModel toBeEdited = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        toBeEdited.setName(TEST_NAME + "a");
        toBeEdited.setImporter(!toBeEdited.isImporter());

        SupplierServiceModel expected = this.supplierService.editSupplier(toBeEdited);
        SupplierServiceModel actual = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.isImporter(), actual.isImporter());
    }

    @Test(expected = NullPointerException.class)
    public void editSupplier_supplierObjectAsNull_shouldThrow() {
        SupplierServiceModel toBeEdited = null;

        this.supplierService.editSupplier(null);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editSupplier_supplierWithNullName_shouldThrow() {
        SupplierServiceModel toBeEdited = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        toBeEdited.setName(null);

        this.supplierService.editSupplier(toBeEdited);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void editSupplier_supplierWithNullIdNotPresentInDatabase_shouldThrow() {
        SupplierServiceModel freshSupplier = new SupplierServiceModel();

        this.supplierService.editSupplier(freshSupplier);
    }

    @Test(expected = NullPointerException.class)
    public void editSupplier_supplierWithInvalidIdNotPresentInDatabase_shouldThrow() {
        SupplierServiceModel freshSupplier = new SupplierServiceModel();
        freshSupplier.setId("asj");

        this.supplierService.editSupplier(freshSupplier);
    }

    @Test
    public void deleteSupplier_deleteWithValidSupplierId_shouldReturnCorrectSupplier() {
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        SupplierServiceModel actual = this.supplierService.deleteSupplier(expected.getId());

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.isImporter(), actual.isImporter());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void deleteSupplier_deleteWithValidSupplierId_supplierShouldNotBePresentInDatabase() {
        SupplierServiceModel supplier = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        this.supplierService.deleteSupplier(supplier.getId());

        SupplierServiceModel fetchingDeleted = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteSupplier_deleteWithInvalidId_shouldThrow() {
        this.supplierService.deleteSupplier("qwe");
    }

    @Test
    public void findSupplierById_findWithValidId_shouldReturnCorrectSupplier() {
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);
        SupplierServiceModel actual = this.supplierService.findSupplierById(expected.getId());


        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.isImporter(), actual.isImporter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findSupplierById_findWithInvalidId_shouldThrow() {
        SupplierServiceModel invalid = this.supplierService.findSupplierById("qwe");
    }
}
