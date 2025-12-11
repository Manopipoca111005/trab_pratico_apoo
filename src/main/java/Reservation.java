import java.time.LocalDateTime;
import java.util.Date;

public class Reservation{
    private Client clients;
    private LocalDateTime startDateTime;
    private String originAddress;
    private String destinationAddress;
    private double kms;
    public static String[] prompts(){
        return new String[] {
        "Digite o número de identificação fiscal do cliente:",
        "Digite a data/hora inicial: ",
        "Digite o endereço de origem: ",
        "Digite o endereço de destino: ",
        "Digite os quilómetros(kms) pecorridos"
        };
    }


    Reservation(Client clients ,LocalDateTime startDateTime, String originAddress, String destinationAddress, double kms) {
        this.clients = clients;
        this.startDateTime = startDateTime;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.kms = kms;
    }

    public Client getClients() {
        return clients;
    }
    public void setClients(Client clients) {
        this.clients = clients;
    }
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public String getOriginAddress() {
        return originAddress;
    }
    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }
    public String getDestinationAddress() {
        return destinationAddress;
    }
    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
    public double getKms() {
        return kms;
    }
    public void setKms(double kms) {
        this.kms = kms;
    }
}
