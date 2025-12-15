import java.time.LocalDateTime;

public class Travel implements HasNif {
    private Driver driver;
    private Client client;
    private Vehicle vehicle;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String originAddress;
    private String destinationAddress;
    private double kms;
    private double tripCost;

    @Override
    public int getNif() {
        return this.client.getNif();
    }
    public static String[] prompts(){
        return new String[] {
                "Digite o número de identificação fiscal do condutor: ",
                "Digite o número de identificação fiscal do cliente: ",
                "Digite o número da matricula: ",
                "Digite a data/hora inicial: ",
                "Digite o endereço de origem: ",
                "Digite o endereço de destino: ",
                "Digite os quilómetros(kms) pecorridos"
        };
    }

    Travel(Driver driver, Client client, Vehicle vehicle, LocalDateTime startDateTime, LocalDateTime endDateTime, String originAddress, String destinationAddress, double kms, double tripCost) {
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

    public Driver getDriver() {
        return driver;
    }
    public Client getClient() {
        return client;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
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
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public void setVehicle(Vehicle vehicle) {
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
