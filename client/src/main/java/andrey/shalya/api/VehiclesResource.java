package andrey.shalya.api;

import andrey.shalya.dtos.TotalCountDto;
import andrey.shalya.dtos.VehicleDto;
import andrey.shalya.dtos.VehicleToModifyDto;
import andrey.shalya.services.MyEjb;
import andrey.shalya.services.RemoteBeanLookup;
import andrey.shalya.services.VehiclesService;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.List;

@ApplicationScoped
@Path("/vehicles")
public class VehiclesResource {
    VehiclesService vehiclesService;

    public VehiclesResource(){
        this.vehiclesService = RemoteBeanLookup.lookupRemoteStatelessBean();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addVehicle(VehicleToModifyDto dto){
        try{
            InitialContext ctx = new InitialContext();
            VehiclesService mes = (VehiclesService) ctx.lookup("java:global/ejbs-jar-with-dependencies/VehiclesService!andrey.shalya.services.VehiclesService");
            mes.addVehicle(dto);
        }catch (ValidationException | EJBException e){
            e.printStackTrace();
            return Response.status(403).entity("Непраильный vehicle!").build();
        }
        catch (NullPointerException | IllegalArgumentException e ){
            return Response.status(403).entity("Непраильный vehicle!").build();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/jndi")
    public String jndi(VehicleToModifyDto dto){
        final InitialContext context;
        try {
            context = new InitialContext();
            InitialContext ctx = new InitialContext();
            NamingEnumeration<NameClassPair> list = ctx.list("");
            while (list.hasMore()) {
                System.out.println(list.next().getName());
            }

        } catch (NamingException e) {
            return "NAMING EXEP1";
        }

        try {
            MyEjb myEjb1 = (MyEjb) context.lookup("java:global/ejbs-jar-with-dependencies/MyEjb!andrey.shalya.services.MyEjb");

            return myEjb1.test("MAMA");
        } catch (NamingException e) {
            e.printStackTrace();
            return "NAMING EXEP 2";
        }
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllVehicles(){
//        try {
//           List<VehicleDto> dtos = vehiclesService.getAllVehicles();
//            return Response.ok().entity(dtos).build();
//        }catch (ValidationException e){
//            e.printStackTrace();
//            return Response.status(403).entity("Непраильный vehicle!").build();
//        }
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicles(@QueryParam("page") int page,
                                @QueryParam("page-size") int pageSize,
                                @QueryParam("sort") String sort,
                                @QueryParam("sort-type") String sortType,
                                @QueryParam("f-id") List<Long> filterId,
                                @QueryParam("f-name") List<String> filterName,
                                @QueryParam("f-date") List<String> filterDate,
                                @QueryParam("f-wheels-number") List<Integer> filterWheels,
                                @QueryParam("f-mileage") List<Integer> filterMileage,
                                @QueryParam("f-engine-power") List<Integer> filterEngine,
                                @QueryParam("f-fuel-consumption") List<Double> filterFuelCons,
                                @QueryParam("f-x") List<Double> filterX,
                                @QueryParam("f-y") List<Long> filterY,
                                @QueryParam("f-vehicle-type") List<String> filterVehicleType,
                                @QueryParam("f-fuel-type") List<String> filterFuelType
                                ){
        try {
            TotalCountDto dtos = vehiclesService.getVehicles(sort,sortType,page,pageSize,filterId,filterName,
                    filterDate,filterWheels,filterMileage,filterEngine,filterFuelCons,filterX,filterY,filterVehicleType,
                    filterFuelType);
            return Response.status(200).entity(dtos).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(522).build();
        }
    }

    @GET
    @Path("/{vehicle-id}")
    public Response getVehicleById(@PathParam("vehicle-id") Long id){
        try {
            VehicleDto dto = vehiclesService.getVehicleById(id);
            return Response.ok().entity(dto).build();
        }catch (EJBException e){
            e.printStackTrace();
            return Response.status(404).entity("Машины с таким id не найдено!").build();
        }
    }

    @PUT
    @Path("/{vehicle-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateVehicle(@PathParam("vehicle-id") Long id, VehicleToModifyDto dto){
        try {
            vehiclesService.updateVehicle(dto,id);
        }catch (ValidationException | EJBException e){
            e.printStackTrace();
            return Response.status(403).entity("Непраильный vehicle!").build();
        }catch (NotFoundException e){
            return Response.status(404).entity(e.getMessage()).build();
        }catch (NullPointerException | IllegalArgumentException e ){
            return Response.status(403).entity("Непраильный vehicle!").build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{vehicle-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteVehicle(@PathParam("vehicle-id") Long id){
        try {
            vehiclesService.deleteVehicle(id);
        }catch (IllegalArgumentException | EJBException e){
            e.printStackTrace();
            return Response.status(404).entity("Нет такого vehicle!").build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/engine-power/sum")
    public Response getEnginePowerSum(){
        try {
            Long ans = vehiclesService.getEnginePowerSum();
            return Response.ok().entity(ans).build();
        }catch (ValidationException e){
            e.printStackTrace();
            return Response.status(403).entity("Непраильный vehicle!").build();
        }
    }

    @POST
    @Path("/engine-power/more/{engine-power}")
    public Response getEnginePowerMoreThan(@PathParam("engine-power") int enginePower){
        try {
            Long ans = vehiclesService.getEnginePowerMoreThan(enginePower);
            return Response.ok().entity(ans).build();
        }catch (ValidationException e){
            e.printStackTrace();
            return Response.status(403).entity("Непраильный vehicle!").build();
        }
    }

    @POST
    @Path("/name/substring/{substring}")
    public Response getNameLike(@PathParam("substring") String substr){
        try {
            TotalCountDto dtos = vehiclesService.getAllWithNameLike(substr);
            return Response.ok().entity(dtos).build();
        }catch (ValidationException e){
            e.printStackTrace();
            return Response.status(403).entity("Непраильный vehicle!").build();
        }
    }
}
