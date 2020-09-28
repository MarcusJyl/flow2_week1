package facades;

import DTO.PersonDTO;
import DTO.PersonsDTO;
import entities.Address;
import utils.EMF_Creator;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Jens", "Jensen", "12345678");
        p1.setAddress(new Address("Lyngby hovedgade 2", "2800", "Lyngby"));
        p2 = new Person("Jønke", "Larsen", "87654321");
        p2.setAddress(new Address("Jærgersborg vej 21", "2800", "Lyngby"));
        p3 = new Person("Hans", "Madssen", "14789632");
        p3.setAddress(new Address("Bagsværd hovedgade 51", "2880", "Bagsværd"));
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetPerson() throws Exception {
        System.out.println("Test get Person");
        int id = p1.getId();
        PersonDTO expResult = new PersonDTO(p1);
        PersonDTO result = facade.getPerson(id);
        assertThat(result, samePropertyValuesAs(expResult));
    }

}
