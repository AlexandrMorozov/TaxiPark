package dto;

public class FullOrderInfoDto
{
    private int orderID;
    private String orderName;
    private String orderDate;
    private String orderTime;
    private double orderCost;

    public FullOrderInfoDto(int orderID, String orderName, String orderDate, String orderTime, double orderCost)
    {
        this.orderID=orderID;
        this.orderName=orderName;
        this.orderDate=orderDate;
        this.orderTime=orderTime;
        this.orderCost=orderCost;
    }

    public int getOrderID() {
        return orderID;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getOrderTime() {
        return orderTime;
    }
}
