package dto;

public class CompletionTimeDto
{
    private int servicesID;
    private double completionTime;

    public CompletionTimeDto(int servicesID,double completionTime)
    {
        this.servicesID=servicesID;
        this.completionTime =completionTime;
    }

    public void setServicesID(int servicesID) {
        this.servicesID = servicesID;
    }

    public void setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
    }

    public int getServicesID() {
        return servicesID;
    }

    public double getCompletionTime() {
        return completionTime;
    }
}
