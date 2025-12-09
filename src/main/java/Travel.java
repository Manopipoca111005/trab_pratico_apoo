import java.time.LocalDateTime;

public class Travel {
    private String driver;
    private String client;
    private String vehicle;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String originAddress;
    private String destinationAddress;
    private double kms;
    private double tripCost;

    Travel(String driver, String client, String vehicle, LocalDateTime startDateTime, LocalDateTime endDateTime, String originAddress, String destinationAddress, double kms, double tripCost) {
        this.driver = driver;
        this.client = client;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.vehicle = vehicle;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.kms = kms;
        this.tripCost = tripCost;
    }

    public String getDriver() {
        return driver;
    }
    public String getClient() {
        return client;
    }
    public String getVehicle() {
        return vehicle;
    }
    public String getOriginAddress() {
        return originAddress;
    }
    public String getDestinationAddress() {
        return destinationAddress;
    }
    public double getKms() {
        return kms;
    }
    public double getTripCost() {
        return tripCost;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public void setClient(String client) {
        this.client = client;
    }
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }
    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
    public void setKms(double kms) {
        this.kms = kms;
    }
    public void setTripCost(double tripCost) {
        this.tripCost = tripCost;
    }
}
