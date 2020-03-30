package core;

import dbmodel.Order_Route;
import org.springframework.data.repository.CrudRepository;

public interface Order_RouteRepo extends CrudRepository<Order_Route,Integer>
{
    Order_Route findByRouteID(int routeID);

}

