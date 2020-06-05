package com.taxipark.repos;

import com.taxipark.dbmodel.ClientOrder;
import com.taxipark.dto.StatisticsDto;
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
   //Find all active orders of selected type for the selected day
    @Query("SELECT new com.taxipark.dbmodel.ClientOrder(o.orderedService,o.dateOfOrder,o.timeOfOrder) FROM ClientOrder o " +
            "WHERE dateOfOrder=:orderDate AND o.orderType=:typeOrder AND o.status=:status ORDER BY o.timeOfOrder ASC")
    List<ClientOrder> findTimesOfAllActiveOrdersByDay(@Param("orderDate")String orderDate,
                                                      @Param("typeOrder")String orderType, @Param("status")String status);

    //Find number of all orders of selected type for current week, that belongs to the selected authorized client
   @Query("SELECT COUNT(o.clientID) FROM ClientOrder o WHERE " +
            "o.clientID=:clientID AND o.orderType=:orderType AND o.dateOfOrder BETWEEN :frm AND :to")
   int findNumOfClientOrdersPerWeek(@Param("clientID") int clientID, @Param("orderType") String type,
                                    @Param("frm") String from, @Param("to") String to );

    //Find number of all orders of selected type for current week, that belongs to the selected unauthorized client
   @Query("SELECT COUNT(o.unauthContactInfo) FROM ClientOrder o WHERE " +
           "o.unauthContactInfo=:unauthInfo AND o.orderType=:orderType AND o.dateOfOrder BETWEEN :frm AND :to")
   int findNumOfClientOrdersPerWeek(@Param("unauthInfo") String unauthInfo, @Param("orderType") String type,
                                    @Param("frm") String from, @Param("to") String to );

    //Find number of all active orders of selected type, that belongs to the selected to the selected authorized client
   @Query("SELECT COUNT(o.clientID) FROM ClientOrder o WHERE " +
           "o.clientID=:clientID AND o.orderType=:typeOrder AND o.status=:status")
   int findNumOfActiveClientOrders(@Param("clientID") int clientID, @Param("typeOrder")String orderType,
                                   @Param("status") String status);

    //Find number of all active orders of selected type, that belongs to the selected to the selected unauthorized client
   @Query("SELECT COUNT(o.unauthContactInfo) FROM ClientOrder o WHERE" +
           " o.unauthContactInfo=:unauthInfo AND o.orderType=:typeOrder AND o.status=:status")
   int findNumOfActiveClientOrders(@Param("unauthInfo") String unauthInfo, @Param("typeOrder")String orderType,
                                   @Param("status") String status);



   //int countByDateOfOrderIsBetweenAndClientID(String from,String to,Integer clientID);

  // int countByClientIDAndDateOfOrderBetweenAndClientID();





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

   /* @Query("SELECT new com.taxipark.dto.StatisticsDto(co.dateOfOrder,COUNT(co.dateOfOrder))" +
            " FROM ClientOrder co WHERE  co.dateOfOrder BETWEEN :frm AND :to GROUP BY co.dateOfOrder")
    List<StatisticsDto> findNumOfAllOrdersByDays(@Param("frm") String from, @Param("to") String to);

    @Query("SELECT new com.taxipark.dto.StatisticsDto(co.dateOfOrder,SUM(co.cost))" +
            " FROM ClientOrder co WHERE co.dateOfOrder BETWEEN :frm AND :to GROUP BY co.dateOfOrder")
    List<StatisticsDto> findAllIncomeByDays(@Param("frm") String from, @Param("to") String to);

    @Query("SELECT new com.taxipark.dto.StatisticsDto(co.dateOfOrder,COUNT(co.dateOfOrder))" +
            " FROM ClientOrder co INNER JOIN com.taxipark.dbmodel.Services_Category sc ON  WHERE co.dateOfOrder BETWEEN :frm AND :to GROUP BY co.dateOfOrder")
    List<StatisticsDto> findAllIncomeByays(@Param("frm") String from, @Param("to") String to);
    */





}
