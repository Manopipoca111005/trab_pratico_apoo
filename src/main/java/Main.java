import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Main application class for the TVDE Management System.
 * Handles the main execution loop, user interaction, and file I/O operations.
 */
public class Main {
    public static Path folder;
    public static String fileName;
    public static String filePath;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");
    static public Scanner scanner = new Scanner(System.in);
    public static ArrayList<Driver> drivers = new ArrayList<>();
    public static ArrayList<Vehicle> vehicles = new ArrayList<>();
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<Travel> travels = new ArrayList<>();
    public static String[] line;
    public static Driver driverFound;
    public static Vehicle vehicleFound;
    public static Reservation reservationFound;
    public static Travel travelFound;
    public static Client clientFound;
    public static ArrayList<Travel> travelsFound = new ArrayList<>();

    /**
     * Displays the application header/logo.
     */
    public static void Header() {
        System.out.println("=========================================");
        System.out.println("       🚗 SISTEMA DE GESTÃO TVDE 📊       ");
        System.out.println("=========================================");

        while (true) {
            System.out.print("👉 Digite o nome da empresa: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ O nome da empresa não pode ser vazio.");
                continue;
            }

            folder = Paths.get(input);
            if (!Files.exists(folder) || !Files.isDirectory(folder)) {
                System.out.println(
                        "❌ A pasta '" + input + "' não encontrada. Certifique-se que existe no diretório do projeto.");
                continue;
            }

            System.out.println("=========================================");
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
                System.out.println("📂 Ficheiros carregados:");
                for (Path file : stream) {
                    System.out.println("   📄 " + file.getFileName());
                }
                break;
            } catch (IOException e) {
                System.out.println("❌ Erro ao ler a pasta: " + e.getMessage());
            }
        }
    }

    /**
     * Displays the main menu options to the console.
     */
    public static void Menu() {
        System.out.println("------------------------------------------");
        System.out.println("         💻 Menu Principal Sistema TVDE    ");
        System.out.println("------------------------------------------");
        System.out.println("1. 👤 Gestão de Condutores (CRUD)");
        System.out.println("2. 🚗 Gestão de Viaturas (CRUD)");
        System.out.println("3. 👥 Gestão de Clientes (CRUD)");
        System.out.println("4. 📅 Gestão de Reservas (CRUD, Inserir/Remover)");
        System.out.println("5. 🗺️ Gestão de Viagens (CRUD, Inserir/Remover)");
        System.out.println("6. ➡️ Criar Viagem a partir de Reserva");
        System.out.println("7. 📊 Pesquisas e Estatísticas");
        System.out.println("8. 📁 Gestão de Ficheiros e Dados (Gravar/Ler)");
        System.out.println("0. ❌ Sair do Sistema");
        System.out.println("------------------------------------------");
        System.out.print("👉 Selecione uma opção: ");
    }

    /**
     * Helper method to display a formatted title with an emoji.
     *
     * @param emoji The emoji to display.
     * @param title The title text.
     */
    public static void infoTitle(String emoji, String title) {
        String line = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
        System.out.println(line);
        System.out.println(emoji + " " + title.toUpperCase());
        System.out.println(line + "\n");
    }

    /**
     * The main method creates the application entry point.
     * Initializes data references and starts the main application loop.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Header();

        int option;
        String value = "";
        long nif;
        String licensePlate;
        int subOption;

        do {
            Menu();
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
            } else {
                scanner.next(); // Consumir entrada invalida
                System.out.println("❌ Opção inválida! Por favor, insira um número inteiro.");
                option = -1;
            }
            switch (option) {
                case 1:
                    Driver.Menu();
                    subOption = scanner.nextInt();
                    scanner.nextLine();
                    switch (subOption) {
                        case 1:
                            addData(drivers, line -> new Driver(
                                    line.get(0),
                                    Long.parseLong(line.get(1)),
                                    line.get(2),
                                    Long.parseLong(line.get(3)),
                                    Long.parseLong(line.get(4)),
                                    line.get(5),
                                    line.get(6)), "drivers.txt", true, null);
                            break;
                        case 2:
                            infoTitle("📋", "INFORMAÇÕES DOS CONDUTORES");
                            for (Driver driver : drivers) {
                                System.out.println(driver);
                            }
                            break;
                        case 3:
                            System.out.print("🪪Digite o número de identificação fiscal do condutor: ");
                            nif = scanner.nextLong();
                            driverFound = findByNIF(nif, drivers, "drivers");
                            System.out.println(driverFound);
                            break;
                        case 4:
                            System.out.print("Digite o número de identificação fiscal do condutor: ");
                            nif = scanner.nextLong();
                            scanner.nextLine();
                            driverFound = findByNIF(nif, drivers, "drivers");
                            if (driverFound == null) {
                                System.out.println("❌ Condutor não encontrado!");
                                break;
                            }
                            System.out.print(Driver.prompts()[0]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setName(value);
                            }
                            System.out.print(Driver.prompts()[1]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setNIC(Long.parseLong(value));
                            }
                            System.out.print(Driver.prompts()[2]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setDriverLicenseNumber(value);
                            }
                            System.out.print(Driver.prompts()[3]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setNiss(Long.parseLong(value));
                            }
                            System.out.print(Driver.prompts()[4]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setNif(Long.parseLong(value));
                            }
                            System.out.print(Driver.prompts()[5]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setTlm(value);
                            }
                            System.out.print(Driver.prompts()[6]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setAddress(value);
                            }
                            break;
                        case 5:
                            filePath = "drivers.txt";
                            line = null;
                            System.out.print("🪪Digite o número de identificação fiscal do condutor: ");
                            nif = scanner.nextLong();
                            scanner.nextLine();
                            driverFound = findByNIF(nif, drivers, "drivers");
                            if (driverFound == null) {
                                System.out.println("❌ Condutor não encontrado!");
                                break;
                            }
                            boolean isNotValid = false;
                            for (Travel travel : travels) {
                                if (driverFound.getDriverNif() == (travel.getDriverNif())) {
                                    isNotValid = true;
                                    System.out.println("Não é possível eleminar condutores com viagens!");
                                    break;
                                }
                            }
                            if (!isNotValid) {
                                drivers.remove(driverFound);
                                try (PrintWriter printWriter = new PrintWriter(
                                        new BufferedWriter(new FileWriter(filePath)))) {
                                    for (Driver driver : drivers) {
                                        printWriter.print(driver.getName());
                                        printWriter.print(";");
                                        printWriter.print(driver.getNIC());
                                        printWriter.print(";");
                                        printWriter.print(driver.getDriverLicenseNumber());
                                        printWriter.print(";");
                                        printWriter.print(driver.getNiss());
                                        printWriter.print(";");
                                        printWriter.print(driver.getDriverNif());
                                        printWriter.print(";");
                                        printWriter.print(driver.getTlm());
                                        printWriter.print(";");
                                        printWriter.println(driver.getAddress());
                                    }
                                    System.out.println("Dados eleminados com sucesso!");
                                } catch (FileNotFoundException e) {
                                    System.out.println("Ficheiro não encontrado!");
                                } catch (IOException e) {
                                    System.out.println("Alguma coisa correu mal!");
                                }
                            }
                            break;
                        case 0:
                            break;
                    }
                    break;
                case 2:
                    Vehicle.Menu();
                    subOption = scanner.nextInt();
                    scanner.nextLine();
                    switch (subOption) {
                        case 1:
                            addData(vehicles, line -> new Vehicle(
                                    line.get(0),
                                    line.get(1),
                                    line.get(2),
                                    Integer.parseInt(line.get(3)),
                                    line.get(4)), "vehicles.txt", true, null);
                            break;
                        case 2:
                            infoTitle("📋", "INFORMAÇÕES DAS VIATURAS");
                            for (Vehicle vehicule : vehicles) {
                                System.out.println(vehicule);
                            }
                            break;
                        case 3:
                            System.out.print("🔢Digite a matricula do veiculo: ");
                            licensePlate = scanner.nextLine();
                            vehicleFound = findVehicleByLicensePlate(licensePlate);
                            System.out.println(vehicleFound);
                            break;
                        case 4:
                            System.out.print("🔢Digite a matricula do veiculo: ");
                            licensePlate = scanner.nextLine();
                            vehicleFound = findVehicleByLicensePlate(licensePlate);
                            if (vehicleFound == null) {
                                System.out.println("❌ Viatura não encontrada!");
                                break;
                            }
                            System.out.print(Vehicle.prompts()[4]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                vehicleFound.setColor(value);
                            }
                            System.out.println("✅ Viatura atualizada com sucesso!");
                            break;
                        case 5:
                            filePath = "vehicles.txt";
                            line = null;
                            System.out.print("🔢Digite a matricula do veiculo: ");
                            licensePlate = scanner.nextLine();
                            vehicleFound = findVehicleByLicensePlate(licensePlate);
                            boolean isNotValid = false;
                            for (Travel travel : travels) {
                                if (vehicleFound.getLicensePlate().equals(travel.getLicensePlate())) {
                                    isNotValid = true;
                                    System.out.println(
                                            "Não é possível eliminar a viatura, pois está associada a uma viagem!");
                                    break;
                                }
                            }
                            if (!isNotValid) {
                                vehicles.remove(vehicleFound);
                                try (PrintWriter printWriter = new PrintWriter(
                                        new BufferedWriter(new FileWriter(filePath)))) {
                                    for (Vehicle vehicule : vehicles) {
                                        printWriter.print(vehicule.getLicensePlate());
                                        printWriter.print(";");
                                        printWriter.print(vehicule.getBrand());
                                        printWriter.print(";");
                                        printWriter.print(vehicule.getModel());
                                        printWriter.print(";");
                                        printWriter.print(vehicule.getProductionYear());
                                    }
                                    System.out.println("Dados eleminados com sucesso!");
                                } catch (FileNotFoundException e) {
                                    System.out.println("Ficheiro não encontrado!");
                                } catch (IOException e) {
                                    System.out.println("Alguma coisa correu mal!");
                                }
                            }
                            break;

                        case 0:
                            break;
                    }
                    break;
                case 3:
                    Client.Menu();
                    subOption = scanner.nextInt();
                    scanner.nextLine();
                    switch (subOption) {
                        case 1:
                            addData(clients, line -> new Client(
                                    line.get(0),
                                    Long.parseLong(line.get(1)),
                                    line.get(2),
                                    line.get(3)), "clients.txt", true, null);
                            break;
                        case 2:
                            infoTitle("📋", "INFORMAÇÕES DOS CLIENTES");
                            for (Client client : clients) {
                                System.out.println(client);
                            }
                            break;
                        case 3:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            clientFound = findByNIF(nif, clients, "clients");
                            System.out.println(clientFound);
                            break;
                        case 4:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            scanner.nextLine();
                            clientFound = findByNIF(nif, clients, "clients");
                            System.out.print(Client.prompts()[0]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                clientFound.setName(value);
                            }
                            System.out.print(Client.prompts()[1]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                clientFound.setNif(Long.parseLong(value));
                            }
                            System.out.print(Client.prompts()[2]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                clientFound.setTlm(value);
                            }
                            System.out.print(Client.prompts()[3]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                clientFound.setAddress(value);
                            }
                            break;
                        case 5:
                            filePath = "clients.txt";
                            line = null;
                            System.out.print("🪪Digite o número do identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            scanner.nextLine();
                            clientFound = findByNIF(nif, clients, "clients");
                            boolean isNotValid = false;
                            for (Reservation reservation : reservations) {
                                if (clientFound.getClientNif() == reservation.getClientNif()) {
                                    isNotValid = true;
                                    System.out.println("Não é possível eleminar clientes com reservas!");
                                    break;
                                } else {
                                    isNotValid = false;
                                }
                            }
                            for (Travel travel : travels) {
                                if (clientFound.getClientNif() == travel.getClientNif()) {
                                    isNotValid = true;
                                    System.out.println("Não é possível eleminar clientes com viagens!");
                                    break;
                                }
                            }
                            if (!isNotValid) {
                                clients.remove(clientFound);
                                try (PrintWriter printWriter = new PrintWriter(
                                        new BufferedWriter(new FileWriter(filePath)))) {
                                    for (Client client : clients) {
                                        printWriter.print(client.getName());
                                        printWriter.print(";");
                                        printWriter.print(client.getClientNif());
                                        printWriter.print(";");
                                        printWriter.print(client.getTlm());
                                        printWriter.print(";");
                                        printWriter.print(client.getAddress());
                                    }
                                    System.out.println("Dados eleminados com sucesso!");
                                } catch (FileNotFoundException e) {
                                    System.out.println("Ficheiro não encontrado!");
                                } catch (IOException e) {
                                    System.out.println("Alguma coisa correu mal!");
                                }
                            }
                            break;
                        case 0:
                            break;
                    }
                    break;
                case 4:
                    Reservation.Menu();
                    subOption = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer
                    switch (subOption) {
                        case 1:
                            infoTitle("➕", "ADICIONAR RESERVA");
                            ArrayList<String> reservationAttributes = new ArrayList<>();
                            for (int i = 0; i < Reservation.prompts().length; i++) {
                                System.out.print(Reservation.prompts()[i]);
                                reservationAttributes.add(scanner.nextLine());
                            }
                            clientFound = findByNIF(Long.parseLong(reservationAttributes.get(0)), clients, "clients");
                            int clientReservationsNum = 0;
                            for (Reservation reservation : reservations) {
                                if (clientFound.getClientNif() == reservation.getClientNif()
                                        && LocalDateTime.parse(reservationAttributes.get(1), DATE_TIME_FORMATTER)
                                                .equals(reservation.getStartDateTime())) {
                                    clientReservationsNum++;
                                }
                            }
                            if (clientReservationsNum < 2) {
                                Reservation newReservation = new Reservation(
                                        clientFound,
                                        LocalDateTime.parse(reservationAttributes.get(1), DATE_TIME_FORMATTER),
                                        reservationAttributes.get(2),
                                        reservationAttributes.get(3),
                                        Double.parseDouble(reservationAttributes.get(4)));
                                reservations.add(newReservation);
                                System.out.println("✅ Reserva adicionada com sucesso!");
                            } else {
                                System.out.println("❌ Cliente já possui 2 reservas para esta data!");
                            }
                            break;
                        case 2:
                            infoTitle("📋", "INFORMAÇÕES DAS RESERVAS");
                            for (Reservation reservation : reservations) {
                                System.out.println(reservation);
                            }
                            break;
                        case 3:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            infoTitle("📋", "RESERVAS DO CLIENTE NIF: " + nif);
                            for (Reservation reservation : reservations) {
                                if (reservation.getClientNif() == nif) {
                                    System.out.println(reservation);
                                }
                            }
                            break;
                        case 4:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            scanner.nextLine();
                            findByNIF(nif, reservations, "clients");
                            reservationFound = findByNIF(nif, reservations, "clients");
                            System.out.print(Reservation.prompts()[0]); // Client NIF
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                reservationFound.setClient(findByNIF(Long.parseLong(value), clients, "clients"));
                            }
                            System.out.print(Reservation.prompts()[1]); // Date
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                reservationFound.setStartDateTime(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                            }
                            System.out.print(Reservation.prompts()[2]); // Origin
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                reservationFound.setOriginAddress(value);
                            }
                            System.out.print(Reservation.prompts()[3]); // Destination
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                reservationFound.setDestinationAddress(value);
                            }
                            System.out.println(Reservation.prompts()[4]); // Kms
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                reservationFound.setKms(Double.parseDouble(value));
                            }
                            break;
                        case 5:
                            filePath = "clients.txt";
                            line = null;
                            System.out.print("🪪Digite o número do identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            scanner.nextLine();
                            reservationFound = findByNIF(nif, reservations, "clients");
                            reservations.remove(reservationFound);
                            try (PrintWriter printWriter = new PrintWriter(
                                    new BufferedWriter(new FileWriter(filePath)))) {
                                for (Reservation reservation : reservations) {
                                    printWriter.print(reservation.getClient().getClientNif());
                                    printWriter.print(";");
                                    printWriter.print(reservation.getStartDateTime());
                                    printWriter.print(";");
                                    printWriter.print(reservation.getOriginAddress());
                                    printWriter.print(";");
                                    printWriter.print(reservation.getDestinationAddress());
                                    printWriter.print(";");
                                    printWriter.print(reservation.getKms());
                                }
                                System.out.println("Dados eleminados com sucesso!");
                            } catch (FileNotFoundException e) {
                                System.out.println("Ficheiro não encontrado!");
                            } catch (IOException e) {
                                System.out.println("Alguma coisa correu mal!");
                            }
                            break;
                        case 0:
                            break;
                    }
                    break;
                case 5:
                    Travel.Menu();
                    subOption = scanner.nextInt();
                    scanner.nextLine();
                    switch (subOption) {
                        case 1:
                            addData(travels, line -> {
                                try {
                                    driverFound = findByNIF(Long.parseLong(line.get(0)), drivers, "drivers");
                                    clientFound = findByNIF(Long.parseLong(line.get(1)), clients, "clients");
                                    vehicleFound = findVehicleByLicensePlate(line.get(2));
                                    int clientReservationsNum = 0;

                                    for (Travel travel : travels) {
                                        if (driverFound.getDriverNif() == travel.getDriverNif()
                                                && clientFound.getClientNif() == travel.getClientNif()
                                                && vehicleFound.getLicensePlate().equals(travel.getLicensePlate())
                                                && LocalDateTime.parse(line.get(3)).equals(travel.getStartDateTime())
                                                && LocalDateTime.parse(line.get(4)).equals(travel.getEndDateTime())) {
                                            clientReservationsNum++;
                                        }
                                    }

                                    if (clientReservationsNum < 2) {
                                        return new Travel(
                                                findByNIF(Long.parseLong(line.get(0)), drivers, "drivers"),
                                                findByNIF(Long.parseLong(line.get(1)), clients, "clients"),
                                                findVehicleByLicensePlate(line.get(2)),
                                                LocalDateTime.parse(line.get(3), DATE_TIME_FORMATTER),
                                                LocalDateTime.parse(line.get(4), DATE_TIME_FORMATTER),
                                                line.get(5),
                                                line.get(6),
                                                Double.parseDouble(line.get(7)),
                                                Double.parseDouble(line.get(8)));
                                    }
                                    return null;
                                } catch (Exception e) {
                                    return null;
                                }
                            }, "reservations.txt", true, null);
                            break;
                        case 2:
                            infoTitle("📋", "INFORMAÇÕES DAS VIAGENS");
                            for (Travel travel : travels) {
                                System.out.println(travel);
                            }
                            break;
                        case 3:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            travelFound = findByNIF(nif, travels, "clients");
                            System.out.println(travelFound);
                            break;
                        case 4:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            scanner.nextLine();
                            travelFound = findByNIF(nif, travels, "clients");
                            System.out.print(Travel.prompts()[0]); // Driver NIF
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setDriver(findByNIF(Long.parseLong(value), drivers, "drivers"));
                            }
                            System.out.print(Travel.prompts()[1]); // Client NIF
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setClient(findByNIF(Long.parseLong(value), clients, "clients"));
                            }
                            System.out.print(Travel.prompts()[2]); // Vehicle Plate
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setVehicle(findVehicleByLicensePlate(value));
                            }
                            System.out.print(Travel.prompts()[3]); // Start time
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setStartDateTime(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                            }
                            System.out.println(Travel.prompts()[4]); // End time
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setEndDateTime(LocalDateTime.parse(value, DATE_TIME_FORMATTER));
                            }
                            System.out.println(Travel.prompts()[5]); // Origin
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setOriginAddress(value);
                            }
                            System.out.println(Travel.prompts()[6]); // Destination
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setDestinationAddress(value);
                            }
                            System.out.println(Travel.prompts()[7]); // Kms
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setKms(Double.parseDouble(value));
                            }
                            System.out.println(Travel.prompts()[8]); // Cost
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setTripCost(Double.parseDouble(value));
                            }
                            break;
                        case 5:
                            filePath = "clients.txt";
                            line = null;
                            System.out.print("🪪Digite o número do identificação fiscal do cliente: ");
                            nif = scanner.nextLong();
                            scanner.nextLine();
                            travelFound = findByNIF(nif, travels, "clients");
                            for (int i = 1; i < travels.toArray().length; i++) {
                                if (travelFound.getClientNif() == travels.get(i - 1).getClientNif() && travels.get(i)
                                        .getStartDateTime().equals(travels.get(i - 1).getStartDateTime())) {
                                    travels.remove(travelFound);
                                    try (PrintWriter printWriter = new PrintWriter(
                                            new BufferedWriter(new FileWriter(filePath)))) {
                                        for (Travel travel : travels) {
                                            printWriter.print(travel.getDriver().getDriverNif());
                                            printWriter.print(";");
                                            printWriter.print(travel.getClient().getDriverNif());
                                            printWriter.print(";");
                                            printWriter.print(travel.getVehicle().getLicensePlate());
                                            printWriter.print(";");
                                            printWriter.print(travel.getStartDateTime());
                                            printWriter.print(";");
                                            printWriter.print(travel.getEndDateTime());
                                            printWriter.print(";");
                                            printWriter.print(travel.getOriginAddress());
                                            printWriter.print(";");
                                            printWriter.print(travel.getDestinationAddress());
                                            printWriter.print(";");
                                            printWriter.print(travel.getKms());
                                            printWriter.print(";");
                                            printWriter.print(travel.getTripCost());
                                            printWriter.print(";");
                                        }
                                        System.out.println("Dados eleminados com sucesso!");
                                    } catch (FileNotFoundException e) {
                                        System.out.println("Ficheiro não encontrado!");
                                    } catch (IOException e) {
                                        System.out.println("Alguma coisa correu mal!");
                                    }

                                }
                            }
                            break;
                        case 0:
                            break;
                    }
                    break;
                case 6:
                    while (true) {
                        System.out.print("🪪Digite o número de identificação fiscal do cliente (0 para cancelar): ");
                        nif = scanner.nextLong();
                        scanner.nextLine(); // Limpar buffer

                        if (nif == 0) {
                            reservationFound = null;
                            break;
                        }

                        reservationFound = findByNIF(nif, reservations, "clients");

                        if (reservationFound != null) {
                            break;
                        }
                        System.out.println("❌ Nenhuma reserva encontrada para este cliente! Tente novamente.");
                    }

                    if (reservationFound == null) {
                        break;
                    }

                    addData(travels, line -> new Travel(
                            findByNIF(Long.parseLong(line.get(0)), drivers, "drivers"),
                            reservationFound.getClient(),
                            findVehicleByLicensePlate(line.get(1)),
                            reservationFound.getStartDateTime(),
                            LocalDateTime.parse(line.get(2), DATE_TIME_FORMATTER),
                            reservationFound.getOriginAddress(),
                            reservationFound.getDestinationAddress(),
                            reservationFound.getKms(),
                            Double.parseDouble(line.get(3))), "travels.txt", false, reservationFound);
                    reservations.remove(reservationFound);
                    break;
                case 7:
                    System.out.println("------------------------------------------");
                    System.out.println("        📊 Pesquisas e Estatísticas       ");
                    System.out.println("------------------------------------------");
                    System.out.println("1. 🔍 Pesquisar Viagens de Cliente por Data");
                    System.out.println("2. 👥 Listar Clientes associados a uma Viatura 🚐");
                    System.out.println("3. 💰 Total Faturado por Motorista num Intervalo de Datas");
                    System.out.println("4. 📏 Distância Média das Viagens por Período");
                    System.out.println("5. 📍 Destino mais solicitado por Período");
                    System.out.println("6. 👥 Listar Clientes por Intervalo de Distância de Viagem");
                    System.out.println("0. ⬅️ Voltar ao Menu Principal");
                    System.out.println("------------------------------------------");
                    System.out.print("👉 Selecione uma opção: ");
                    subOption = scanner.nextInt();
                    scanner.nextLine();
                    switch (subOption) {
                        case 1: {
                            System.out.print(Travel.prompts()[1]);
                            nif = scanner.nextLong();
                            scanner.nextLine(); // Clear buffer
                            LocalDate startDate = readDate("Digite a data inicial (dd/MM/yyyy): ", scanner);
                            LocalDateTime startDateTime = startDate.atStartOfDay();
                            LocalDate endDate = readDate("Digite a data final (dd/MM/yyyy): ", scanner);
                            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
                            findTravelByDate(startDateTime, endDateTime, nif, travelsFound);
                            for (Travel travel : travelsFound) {
                                System.out.println(travel);
                            }
                            break;
                        }
                        case 2:
                            System.out.print("🔢Digite a placa da viatura: ");
                            licensePlate = scanner.nextLine();
                            infoTitle("👥", "CLIENTES ASSOCIADOS À VIATURA " + licensePlate);
                            boolean foundAny = false;
                            for (Travel travel : travels) {
                                if (travel.getVehicle() != null
                                        && travel.getVehicle().getLicensePlate().equalsIgnoreCase(licensePlate)) {
                                    if (travel.getClient() != null) {
                                        System.out.println(travel.getClient());
                                        foundAny = true;
                                    }
                                }
                            }
                            if (!foundAny) {
                                System.out.println("❌ Nenhum cliente encontrado para esta viatura.");
                            }
                            break;
                        case 3: {
                            System.out.println("Digite o NIF do condutor: ");
                            nif = scanner.nextLong();
                            scanner.nextLine(); // Clear buffer
                            LocalDate startDate = readDate("Digite a data inicial (dd/MM/yyyy): ", scanner);
                            LocalDateTime startDateTime = startDate.atStartOfDay();
                            LocalDate endDate = readDate("Digite a data final (dd/MM/yyyy): ", scanner);
                            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
                            foundAny = false;
                            double tripCost = 0;
                            for (Travel travel : travels) {
                                if (travel.getDriver().getDriverNif() == nif &&
                                        !travel.getStartDateTime().isBefore(startDateTime) &&
                                        !travel.getEndDateTime().isAfter(endDateTime)) {
                                    tripCost += travel.getTripCost();
                                    foundAny = true;
                                }
                            }
                            System.out.println("Total faturado: " + tripCost);
                            if (!foundAny) {
                                System.out.println("❌ Nenhum condutor encontrado para este intervalo de datas.");
                            }
                            break;
                        }
                        case 4: {
                            LocalDate startDate = readDate("📅 Digite a data inicial (dd/MM/yyyy): ", scanner);
                            LocalDateTime startDateTime = startDate.atStartOfDay();
                            LocalDate endDate = readDate("📅 Digite a data final (dd/MM/yyyy): ", scanner);
                            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
                            foundAny = false;
                            double averageDistance = 0;
                            int travelCount = 0;
                            for (Travel travel : travels) {
                                if (!travel.getStartDateTime().isBefore(startDateTime) &&
                                        !travel.getEndDateTime().isAfter(endDateTime)) {
                                    averageDistance += travel.getKms();
                                    travelCount++;
                                    foundAny = true;
                                }
                            }
                            if (!foundAny) {
                                System.out.println("❌ Nenhuma viagem encontrada para este intervalo de datas.");
                            } else {
                                System.out.println("📏 Distância média: " + (averageDistance / travelCount) + " km");
                            }
                            break;
                        }
                        case 5: {
                            LocalDate startDate = readDate("📅 Digite a data inicial (dd/MM/yyyy): ", scanner);
                            LocalDateTime startDateTime = startDate.atStartOfDay();
                            LocalDate endDate = readDate("📅 Digite a data final (dd/MM/yyyy): ", scanner);
                            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
                            infoTitle("📍", "DESTINO MAIS SOLICITADO NO PERÍODO");
                            HashMap<String, Integer> destinationCount = new HashMap<>();
                            for (Reservation reservation : reservations) {
                                LocalDateTime reservationDate = reservation.getStartDateTime();
                                if (!reservationDate.isBefore(startDateTime) && !reservationDate.isAfter(endDateTime)) {
                                    String destination = reservation.getDestinationAddress();
                                    destinationCount.put(destination,
                                            destinationCount.getOrDefault(destination, 0) + 1);
                                }
                            }
                            for (Travel travel : travels) {
                                LocalDateTime travelDate = travel.getStartDateTime();
                                if (!travelDate.isBefore(startDateTime) && !travelDate.isAfter(endDateTime)) {
                                    String destination = travel.getDestinationAddress();
                                    destinationCount.put(destination,
                                            destinationCount.getOrDefault(destination, 0) + 1);
                                }
                            }
                            if (destinationCount.isEmpty()) {
                                System.out.println("❌ Nenhuma reserva ou viagem encontrada no período especificado.");
                            } else {
                                String mostRequestedDestination = null;
                                int maxCount = 0;
                                for (Map.Entry<String, Integer> entry : destinationCount.entrySet()) {
                                    if (entry.getValue() > maxCount) {
                                        maxCount = entry.getValue();
                                        mostRequestedDestination = entry.getKey();
                                    }
                                }
                                System.out.println("🏆 Destino mais solicitado: " + mostRequestedDestination);
                                System.out.println("📊 Total de solicitações: " + maxCount);
                            }
                            break;
                        }
                        case 6:
                            System.out.print("Distancia minima: ");
                            double minDistance = scanner.nextDouble();
                            System.out.print("Distancia maxima: ");
                            double maxDistance = scanner.nextDouble();
                            infoTitle("📍", "VIAGENS POR DISTANCIA");
                            for (Travel travel : travels) {
                                if (travel.getKms() >= minDistance && travel.getKms() <= maxDistance) {
                                    System.out.println(travel.getClient());
                                }
                            }
                            break;
                        case 0:
                            System.out.println("Voltando ao menu principal...");
                            break;
                        default:
                            System.out.println("❌ Opção inválida! Tente novamente.");
                    }
                    break;
                case 8:
                    System.out.println("------------------------------------------");
                    System.out.println("      📁 Gestão de Ficheiros e Dados      ");
                    System.out.println("------------------------------------------");
                    System.out.println("1. 💾 Gravar Dados no Ficheiro (Exportar)");
                    System.out.println("2. 📂 Ler Dados do Ficheiro (Importar)");
                    System.out.println("0. ⬅️ Voltar ao Menu Principal");
                    System.out.println("------------------------------------------");
                    System.out.print("👉 Selecione uma opção: ");
                    int fileOption = scanner.nextInt();
                    scanner.nextLine();
                    switch (fileOption) {
                        case 1:
                            saveAllDataToFiles();
                            break;
                        case 2:
                            loadAllDataFromFiles();
                            break;
                        case 0:
                            System.out.println("Voltando ao menu principal...");
                            break;
                        default:
                            System.out.println("❌ Opção inválida!");
                    }
                    break;
                case 0:
                    System.out.println("👋 Encerrando o sistema... Até breve!");
                    break;
                default:
                    System.out.println("❌ Opção inválida! Tente novamente.");
                    break;
            }
        } while (option != 0);

    }

    /**
     * Generic method to read data from a file and map it to objects.
     *
     * @param <T>        The type of object to create.
     * @param lineMapper A function that maps an array of strings (lines) to an
     *                   object of type T.
     * @param fileName   The name of the file to read from.
     * @return An ArrayList containing the objects read from the file.
     */
    static <T> void readFiles(ArrayList<T> list, Function<String[], T> lineMapper, String fileName) {
        switch (fileName) {
            case "drivers.txt":
                filePath = folder.resolve("drivers.txt").toString();
                break;
            case "vehicles.txt":
                filePath = folder.resolve("vehicles.txt").toString();
                break;
            case "clients.txt":
                filePath = folder.resolve("clients.txt").toString();
                break;
            case "reservations.txt":
                filePath = folder.resolve("reservations.txt").toString();
                break;
            case "travels.txt":
                filePath = folder.resolve("travels.txt").toString();
                break;
            default:
                System.out.println("Ficheiro não encontrado!");
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String[] line;
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine().split(";");
                if (line.length == 0 || (line.length == 1 && line[0].trim().isEmpty())) {
                    continue; // Skip empty lines
                }
                T newObjectReader = lineMapper.apply(line);
                if (newObjectReader != null) {
                    list.add(newObjectReader);
                } else {
                    System.out.println("⚠️ Aviso: Linha ignorada em " + fileName + " (objeto null ou dados inválidos)");
                }
            }
            System.out.println("Dados lidos com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        } catch (IOException e) {
            System.out.println("Alguma coisa correu mal!");
        }
    }

    /**
     * Generic method to add data to memory (ArrayList) without writing to file.
     *
     * @param <T>         The type of object being created.
     * @param list        The ArrayList to add the object to.
     * @param lineMapper  A function to map attributes to an object.
     * @param fileName    The identifier for the type of object (used for prompts).
     * @param isObjectNew Flag indicating if the object is new or an update.
     * @param reservation Optional reservation object for specific logic.
     */
    public static <T> void addData(ArrayList<T> list, Function<ArrayList<String>, T> lineMapper, String fileName,
            boolean isObjectNew, Reservation reservation) {

        ArrayList<String> attributesValues = new ArrayList<>();
        T newObject = null;

        switch (fileName) {
            case "drivers.txt":
                for (int i = 0; i < Driver.prompts().length; i++) {
                    System.out.print(Driver.prompts()[i]);
                    attributesValues.add(scanner.nextLine());
                }
                newObject = lineMapper.apply(attributesValues);
                break;
            case "vehicles.txt":
                for (int i = 0; i < Vehicle.prompts().length; i++) {
                    String value;
                    if (i == 0) {
                        while (true) {
                            System.out.print(Vehicle.prompts()[i]);
                            value = scanner.nextLine();
                            if (findVehicleByLicensePlate(value) != null) {
                                System.out.println(
                                        "❌ Erro: Viatura com matrícula " + value + " já existe! Tente novamente.");
                            } else {
                                break;
                            }
                        }
                    } else {
                        System.out.print(Vehicle.prompts()[i]);
                        value = scanner.nextLine();
                    }
                    attributesValues.add(value);
                }
                newObject = lineMapper.apply(attributesValues);
                break;
            case "clients.txt":
                for (int i = 0; i < Client.prompts().length; i++) {
                    String value;
                    if (i == 0) { // 0 is index for Name
                        while (true) {
                            System.out.print(Client.prompts()[i]);
                            value = scanner.nextLine().trim();
                            if (value.isEmpty()) {
                                System.out.println("❌ Erro: O nome do cliente não pode ser vazio! Tente novamente.");
                            } else {
                                break;
                            }
                        }
                    } else if (i == 1) { // 1 is index for NIF
                        while (true) {
                            System.out.print(Client.prompts()[i]);
                            value = scanner.nextLine().trim();
                            try {
                                long nifToCheck = Long.parseLong(value);
                                if (findByNIF(nifToCheck, clients, "clients") != null) {
                                    System.out.println(
                                            "❌ Erro: Cliente com NIF " + value + " já existe! Tente novamente.");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("❌ Erro: NIF deve ser numérico! Tente novamente.");
                            }
                        }
                    } else {
                        System.out.print(Client.prompts()[i]);
                        value = scanner.nextLine().trim();
                    }
                    attributesValues.add(value);
                }
                newObject = lineMapper.apply(attributesValues);
                break;
            case "reservations.txt":
                for (int i = 0; i < Reservation.prompts().length; i++) {
                    System.out.println(Reservation.prompts()[i]);
                    attributesValues.add(scanner.nextLine());
                }
                newObject = lineMapper.apply(attributesValues);
                break;
            case "travels.txt":
                if (isObjectNew) {
                    for (int i = 0; i < Travel.prompts().length; i++) {
                        System.out.print(Travel.prompts()[i]);
                        attributesValues.add(scanner.nextLine());
                    }
                    newObject = lineMapper.apply(attributesValues);
                } else {
                    // Specific logic for creating travel from reservation
                    System.out.print(Travel.prompts()[0]);
                    attributesValues.add(scanner.nextLine());

                    // Add existing values from reservation/context (handled by mapper, but we need
                    // to push to list to match index)
                    // The mapper expects specific indices. Let's see how the mapper is defined in
                    // the caller.
                    // Caller mapper for "travels.txt" from reservation (Case 6) uses:
                    // line.get(0) -> Driver NIF (Prompt 0)
                    // line.get(1) -> Vehicle Plate (Prompt 2 ?? wait, let's check prompts)

                    // Travel.prompts():
                    // 0: NIF Condutor
                    // 1: NIF Cliente
                    // 2: Matricula Veiculo
                    // 3: Data Inicio
                    // 4: Data Fim
                    // ...

                    // The caller in Case 6:
                    // line.get(0) -> driver NIF
                    // line.get(1) -> vehicle Key
                    // line.get(2) -> End Date
                    // line.get(3) -> Cost

                    // We need to collect these specific inputs.

                    // Prompt 2 is Vehicle License Plate
                    System.out.print(Travel.prompts()[2]); // Matricula
                    attributesValues.add(scanner.nextLine());

                    // Prompt 4 is End Date (Start Date comes from reservation)
                    System.out.print(Travel.prompts()[4]); // Data Fim
                    attributesValues.add(scanner.nextLine());

                    // Prompt 8 is Cost
                    System.out.println(Travel.prompts()[8]); // Custo
                    attributesValues.add(scanner.nextLine());

                    newObject = lineMapper.apply(attributesValues);
                }
                break;
        }

        if (newObject != null) {
            list.add(newObject);
            System.out.println("✅ Adicionado com sucesso! (Não se esqueça de gravar no menu 8)");
        } else {
            System.out.println("❌ Erro ao criar registo (dados inválidos ou duplicados).");
        }
    }

    /**
     * Searches for an object (Client or Driver) by its NIF.
     *
     * @param <T>          The type of object (must implement HasNif).
     * @param nif          The NIF to search for.
     * @param objects      The list of objects to search within.
     * @param typeOfObject A string indicating the type ("clients" or "drivers").
     * @return The object if found, otherwise null.
     */
    public static <T extends HasNif> T findByNIF(long nif, ArrayList<T> objects, String typeOfObject) {
        if (typeOfObject.equals("clients")) {
            for (T object : objects) {
                if (object.getClientNif() == nif) {
                    return object;
                }
            }
        } else if (typeOfObject.equals("drivers")) {
            for (T object : objects) {
                if (object.getDriverNif() == nif) {
                    return object;
                }
            }
        }
        return null;
    }

    /**
     * Searches for a vehicle by its license plate.
     *
     * @param licensePlate The license plate to search for.
     * @return The Vehicle object if found, otherwise null.
     */
    public static Vehicle findVehicleByLicensePlate(String licensePlate) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Searches for travels within a specific date range for a given client NIF.
     *
     * @param startDate    The start date of the range.
     * @param endDate      The end date of the range.
     * @param nif          The client NIF.
     * @param travelsFound The list to add found travels to.
     * @return The list of travels found.
     */
    public static ArrayList<Travel> findTravelByDate(LocalDateTime startDate, LocalDateTime endDate, long nif,
            ArrayList<Travel> travelsFound) {
        travelsFound.clear();
        for (Travel travel : travels) {
            if (nif == travel.getClientNif() && !travel.getStartDateTime().isBefore(startDate)
                    && !travel.getEndDateTime().isAfter(endDate)) {
                travelsFound.add(travel);
            }
        }
        return travelsFound;
    }

    // Método para gravar todos os dados nos ficheiros
    /**
     * Saves all in-memory data (drivers, vehicles, clients, reservations, travels)
     * to their respective files.
     */
    public static void saveAllDataToFiles() {
        infoTitle("💾", "GRAVAR DADOS NOS FICHEIROS");

        // Gravar drivers
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(folder.resolve("drivers.txt").toString())))) {
            for (Driver driver : drivers) {
                writer.print(driver.getName());
                writer.print(";");
                writer.print(driver.getNIC());
                writer.print(";");
                writer.print(driver.getDriverLicenseNumber());
                writer.print(";");
                writer.print(driver.getNiss());
                writer.print(";");
                writer.print(driver.getDriverNif());
                writer.print(";");
                writer.print(driver.getTlm());
                writer.print(";");
                writer.println(driver.getAddress());
            }
            System.out.println("✅ Condutores gravados com sucesso!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao gravar condutores: " + e.getMessage());
        }

        // Gravar vehicles
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(folder.resolve("vehicles.txt").toString())))) {
            for (Vehicle vehicle : vehicles) {
                writer.print(vehicle.getLicensePlate());
                writer.print(";");
                writer.print(vehicle.getBrand());
                writer.print(";");
                writer.print(vehicle.getModel());
                writer.print(";");
                writer.print(vehicle.getProductionYear());
                writer.print(";");
                writer.println(vehicle.getColor());
            }
            System.out.println("✅ Viaturas gravadas com sucesso!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao gravar viaturas: " + e.getMessage());
        }

        // Gravar clients
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(folder.resolve("clients.txt").toString())))) {
            for (Client client : clients) {
                writer.print(client.getName());
                writer.print(";");
                writer.print(client.getClientNif());
                writer.print(";");
                writer.print(client.getTlm());
                writer.print(";");
                writer.println(client.getAddress());
            }
            System.out.println("✅ Clientes gravados com sucesso!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao gravar clientes: " + e.getMessage());
        }

        // Gravar reservations
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(folder.resolve("reservations.txt").toString())))) {
            for (Reservation reservation : reservations) {
                // Verificar se o cliente existe
                if (reservation.getClient() == null) {
                    System.out.println("⚠️ Aviso: Reserva com cliente null foi ignorada ao gravar.");
                    continue;
                }
                writer.print(reservation.getClient().getClientNif());
                writer.print(";");
                writer.print(reservation.getStartDateTime().format(DATE_TIME_FORMATTER));
                writer.print(";");
                writer.print(reservation.getOriginAddress());
                writer.print(";");
                writer.print(reservation.getDestinationAddress());
                writer.print(";");
                writer.println(reservation.getKms());
            }
            System.out.println("✅ Reservas gravadas com sucesso!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao gravar reservas: " + e.getMessage());
        }

        // Gravar travels
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(folder.resolve("travels.txt").toString())))) {
            for (Travel travel : travels) {
                writer.print(travel.getDriver().getDriverNif());
                writer.print(";");
                writer.print(travel.getClient().getClientNif());
                writer.print(";");
                writer.print(travel.getVehicle().getLicensePlate());
                writer.print(";");
                writer.print(travel.getStartDateTime().format(DATE_TIME_FORMATTER));
                writer.print(";");
                writer.print(travel.getEndDateTime().format(DATE_TIME_FORMATTER));
                writer.print(";");
                writer.print(travel.getOriginAddress());
                writer.print(";");
                writer.print(travel.getDestinationAddress());
                writer.print(";");
                writer.print(travel.getKms());
                writer.print(";");
                writer.println(travel.getTripCost());
            }
            System.out.println("✅ Viagens gravadas com sucesso!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao gravar viagens: " + e.getMessage());
        }

        System.out.println("\n✅ Todos os dados foram gravados com sucesso!");
    }

    // Método para ler todos os dados dos ficheiros
    /**
     * Loads all data from files into memory (drivers, vehicles, clients,
     * reservations, travels).
     */
    public static void loadAllDataFromFiles() {
        infoTitle("📂", "LER DADOS DOS FICHEIROS");

        // Ler drivers
        readFiles(drivers, line -> new Driver(
                line[0],
                Long.parseLong(line[1]),
                line[2],
                Long.parseLong(line[3]),
                Long.parseLong(line[4]),
                line[5],
                line[6]), "drivers.txt");

        // Ler vehicles
        // Ler vehicles
        readFiles(vehicles, line -> new Vehicle(
                line[0],
                line[1],
                line[2],
                Integer.parseInt(line[3]),
                line.length > 4 ? line[4] : "Indefinido"), "vehicles.txt");

        // Ler clients
        // Ler clients
        readFiles(clients, line -> new Client(
                line[0],
                Long.parseLong(line[1]),
                line[2],
                line[3]), "clients.txt");

        // Ler reservations
        // Ler reservations
        readFiles(reservations, line -> {
            Client client = findByNIF(Long.parseLong(line[0]), clients, "clients");
            if (client == null) {
                System.out.println("❌ Cliente com NIF " + line[0] + " não encontrado para reserva");
                return null;
            }
            return new Reservation(
                    client,
                    LocalDateTime.parse(line[1], DATE_TIME_FORMATTER),
                    line[2],
                    line[3],
                    Double.parseDouble(line[4]));
        }, "reservations.txt");

        // Ler travels
        // Ler travels
        readFiles(travels, line -> new Travel(
                findByNIF(Long.parseLong(line[0]), drivers, "drivers"),
                findByNIF(Long.parseLong(line[1]), clients, "clients"),
                findVehicleByLicensePlate(line[2]),
                LocalDateTime.parse(line[3], DATE_TIME_FORMATTER),
                LocalDateTime.parse(line[4], DATE_TIME_FORMATTER),
                line[5],
                line[6],
                Double.parseDouble(line[7]),
                Double.parseDouble(line[8])), "travels.txt");

        System.out.println("\n✅ Todos os dados foram lidos com sucesso!");
    }

    public static LocalDate readDate(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input, DATE_FORMATTER);
            } catch (Exception e) {
                System.out.println("❌ Formato inválido! Use o formato dd/MM/yyyy (ex: 26/10/2023)");
            }
        }
    }
}
