package andrey.shalya.repositories.impl;

import andrey.shalya.models.Coordinates;
import andrey.shalya.models.Enums.FuelType;
import andrey.shalya.models.Enums.VehicleType;
import andrey.shalya.models.Vehicle;
import andrey.shalya.repositories.VehicleRepository;
import jakarta.validation.ValidationException;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class VehicleRepositoryImpl implements VehicleRepository {

    public VehicleRepositoryImpl(){
        jakarta.persistence.EntityManagerFactory entityManagerFactory = jakarta.persistence.Persistence.
                createEntityManagerFactory("spring");
        em = entityManagerFactory.createEntityManager();
    }

    private final EntityManager em;

    @Override
    public void addVehicle(Vehicle vehicle) {
        try {
            em.getTransaction().begin();
            em.persist(vehicle);
            em.getTransaction().commit();
        }catch (ValidationException e){
            em.getTransaction().rollback();
            throw e;
        }

    }

    @Override
    public void updateVehicle(Vehicle vehicle, Long id) {
        try {
            em.getTransaction().begin();
            Vehicle oldVehicle = em.find(Vehicle.class, id);
            Coordinates oldCoordinates = em.find(Coordinates.class, oldVehicle.getCoordinates().getId());

            oldVehicle.setName(vehicle.getName());
            oldVehicle.setWheelsNumber(vehicle.getWheelsNumber());
            oldVehicle.setType(vehicle.getType());
            oldVehicle.setFuelType(vehicle.getFuelType());
            oldVehicle.setMileage(vehicle.getMileage());
            oldVehicle.setFuelConsumption(vehicle.getFuelConsumption());
            oldVehicle.setEnginePower(vehicle.getEnginePower());

            oldCoordinates.setX(vehicle.getCoordinates().getX());
            oldCoordinates.setY(vehicle.getCoordinates().getY());
            em.getTransaction().commit();
        } catch (NullPointerException e) {
            em.getTransaction().rollback();
            throw new NotFoundException("Машины не найдено");
        }
    }

    @Override
    public void deleteVehicle(Long id) {
        em.getTransaction().begin();
        Vehicle vehicle = em.find(Vehicle.class, id);
        em.remove(vehicle);
        em.getTransaction().commit();
    }

    @Override
    public Vehicle getById(Long id) {
        return em.find(Vehicle.class, id);
    }

    @Override
    public List<Vehicle> getAll() {
        return em.createQuery("SELECT v FROM Vehicle v ", Vehicle.class).getResultList();
    }

    @Override
    public long getTotalCount() {
        return (long) em.createNativeQuery("SELECT COUNT(*) FROM vehicles").getSingleResult();
    }

    @Override
    public List<Vehicle> getPage(String sort, String sortType, int page, int pageSize,
                                 List<Long> filterId,
                                 List<String> filterName,
                                 List<String> filterDate,
                                 List<Integer> filterWheels,
                                 List<Integer> filterMileage,
                                 List<Integer> filterEngine,
                                 List<Double> filterFuelCons,
                                 List<Double> filterX,
                                 List<Long> filterY,
                                 List<String> filterVehicleType,
                                 List<String> filterFuelType) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Vehicle> criteriaQuery = criteriaBuilder.createQuery(Vehicle.class);
        Root<Vehicle> vehicleRoot = criteriaQuery.from(Vehicle.class);
        ArrayList<Predicate> andPredicate = new ArrayList<>();
        Join<Vehicle, Coordinates> coordinatesJoin = vehicleRoot.join("coordinates");


        if (!filterId.isEmpty()) {
            ArrayList<Predicate> predicateId = new ArrayList<>();
            filterId.forEach(p -> {
                predicateId.add(criteriaBuilder.equal(vehicleRoot.get("id"), p));
            });
            Predicate[] predicateIdArr = new Predicate[predicateId.size()];
            Predicate idPredicateFinal = criteriaBuilder.or(predicateId.toArray(predicateIdArr));
            andPredicate.add(idPredicateFinal);
        }
        if (!filterName.isEmpty()) {
            ArrayList<Predicate> predicateName = new ArrayList<>();
            filterName.forEach(p -> {
                predicateName.add(criteriaBuilder.equal(vehicleRoot.get("name"), p));
            });
            Predicate[] predicateNameArr = new Predicate[predicateName.size()];
            Predicate namePredicateFinal = criteriaBuilder.or(predicateName.toArray(predicateNameArr));
            andPredicate.add(namePredicateFinal);
        }
        if (!filterDate.isEmpty()) {
            ArrayList<Predicate> predicateDate = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            filterDate.forEach(p -> {
                try {
                    predicateDate.add(criteriaBuilder.equal(vehicleRoot.get("creationDate"), LocalDateTime.parse(p)));
                } catch (Exception e) {
                    predicateDate.add(criteriaBuilder.or());
                }
            });
            Predicate[] predicateDateArr = new Predicate[predicateDate.size()];
            Predicate datePredicateFinal = criteriaBuilder.or(predicateDate.toArray(predicateDateArr));
            andPredicate.add(datePredicateFinal);
        }
        if (!filterWheels.isEmpty()) {
            ArrayList<Predicate> predicateWheels = new ArrayList<>();
            filterWheels.forEach(p -> {
                predicateWheels.add(criteriaBuilder.equal(vehicleRoot.get("wheelsNumber"), p));
            });
            Predicate[] predicateWheelsArr = new Predicate[predicateWheels.size()];
            Predicate wheelsPredicateFinal = criteriaBuilder.or(predicateWheels.toArray(predicateWheelsArr));
            andPredicate.add(wheelsPredicateFinal);
        }
        if (!filterMileage.isEmpty()) {
            ArrayList<Predicate> predicateMileage = new ArrayList<>();
            filterMileage.forEach(p -> {
                predicateMileage.add(criteriaBuilder.equal(vehicleRoot.get("mileage"), p));
            });
            Predicate[] predicateMileageArr = new Predicate[predicateMileage.size()];
            Predicate mileagePredicateFinal = criteriaBuilder.or(predicateMileage.toArray(predicateMileageArr));
            andPredicate.add(mileagePredicateFinal);
        }
        if (!filterEngine.isEmpty()) {
            ArrayList<Predicate> predicateEngine = new ArrayList<>();
            filterEngine.forEach(p -> {
                predicateEngine.add(criteriaBuilder.equal(vehicleRoot.get("enginePower"), p));
            });
            Predicate[] predicateEngineArr = new Predicate[predicateEngine.size()];
            Predicate enginePredicateFinal = criteriaBuilder.or(predicateEngine.toArray(predicateEngineArr));
            andPredicate.add(enginePredicateFinal);
        }
        if (!filterFuelCons.isEmpty()) {
            ArrayList<Predicate> predicateFuelCons = new ArrayList<>();
            filterFuelCons.forEach(p -> {
                predicateFuelCons.add(criteriaBuilder.equal(vehicleRoot.get("fuelConsumption"), p));
            });
            Predicate[] predicateFuelConsArr = new Predicate[predicateFuelCons.size()];
            Predicate fuelConsPredicateFinal = criteriaBuilder.or(predicateFuelCons.toArray(predicateFuelConsArr));
            andPredicate.add(fuelConsPredicateFinal);
        }
        if (!filterVehicleType.isEmpty()) {
            ArrayList<Predicate> predicateVehicleType = new ArrayList<>();
            filterVehicleType.forEach(p -> {
                try {
                    predicateVehicleType.add(criteriaBuilder.equal(vehicleRoot.get("type"), VehicleType.valueOf(p)));
                } catch (Exception e) {
                    predicateVehicleType.add(criteriaBuilder.or());
                }
            });
            Predicate[] predicateVehicleTypeArr = new Predicate[predicateVehicleType.size()];
            Predicate vehicleTypePredicateFinal = criteriaBuilder.or(predicateVehicleType.toArray(predicateVehicleTypeArr));
            andPredicate.add(vehicleTypePredicateFinal);
        }
        if (!filterFuelType.isEmpty()) {
            ArrayList<Predicate> predicateFuelType = new ArrayList<>();
            filterFuelType.forEach(p -> {
                try {
                    predicateFuelType.add(criteriaBuilder.equal(vehicleRoot.get("fuelType"), FuelType.valueOf(p)));
                } catch (Exception e) {
                    predicateFuelType.add(criteriaBuilder.or());
                }
            });
            Predicate[] predicateFuelTypeArr = new Predicate[predicateFuelType.size()];
            Predicate fuelTypePredicateFinal = criteriaBuilder.or(predicateFuelType.toArray(predicateFuelTypeArr));
            andPredicate.add(fuelTypePredicateFinal);
        }
        if (!filterY.isEmpty()) {
            ArrayList<Predicate> predicateY = new ArrayList<>();
            filterY.forEach(p -> {
                try {
                    predicateY.add(criteriaBuilder.equal(coordinatesJoin.get("y"), p));
                } catch (Exception e) {
                    predicateY.add(criteriaBuilder.or());
                }
            });
            Predicate[] predicateYArr = new Predicate[predicateY.size()];
            Predicate yPredicateFinal = criteriaBuilder.or(predicateY.toArray(predicateYArr));
            andPredicate.add(yPredicateFinal);
        }
        if (!filterX.isEmpty()) {
            ArrayList<Predicate> predicateX = new ArrayList<>();
            filterX.forEach(p -> {
                try {
                    predicateX.add(criteriaBuilder.equal(coordinatesJoin.get("x"), p));
                } catch (Exception e) {
                    predicateX.add(criteriaBuilder.or());
                }
            });
            Predicate[] predicateXArr = new Predicate[predicateX.size()];
            Predicate xPredicateFinal = criteriaBuilder.or(predicateX.toArray(predicateXArr));
            andPredicate.add(xPredicateFinal);
        }

        Predicate[] andPredicateArr = new Predicate[andPredicate.size()];
        Predicate andPredicateFinal = criteriaBuilder.and(andPredicate.toArray(andPredicateArr));

        criteriaQuery.where(andPredicateFinal);

        if (sortType.equals("asc")) {
            if(sort.equals("coordinates")){
                criteriaQuery.orderBy(criteriaBuilder.asc(coordinatesJoin.get("x")));
            }
            else {
                criteriaQuery.orderBy(criteriaBuilder.asc(vehicleRoot.get(sort)));
            }
        } else {
            if(sort.equals("coordinates.x")){
                criteriaQuery.orderBy(criteriaBuilder.desc(coordinatesJoin.get("x")));
            }
            else {
                criteriaQuery.orderBy(criteriaBuilder.desc(vehicleRoot.get(sort)));
            }
        }


        TypedQuery<Vehicle> vehicleTypedQuery = em.createQuery(criteriaQuery).
                setFirstResult(page * pageSize).
                setMaxResults(pageSize);
        List<Vehicle> res = vehicleTypedQuery.getResultList();

        String qlog = vehicleTypedQuery.unwrap(EJBQueryImpl.class).getDatabaseQuery().getSQLString();

        return res;
    }

    @Override
    public Long getEnginePowerSum() {
        Object o = em.createQuery("select SUM(v.enginePower) from Vehicle v").getSingleResult();
        return (Long) o;
    }

    @Override
    public Long getEnginePowerMoreThan(int p) {
        Query query = em.createQuery("select COUNT(v.enginePower) from Vehicle v where v.enginePower>:p");
        query.setParameter("p", p);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Vehicle> getAllWithNameLike(String substring) {
        TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v where v.name like " +
                "'%" + substring + "%'", Vehicle.class);
        String sss = query.toString();
        return query.getResultList();
    }
}
