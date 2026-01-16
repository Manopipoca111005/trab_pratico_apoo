import java.time.LocalDateTime;

/**
 * Represents a Reservation made by a client.
 * Includes details such as start time, origin, destination, and distance.
 */
public class Reservation implements HasNif {
    private Client client;
    private LocalDateTime startDateTime;
    private String originAddress;
    private String destinationAddress;
    private double kms;

    /**
     * Retrieves the NIF of the client who made the reservation.
     *
     * @return The client's NIF.
     */
    @Override
    public long getClientNif() {
        return this.getClient().getClientNif();
    }

    /**
     * Retrieves the driver's NIF.
     *
     * @return Always returns 0 as this is a Reservation.
     */
    @Override
    public long getDriverNif() {
        return 0;
    }

    /**
     * Constructs a new Reservation.
     *
     * @param client             The client making the reservation.
     * @param startDateTime      The start date and time of the reservation.
     * @param originAddress      The starting address.
     * @param destinationAddress The destination address.
     * @param kms                The distance in kilometers.
     */
    Reservation(Client client, LocalDateTime startDateTime, String originAddress, String destinationAddress,
            double kms) {
        this.client = client;
        this.startDateTime = startDateTime;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.kms = kms;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client clients) {
        this.client = clients;
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

    /**
     * Displays the Reservation management menu options to the console.
     */
    public static void Menu() {
        System.out.println("📅 --- Gestão de Reservas --- 📋");
        System.out.println("1.  ➕ Registar Reserva");
        System.out.println("2.  📋 Listar Reservas");
        System.out.println("3.  🔍 Consultar Reservas de um cliente por NIF");
        System.out.println("4.  📝 Atualizar Reserva");
        System.out.println("5.  🗑️ Eliminar Reserva");
        System.out.println("0.  ↩️ Voltar");
        System.out.print("👉 Selecione uma opção: ");
    }

    /**
     * Provides prompts for collecting reservation information.
     *
     * @return An array of prompt strings.
     */
    public static String[] prompts() {
        return new String[] {
                "🪪 Digite o número de identificação fiscal do cliente:",
                "📅 Digite a data/hora inicial (dd/MM/yyyy HH:mm): ",
                "🏠 Digite o endereço de origem: ",
                "📍 Digite o endereço de destino: ",
                "🛣️ Digite os quilómetros(kms) percorridos: "
        };
    }

    /**
     * Provides labels for displaying reservation information.
     *
     * @return An array of label strings.
     */
    public static String[] infoPrompts() {
        return new String[] {
                "👤 Nome do cliente: ",
                "🪪 Número de identificação fiscal do cliente: ",
                "🪪 Hora/Data inicial: ",
                "🏠 Endereço de origem: ",
                "📍 Endereço de destino: ",
                "🛣️ Quilómetros(kms) percorridos: "
        };
    }

    /**
     * Returns a string representation of the Reservation.
     *
     * @return A formatted string containing reservation details.
     */
    @Override
    public String toString() {
        return "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "📅 Reserva do cliente " + this.getClient().getName() + " " + this.getClient().getClientNif() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                infoPrompts()[0] + this.getClient().getName() + "\n" +
                infoPrompts()[1] + this.getClient().getClientNif() + "\n" +
                infoPrompts()[2] + this.getStartDateTime() + "\n" +
                infoPrompts()[3] + this.getOriginAddress() + "\n" +
                infoPrompts()[4] + this.getDestinationAddress() + "\n" +
                infoPrompts()[5] + this.getKms() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n";
    }
}
