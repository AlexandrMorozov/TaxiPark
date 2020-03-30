package core;

import dbmodel.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RouteRepo extends CrudRepository<Route,Integer>
{
    List<Route> findByType(String type);
}
