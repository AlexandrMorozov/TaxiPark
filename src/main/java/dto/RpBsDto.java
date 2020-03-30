package dto;

public class RpBsDto
{
    private int busStopID;
    private int routeID;
    private String direction;
    private int stopNumber;
    private String name;
    public RpBsDto(int busStopID, int routeID, String direction, int stopNumber, String name) {
        this.busStopID = busStopID;
        this.routeID = routeID;
        this.direction = direction;
        this.stopNumber = stopNumber;
        this.name=name;
    }

   public void setBusStopID(int busStopID) {
        this.busStopID = busStopID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getRouteID() {
        return routeID;
    }

    public int getBusStopID() {
        return busStopID;
    }

    public String getDirection() {
        return direction;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public int getStopNumber() {
        return stopNumber;
    }
}
