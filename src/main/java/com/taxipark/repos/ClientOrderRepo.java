package com.taxipark.repos;

import com.taxipark.dbmodel.ClientOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientOrderRepo extends CrudRepository<ClientOrder,Integer>
{
    /*o.dateOfOrder=:orderDate AND WHERE o.orderType=:typeOrder*/
   /* @Query("SELECT new com.taxipark.dbmodel.ClientOrder(o.serviceID,o.dateOfOrder,o.timeOfOrder) FROM ClientOrder o " +
            "WHERE dateOfOrder=:orderDate AND o.orderType=:typeOrder AND o.status=:status ORDER BY o.timeOfOrder ASC")
    List<ClientOrder> findTimesOfAllActiveOrdersByDay(@Param("orderDate")String orderDate, @Param("typeOrder")String orderType, @Param("status")String status);
*/
    @Query("SELECT new com.taxipark.dbmodel.ClientOrder(o.orderedService,o.dateOfOrder,o.timeOfOrder) FROM ClientOrder o " +
            "WHERE dateOfOrder=:orderDate AND o.orderType=:typeOrder AND o.status=:status ORDER BY o.timeOfOrder ASC")
    List<ClientOrder> findTimesOfAllActiveOrdersByDay(@Param("orderDate")String orderDate, @Param("typeOrder")String orderType, @Param("status")String status);



    /*

    SELECT orderID ,serviceName ,dateOfOrder, timeOfOrder, cost FROM client_order
INNER JOIN services ON services.servicesID=client_order.serviceID
INNER JOIN order_route ON client_order.routeID=order_route.routeID
WHERE client_order.clientID=15 AND client_order.status='active'
ORDER BY dateOfOrder DESC,timeOfOrder ASC

    */
   /* @Query("SELECT new com.taxipark.dto.FullOrderInfoDto(c.orderID,s.serviceName,c.dateOfOrder,c.timeOfOrder,c.cost) FROM Services s " +
            "INNER JOIN ClientOrder c " + "ON c.serviceID=s.servicesID WHERE c.clientID=:client AND c.status=:orderStatus " +
            "ORDER BY c.dateOfOrder DESC, c.timeOfOrder ASC")
    List<FullOrderInfoDto> findFullOrderInfo(@Param("client") int clientID, @Param("orderStatus") String orderStatus);*/

    List<ClientOrder> findByClientIDAndStatus(int clientID,String status);

    ClientOrder findByOrderID(int orderID);




    //

   /* @Query("SELECT new com.taxipark.dto.StatisticsDto(co.dateOfOrder,cs.serviceName,COUNT(co.dateOfOrder))" +
            " FROM Services cs INNER JOIN ClientOrder co ON co.serviceID=cs.servicesID WHERE " +
            "cs.serviceName=:serviceN  GROUP BY co.dateOfOrder")
    List<StatisticsDto> getNumberOfServiceOrdersPerDate(@Param("serviceN") String serviceName);

    @Query("SELECT new com.taxipark.dto.StatisticsDto(co.dateOfOrder,cs.serviceName,SUM(co.cost))" +
            " FROM Services cs INNER JOIN ClientOrder co ON co.serviceID=cs.servicesID WHERE " +
            "cs.serviceName=:serviceN  GROUP BY co.dateOfOrder")
    List<StatisticsDto> getValueOfServiceOrdersPerDate(@Param("serviceN") String serviceName);
*/



}
