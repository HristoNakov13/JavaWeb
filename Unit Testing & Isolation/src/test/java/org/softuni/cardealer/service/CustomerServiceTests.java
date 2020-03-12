package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceTests {

    private final String TEST_NAME = "Ivan";
    private final LocalDate TEST_BIRTH_DATE = LocalDate.of(1999, 1, 1);
    private final boolean TEST_IS_YOUNG_DRIVER = true;
    private final String INVALID_ID = "qwe";

    @Autowired
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;
    private CustomerService customerService;

    @Before
    public void setupTests() {
        this.modelMapper = new ModelMapper();
        this.customerService = new CustomerServiceImpl(this.customerRepository, this.modelMapper);

        CustomerServiceModel testCustomer = new CustomerServiceModel();
        testCustomer.setName("Pesho");
        testCustomer.setBirthDate(LocalDate.now());
        testCustomer.setYoungDriver(false);

        this.customerService.saveCustomer(testCustomer);
    }

    private void assertAllFieldsAreEqual(CustomerServiceModel expected, CustomerServiceModel actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getBirthDate(), actual.getBirthDate());
    }

    @Test
    public void saveCustomer_customerWithValidValues_shouldReturnCorrectCustomer() {
        CustomerServiceModel toBeSaved = new CustomerServiceModel();
        toBeSaved.setYoungDriver(TEST_IS_YOUNG_DRIVER);
        toBeSaved.setBirthDate(TEST_BIRTH_DATE);
        toBeSaved.setName(TEST_NAME);

        CustomerServiceModel expected = this.customerService.saveCustomer(toBeSaved);
        CustomerServiceModel actual = this.modelMapper.map(this.customerRepository.findAll().get(1), CustomerServiceModel.class);

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveCustomer_customerWithNullName_shouldThrow() {
        CustomerServiceModel toBeSaved = new CustomerServiceModel();
        toBeSaved.setYoungDriver(TEST_IS_YOUNG_DRIVER);
        toBeSaved.setBirthDate(TEST_BIRTH_DATE);
        toBeSaved.setName(null);

        this.customerService.saveCustomer(toBeSaved);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveCustomer_customerWithNullBirthDate_shouldThrow() {
        CustomerServiceModel toBeSaved = new CustomerServiceModel();
        toBeSaved.setYoungDriver(TEST_IS_YOUNG_DRIVER);
        toBeSaved.setBirthDate(null);
        toBeSaved.setName(TEST_NAME);

        this.customerService.saveCustomer(toBeSaved);
    }


    @Test
    public void editCustomer_customerWithValidValues_shouldReturnCorrectCustomer() {
        CustomerServiceModel toBeEdited = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        toBeEdited.setName(TEST_NAME);
        toBeEdited.setBirthDate(TEST_BIRTH_DATE);
        toBeEdited.setYoungDriver(TEST_IS_YOUNG_DRIVER);

        CustomerServiceModel expected = this.customerService.editCustomer(toBeEdited);
        CustomerServiceModel actual = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void editCustomer_customerWithIdAsNull_shouldThrow() {
        CustomerServiceModel customer = new CustomerServiceModel();

        this.customerService.editCustomer(customer);
    }

    @Test(expected = NullPointerException.class)
    public void editCustomer_customerWithInvalidIdCustomerNotPresentInDb_shouldThrow() {
        CustomerServiceModel toBeEdited = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        toBeEdited.setId(INVALID_ID);

        this.customerService.editCustomer(toBeEdited);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editCustomer_customerWithNullName_shouldThrow() {
        CustomerServiceModel toBeEdited = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        toBeEdited.setName(null);

        this.customerService.editCustomer(toBeEdited);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editCustomer_customerWithNullBirthDate_shouldThrow() {
        CustomerServiceModel toBeEdited = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        toBeEdited.setBirthDate(null);

        this.customerService.editCustomer(toBeEdited);
    }

    @Test
    public void deleteCustomer_customerWithValidValues_shouldReturnCorrectCustomer() {
        CustomerServiceModel expected = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        CustomerServiceModel actual = this.customerService.deleteCustomer(expected.getId());

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test
    public void deleteCustomer_customerWithValidId_shouldRemoveCustomerFromDb() {
        CustomerServiceModel toBeDeleted = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        this.customerService.deleteCustomer(toBeDeleted.getId());

        Assert.assertTrue(this.customerRepository.findAll().isEmpty());
    }


    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteCustomer_customerWithNullId_shouldThrow() {
        String customerId = null;

        this.customerService.deleteCustomer(customerId);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteCustomer_customerWithInvalidIdNotPresentInDb_shouldThrow() {
        this.customerService.deleteCustomer(INVALID_ID);
    }

    @Test
    public void findCustomerById_customerWithValidId_shouldReturnCorrectCustomer() {
        CustomerServiceModel expected = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);
        CustomerServiceModel actual = this.customerService.findCustomerById(expected.getId());

        this.assertAllFieldsAreEqual(expected, actual);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findCustomerById_customerWithNullId_shouldThrow() {
        String customerId = null;

        this.customerService.deleteCustomer(customerId);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findCustomerById_customerWithInvalidIdNotPresentInDb_shouldThrow() {
        this.customerService.deleteCustomer(INVALID_ID);
    }
}
