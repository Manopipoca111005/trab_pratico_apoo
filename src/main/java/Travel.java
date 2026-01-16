import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Travel (Trip) in the system.
 * A travel involves a driver, a client, a vehicle, and details about the
 * journey.
 */
public class Travel implements HasNif, HasLicensePlate {
    private Driver driver;
    private Client client;
    private Vehicle vehicle;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String originAddress;
    private String destinationAddress;
    private double kms;
    private double tripCost;

    /**
     * Retrieves the client's NIF.
     *
     * @return The NIF of the client associated with the travel.
     */
    @Override
    public long getClientNif() {
        return this.client.getClientNif();
    }

    /**
     * Retrieves the driver's NIF.
     *
     * @return The NIF of the driver associated with the travel.
     */
    @Override
    public long getDriverNif() {
        return this.driver.getDriverNif();
    }

    /**
     * Retrieves the vehicle's license plate.
     *
     * @return The license plate of the vehicle used in the travel.
     */
    @Override
    public String getLicensePlate() {
        return this.vehicle.getLicensePlate();
    }

    /**
     * Constructs a new Travel instance.
     *
     * @param driver             The driver of the travel.
     * @param client             The client of the travel.
     * @param vehicle            The vehicle used.
     * @param startDateTime      The start date and time.
     * @param endDateTime        The end date and time.
     * @param originAddress      The origin address.
     * @param destinationAddress The destination address.
     * @param kms                The distance in kilometers.
     * @param tripCost           The cost of the trip.
     */
    Travel(Driver driver, Client client, Vehicle vehicle, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String originAddress, String destinationAddress, double kms, double tripCost) {
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

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setKms(double kms) {
        this.kms = kms;
    }

    public void setTripCost(double tripCost) {
        this.tripCost = tripCost;
    }

    /**
     * Displays the Travel management menu options to the console.
     */
    public static void Menu() {
        System.out.println("📅 --- Gestão de Viagens --- 📋");
        System.out.println("1.  ➕ Registar Viagem");
        System.out.println("2.  📋 Listar Viagens");
        System.out.println("3.  🔍 Consultar Viagem (por NIF do cliente)");
        System.out.println("4.  📝 Atualizar Viagem");
        System.out.println("5.  🗑️ Eliminar Viagem");
        System.out.println("0.  ↩️ Voltar");
        System.out.print("👉 Selecione uma opção: ");
    }

    /**
     * Provides prompts for collecting travel information.
     *
     * @return An array of prompt strings.
     */
    public static String[] prompts() {
        return new String[] {
                "🪪 Digite o número de identificação fiscal do condutor: ",
                "🪪 Digite o número de identificação fiscal do cliente: ",
                "🪪 Digite a matricula da viatura: ",
                "📅 Digite a Hora/Data inicial (dd/MM/yyyy HH:mm): ",
                "📅 Digite a Hora/Data final (dd/MM/yyyy HH:mm): ",
                "🏠 Digite a morada do origem: ",
                "📍 Digite o endereço de destino: ",
                "🛣️ Digite os quilómetros(kms) percorridos: ",
                "💰 Digite o custo da viagem: "
        };
    }

    /**
     * Provides labels for displaying travel information.
     *
     * @return An array of label strings.
     */
    public static String[] infoPrompts() {
        return new String[] {
                "👤 Nome do cliente: ",
                "🪪 Número de identificação fiscal do cliente: ",
                "👤 Nome do condutor: ",
                "🪪 Número de identificação fiscal do condutor: ",
                "🔢 Matricula da viatura: ",
                "🚗 Marca da Viatura: ",
                "🔍 Modelo da Viatura: ",
                "📅 Ano de fabrico da viatura: ",
                "📅 Hora/Data inicial: ",
                "📅 Hora/Data final: ",
                "🏠 Morada do origem: ",
                "📍 Endereço de destino: ",
                "🛣️ Quilómetros(kms) percorridos: ",
                "💰 Custo da viagem: "
        };
    }

    /**
     * Returns a string representation of the Travel.
     *
     * @return A formatted string containing travel details.
     */
    @Override
    public String toString() {
        return "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "🗺️ " + this.getClient().getName().toUpperCase() + " " + this.getClient().getClientNif() + "\n + " +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                infoPrompts()[0] + this.getClient().getName() + "\n" +
                infoPrompts()[1] + this.getClient().getClientNif() + "\n" +
                infoPrompts()[2] + this.getDriver().getName() + "\n" +
                infoPrompts()[3] + this.getDriver().getDriverNif() + "\n" +
                infoPrompts()[4] + this.getVehicle().getLicensePlate() + "\n" +
                infoPrompts()[5] + this.getVehicle().getBrand() + "\n" +
                infoPrompts()[6] + this.getVehicle().getModel() + "\n" +
                infoPrompts()[7] + this.getVehicle().getProductionYear() + "\n" +
                infoPrompts()[8] + this.getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                + "\n" +
                infoPrompts()[9] + this.getEndDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n"
                +
                infoPrompts()[10] + this.getOriginAddress() + "\n" +
                infoPrompts()[11] + this.getDestinationAddress() + "\n" +
                infoPrompts()[12] + this.getKms() + "\n" +
                infoPrompts()[13] + this.getTripCost() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n";
    }

}
