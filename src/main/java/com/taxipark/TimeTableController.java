package com.taxipark;

import com.taxipark.repos.*;
import com.taxipark.dbmodel.Arrival_Time;
import com.taxipark.dbmodel.Route;
import com.taxipark.dto.ArrivalHourDisplayDto;
import com.taxipark.dto.ArrivalMinuteDisplayDto;
import com.taxipark.dto.RpBsDto;
import com.taxipark.services.NavBarLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TimeTableController
{
    @Autowired
    private RouteRepo routeRepo;
    @Autowired
    private Route_PointsRepo route_pointsRepo;
    @Autowired
    private Arrival_TimeRepo arrival_timeRepo;
    @Autowired
    private Services_CategoryRepo services_categoryRepo;
    @Autowired
    private ServicesRepo servicesRepo;

    private NavBarLoader navBarLoader=new NavBarLoader();

    @GetMapping("/MainTimetableMenu")
    public String busTimetable(HttpSession session, Map<String, Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        Iterable<Route> routes=routeRepo.findByType("auto");

        model.put("routesList",routes);

        return "user/MainTimetableMenu";
    }

    @GetMapping("/Route")
    public String getRoute(@RequestParam(name="routeID", required=true) String routeID,HttpSession session,Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");


        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        int routeIdent=Integer.parseInt(routeID);

        Iterable<RpBsDto> upRoutePoints=route_pointsRepo.fetchRpBsDataInnerJoin(routeIdent,"up");
        Iterable<RpBsDto> bkRoutePoints=route_pointsRepo.fetchRpBsDataInnerJoin(routeIdent,"back");


        model.put("routeNum",routeID);
        model.put("upRoutes",upRoutePoints);
        model.put("downRoutes",bkRoutePoints);

        return "user/Route";
    }

    @GetMapping("/RoutePoint")
    public String getStopTimetable(@RequestParam(name="routeID", required=true) String routeID,
                                   @RequestParam(name="pointID", required=true) String pointID,
                                   @RequestParam(name="dir", required=true) String direction,
                                   HttpSession session,Map<String,Object> model)
    {
        String login=(String) session.getAttribute("login");

        navBarLoader.checkAuthorization(login,model);
        navBarLoader.loadNavigationBarLinks(model,services_categoryRepo,servicesRepo);

        int routeIdent=Integer.parseInt(routeID);
        int busStopIdent=Integer.parseInt(pointID);

        List<ArrivalHourDisplayDto> workDaysArrivalHours=new ArrayList<>();
        List<ArrivalHourDisplayDto> weekendArrivalHours=new ArrayList<>();
        List<Arrival_Time> workDaysArrivalTimeList=
                arrival_timeRepo.findAllByRouteIDAndBusStopIDAndTypeOfDayAndDirection(routeIdent,busStopIdent,"work",direction);

        formTimetable(workDaysArrivalTimeList,workDaysArrivalHours);

        List<Arrival_Time> weekendArrivalTimeList=
                arrival_timeRepo.findAllByRouteIDAndBusStopIDAndTypeOfDayAndDirection(routeIdent,busStopIdent,"weekend",direction);

        formTimetable(weekendArrivalTimeList,weekendArrivalHours);

        model.put("routeNum",routeID);
        model.put("workDays",workDaysArrivalHours);
        model.put("weekendDays",weekendArrivalHours);


        return "user/RoutePoint";
    }

    private void formTimetable(List<Arrival_Time> arrivalTimeList, List<ArrivalHourDisplayDto> arrivalHours)
    {
        for(int i=0;i<24;i++)
        {
            List<ArrivalMinuteDisplayDto> listOfArrivalMinutes=new ArrayList<>();
            for(int j=0;j<arrivalTimeList.size();j++)
            {
                String c=arrivalTimeList.get(j).getTime();
                String[] time=c.split(":");

                if(Integer.parseInt(time[0])==i)
                {
                    listOfArrivalMinutes.add(new ArrivalMinuteDisplayDto(time[1]));
                }
            }

            if(listOfArrivalMinutes.size()>0)
            {
                arrivalHours.add(new ArrivalHourDisplayDto(Integer.toString(i),listOfArrivalMinutes));
            }
        }
    }


}
