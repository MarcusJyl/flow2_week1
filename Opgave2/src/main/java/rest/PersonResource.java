package rest;

import DTO.PersonDTO;
import DTO.PersonsDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import exceptinos.PersonNotFoundException;
import exceptinos.PersonNotFoundExceptionMapper;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        PersonsDTO allpersons = FACADE.getAllPersons();
        return GSON.toJson(allpersons);
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getJokeById(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO joke = FACADE.getPerson(id);
        return GSON.toJson(joke);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(String person) {
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        PersonDTO pNew = FACADE.addPerson(p.getfName(), p.getlName(), p.getPhone(), p.getStreet(), p.getZip(), p.getCity());
        
        return GSON.toJson(pNew);
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO person = FACADE.deletePerson(id);
        return GSON.toJson(person);
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public String editPerson(String person, @PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        p.setId(id);
        PersonDTO pEdited = FACADE.editPerson(p);
        return GSON.toJson(pEdited);
    }
}
