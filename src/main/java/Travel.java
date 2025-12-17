import java.time.LocalDateTime;

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

    @Override
    public int getClientNif() {
        return this.client.getClientNif();
    }

    @Override
    public int getDriverNif() {
        return this.driver.getDriverNif();
    }

    @Override
    public String getLicensePlate(){
        return this.vehicle.getLicensePlate();
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

    public static void Menu(){
        System.out.println("📅 --- Gestão de Viagens --- 📋");
        System.out.println("1.  ➕ Registar Viagem");
        System.out.println("2.  📋 Listar Viagens");
        System.out.println("3.  🔍 Consultar Viagem (por NIF do cliente)");
        System.out.println("4.  📝 Atualizar Viagem");
        System.out.println("5.  🗑️ Eliminar Viagem");
        System.out.println("0.  ↩️ Voltar");
        System.out.print("👉 Selecione uma opção: ");
    }

    public static String[] prompts(){
        return new String[] {
                "🪪 Digite o número de identificação fiscal do condutor: ",
                "🪪 Digite o número de identificação fiscal do cliente: ",
                "🪪 Digite a matricula da viatura: ",
                "📅 Digite a Hora/Data inicial: ",
                "📅 Digite a Hora/Data final: ",
                "🏠 Digite a morada do origem: ",
                "📍 Digite o endereço de destino: ",
                "🛣️ Digite os quilómetros(kms) percorridos: ",
                "💰 Digite o custo da viagem: "
        };
    }

    public static String [] infoPrompts(){
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
    @Override
    public String toString() {
        return "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "🗺️ " + this.getClient().getName().toUpperCase() + " " + this.getClient().getClientNif() + "\n + " +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                infoPrompts()[1] + this.getClient().getName() + "\n" +
                infoPrompts()[2] + this.getClient().getClientNif() + "\n" +
                infoPrompts()[3] + this.getDriver().getName() + "\n" +
                infoPrompts()[4] + this.getDriver().getClientNif() + "\n" +
                infoPrompts()[5] + this.getVehicle().getLicensePlate() + "\n" +
                infoPrompts()[6] + this.getVehicle().getBrand() + "\n" +
                infoPrompts()[7] + this.getVehicle().getModel() + "\n" +
                infoPrompts()[8] + this.getVehicle().getProductionYear() + "\n" +
                infoPrompts()[9] + this.getStartDateTime() + "\n" +
                infoPrompts()[10] + this.getEndDateTime() + "\n" +
                infoPrompts()[11] + this.getOriginAddress() + "\n" +
                infoPrompts()[12] + this.getDestinationAddress() + "\n" +
                infoPrompts()[13] + this.getKms() + "\n" +
                infoPrompts()[14] + this.getTripCost() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n";
    }

}
