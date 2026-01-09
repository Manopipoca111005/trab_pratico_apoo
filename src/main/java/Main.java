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
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
        System.out.print("Digite o nome da empresa:");
        System.out.println("=========================================");
        folder = Paths.get(scanner.nextLine());
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder)) {
            for (Path file : stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao ler a pasta: " + e.getMessage());
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
        int nif;
        String licensePlate;

        do {
            Menu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    Driver.Menu();
                    option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            drivers = writeFiles(line -> new Driver(
                                    line.get(0),
                                    Integer.parseInt(line.get(1)),
                                    line.get(2),
                                    Long.parseLong(line.get(3)),
                                    Integer.parseInt(line.get(4)),
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
                            nif = scanner.nextInt();
                            travelFound = findByNIF(nif, travels, "clients");
                            System.out.println(travelFound);
                            break;
                        case 4:
                            System.out.print("Digite o número do cartão de cidadão do condutor: ");
                            nif = scanner.nextInt();
                            scanner.nextLine();
                            driverFound = findByNIF(nif, drivers, "drivers");
                            System.out.print(Driver.prompts()[0]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setName(value);
                            }
                            System.out.print(Driver.prompts()[1]);
                            if (!value.isEmpty()) {
                                driverFound.setNIC(Integer.parseInt(value));
                            }
                            System.out.print(Driver.prompts()[2]);
                            if (!value.isEmpty()) {
                                driverFound.setDriverLicenseNumber(value);
                            }
                            System.out.print(Driver.prompts()[3]);
                            if (!value.isEmpty()) {
                                driverFound.setNiss(Long.parseLong(value));
                            }
                            System.out.print(Driver.prompts()[4]);
                            if (!value.isEmpty()) {
                                driverFound.setNif(Integer.parseInt(value));
                            }
                            System.out.print(Driver.prompts()[5]);
                            if (!value.isEmpty()) {
                                driverFound.setTlm(value);
                            }
                            System.out.print(Driver.prompts()[6]);
                            if (!value.isEmpty()) {
                                driverFound.setAddress(value);
                            }
                            break;
                        case 5:
                            filePath = "drivers.txt";
                            line = null;
                            System.out.print("🪪Digite o número de identificação fiscal do condutor: ");
                            nif = scanner.nextInt();
                            scanner.nextLine();
                            driverFound = findByNIF(nif, drivers, "drivers");
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
                            Driver.Menu();
                            break;
                    }
                    break;
                case 2:
                    Vehicle.Menu();
                    option = scanner.nextInt();
                    scanner.nextLine();
                    switch (option) {
                        case 1:
                            vehicles = writeFiles(line -> new Vehicle(
                                    line.get(0),
                                    line.get(1),
                                    line.get(2),
                                    Integer.parseInt(line.get(3))), "vehicles.txt", true, null);
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
                        case 4:
                            System.out.print("🔢Digite a matricula do veiculo: ");
                            licensePlate = scanner.nextLine();
                            vehicleFound = findVehicleByLicensePlate(licensePlate);
                            System.out.print(Driver.prompts()[0]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                vehicleFound.setLicensePlate(value);
                            }
                            System.out.print(Driver.prompts()[1]);
                            if (!value.isEmpty()) {
                                vehicleFound.setBrand(value);
                            }
                            System.out.print(Driver.prompts()[2]);
                            if (!value.isEmpty()) {
                                vehicleFound.setModel(value);
                            }
                            System.out.print(Driver.prompts()[3]);
                            if (!value.isEmpty()) {
                                vehicleFound.setProductionYear(Integer.parseInt(value));
                            }
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
                            Vehicle.Menu();
                            break;
                    }
                    break;
                case 3:
                    Client.Menu();
                    option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            clients = writeFiles(line -> new Client(
                                    line.get(0),
                                    Integer.parseInt(line.get(1)),
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
                            nif = scanner.nextInt();
                            clientFound = findByNIF(nif, clients, "clients");
                            System.out.println(clientFound);
                            break;
                        case 4:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextInt();
                            scanner.nextLine();
                            clientFound = findByNIF(nif, clients, "clients");
                            System.out.print(Client.prompts()[0]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                clientFound.setName(value);
                            }
                            System.out.print(Driver.prompts()[1]);
                            if (!value.isEmpty()) {
                                clientFound.setNif(Integer.parseInt(value));
                            }
                            System.out.print(Driver.prompts()[2]);
                            if (!value.isEmpty()) {
                                clientFound.setTlm(value);
                            }
                            System.out.print(Driver.prompts()[3]);
                            if (!value.isEmpty()) {
                                clientFound.setAddress(value);
                            }
                            break;
                        case 5:
                            filePath = "clients.txt";
                            line = null;
                            System.out.print("🪪Digite o número do identificação fiscal do cliente: ");
                            nif = scanner.nextInt();
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
                            Client.Menu();
                            break;
                    }
                case 4:
                    Reservation.Menu();
                    option = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer
                    switch (option) {
                        case 1:
                            infoTitle("➕", "ADICIONAR RESERVA");
                            ArrayList<String> reservationAttributes = new ArrayList<>();
                            for (int i = 0; i < Reservation.prompts().length; i++) {
                                System.out.print(Reservation.prompts()[i]);
                                reservationAttributes.add(scanner.nextLine());
                            }
                            clientFound = findByNIF(Integer.parseInt(reservationAttributes.get(0)), clients, "clients");
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
                            nif = scanner.nextInt();
                            infoTitle("📋", "RESERVAS DO CLIENTE NIF: " + nif);
                            for (Reservation reservation : reservations) {
                                if (reservation.getClientNif() == nif) {
                                    System.out.println(reservation);
                                }
                            }
                            break;
                        case 4:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextInt();
                            scanner.nextLine();
                            findByNIF(nif, reservations, "clients");
                            reservationFound = findByNIF(nif, reservations, "clients");
                            System.out.print(Client.prompts()[0]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                reservationFound.setClient(findByNIF(Integer.parseInt(value), clients, "clients"));
                            }
                            System.out.print(Driver.prompts()[1]);
                            if (!value.isEmpty()) {
                                reservationFound.setStartDateTime(LocalDateTime.parse(value));
                            }
                            System.out.print(Driver.prompts()[2]);
                            if (!value.isEmpty()) {
                                reservationFound.setOriginAddress(value);
                            }
                            System.out.print(Driver.prompts()[3]);
                            if (!value.isEmpty()) {
                                reservationFound.setDestinationAddress(value);
                            }
                            System.out.println(Driver.prompts()[4]);
                            if (!value.isEmpty()) {
                                reservationFound.setKms(Double.parseDouble(value));
                            }
                            break;
                        case 5:
                            filePath = "clients.txt";
                            line = null;
                            System.out.print("🪪Digite o número do identificação fiscal do cliente: ");
                            nif = scanner.nextInt();
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
                            Reservation.Menu();
                            break;
                    }
                    break;
                case 5:
                    Travel.Menu();
                    option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            travels = writeFiles(line -> {
                                try {
                                    driverFound = findByNIF(Integer.parseInt(line.get(0)), drivers, "drivers");
                                    clientFound = findByNIF(Integer.parseInt(line.get(1)), clients, "clients");
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
                                                findByNIF(Integer.parseInt(line.get(0)), drivers, "drivers"),
                                                findByNIF(Integer.parseInt(line.get(1)), clients, "clients"),
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
                            nif = scanner.nextInt();
                            travelFound = findByNIF(nif, travels, "clients");
                            System.out.println(travelFound);
                            break;
                        case 4:
                            System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                            nif = scanner.nextInt();
                            scanner.nextLine();
                            travelFound = findByNIF(nif, travels, "clients");
                            System.out.print(Travel.prompts()[0]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                travelFound.setDriver(findByNIF(Integer.parseInt(value), drivers, "drivers"));
                            }
                            System.out.print(Travel.prompts()[1]);
                            if (!value.isEmpty()) {
                                travelFound.setClient(findByNIF(Integer.parseInt(value), clients, "clients"));
                            }
                            System.out.print(Travel.prompts()[2]);
                            if (!value.isEmpty()) {
                                travelFound.setVehicle(findVehicleByLicensePlate(value));
                            }
                            System.out.print(Travel.prompts()[3]);
                            if (!value.isEmpty()) {
                                travelFound.setStartDateTime(LocalDateTime.parse(value));
                            }
                            System.out.println(Travel.prompts()[4]);
                            if (!value.isEmpty()) {
                                travelFound.setEndDateTime(LocalDateTime.parse(value));
                            }
                            System.out.println(Travel.prompts()[5]);
                            if (!value.isEmpty()) {
                                travelFound.setOriginAddress(value);
                            }
                            System.out.println(Travel.prompts()[6]);
                            if (!value.isEmpty()) {
                                travelFound.setDestinationAddress(value);
                            }
                            System.out.println(Travel.prompts()[7]);
                            if (!value.isEmpty()) {
                                travelFound.setKms(Double.parseDouble(value));
                            }
                            System.out.println(Travel.prompts()[8]);
                            if (!value.isEmpty()) {
                                travelFound.setTripCost(Double.parseDouble(value));
                            }
                            break;
                        case 5:
                            filePath = "clients.txt";
                            line = null;
                            System.out.print("🪪Digite o número do identificação fiscal do cliente: ");
                            nif = scanner.nextInt();
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
                            Reservation.Menu();
                            break;
                    }
                case 6:
                    System.out.print("🪪Digite o número de identificação fiscal do cliente: ");
                    nif = scanner.nextInt();
                    reservationFound = findByNIF(nif, reservations, "clients");
                    travels = writeFiles(line -> new Travel(
                            findByNIF(Integer.parseInt(line.get(0)), drivers, "drivers"),
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
                    option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            System.out.print(Travel.prompts()[1]);
                            nif = scanner.nextInt();
                            System.out.print("Digite a data inicial: ");
                            LocalDate startDate = LocalDate.parse(scanner.next(), DATE_FORMATTER);
                            System.out.print("Digite a data final: ");
                            LocalDate endDate = LocalDate.parse(scanner.next(), DATE_FORMATTER);
                            findTravelByDate(startDate, endDate, nif, travelsFound);
                            for (Travel travel : travelsFound) {
                                System.out.println(travel);
                            }
                            break;
                        case 2:
                            System.out.print("🔢Digite a placa da viatura: ");
                            scanner.nextLine(); // Limpar buffer
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
                        case 3:
                            System.out.println("Digite o NIF do condutor: ");
                            nif = scanner.nextInt();
                            System.out.print("Digite a data inicial: ");
                            startDate = LocalDate.parse(scanner.next(), DATE_FORMATTER);
                            System.out.print("Digite a data final: ");
                            endDate = LocalDate.parse(scanner.next(), DATE_FORMATTER);
                            foundAny = false;
                            double tripCost = 0;
                            for (Travel travel : travels) {
                                if (travel.getDriver().getClientNif() == nif &&
                                        !travel.getStartDateTime().toLocalDate().isBefore(startDate) &&
                                        !travel.getEndDateTime().toLocalDate().isAfter(endDate)) {
                                    tripCost += travel.getTripCost();
                                    foundAny = true;
                                }
                            }
                            System.out.println("Total faturado: " + tripCost);
                            if (!foundAny) {
                                System.out.println("❌ Nenhum condutor encontrado para este intervalo de datas.");
                            }
                            break;
                        case 4:
                            System.out.print("📅 Digite a data inicial (dd/MM/yyyy): ");
                            scanner.nextLine(); // Limpar buffer
                            startDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
                            System.out.print("📅 Digite a data final (dd/MM/yyyy): ");
                            endDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
                            foundAny = false;
                            double averageDistance = 0;
                            int travelCount = 0;
                            for (Travel travel : travels) {
                                if (!travel.getStartDateTime().toLocalDate().isBefore(startDate) &&
                                        !travel.getEndDateTime().toLocalDate().isAfter(endDate)) {
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
                        case 5:
                            System.out.print("📅 Digite a data inicial (dd/MM/yyyy): ");
                            scanner.nextLine();
                            String startDateStr = scanner.nextLine();
                            startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
                            System.out.print("📅 Digite a data final (dd/MM/yyyy): ");
                            String endDateStr = scanner.nextLine();
                            endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);
                            infoTitle("📍", "DESTINO MAIS SOLICITADO NO PERÍODO");
                            HashMap<String, Integer> destinationCount = new HashMap<>();
                            for (Reservation reservation : reservations) {
                                LocalDate reservationDate = reservation.getStartDateTime().toLocalDate();
                                if (!reservationDate.isBefore(startDate) && !reservationDate.isAfter(endDate)) {
                                    String destination = reservation.getDestinationAddress();
                                    destinationCount.put(destination,
                                            destinationCount.getOrDefault(destination, 0) + 1);
                                }
                            }
                            for (Travel travel : travels) {
                                LocalDate travelDate = travel.getStartDateTime().toLocalDate();
                                if (!travelDate.isBefore(startDate) && !travelDate.isAfter(endDate)) {
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
    static <T> ArrayList<T> readFiles(Function<String[], T> lineMapper, String fileName) {
        ArrayList<T> list = new ArrayList<>();
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
        return list;
    }

    /**
     * Generic method to write data to a file.
     * Supports appending or creating new records.
     *
     * @param <T>         The type of object being written (though return is list of
     *                    T).
     * @param lineMapper  A function to map attributes to an object.
     * @param fileName    The name of the destination file.
     * @param isObjectNew Flag indicating if the object is new or an update.
     * @param reservation Optional reservation object for specific logic.
     * @return An ArrayList of the objects processed.
     */
    public static <T> ArrayList<T> writeFiles(Function<ArrayList<String>, T> lineMapper, String fileName,
            boolean isObjectNew, Reservation reservation) {
        ArrayList<T> list = new ArrayList<>();
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
                return list;
        }

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            File file = new File(filePath);
            if (file.length() > 0) {
                printWriter.println();
            }
            ArrayList<String> attributesValues = new ArrayList<>();
            T newObjectWriter = null;
            switch (filePath) {
                case "drivers.txt":
                    for (int i = 0; i < Driver.prompts().length; i++) {
                        System.out.print(Driver.prompts()[i]);
                        attributesValues.add(scanner.nextLine());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "vehicles.txt":
                    for (int i = 0; i < Vehicle.prompts().length; i++) {
                        System.out.print(Vehicle.prompts()[i]);
                        System.out.flush();
                        attributesValues.add(scanner.next());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                        printWriter.flush();
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "clients.txt":
                    for (int i = 0; i < Client.prompts().length; i++) {
                        System.out.print(Client.prompts()[i]);
                        System.out.flush();
                        attributesValues.add(scanner.next());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                        System.out.flush();
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "reservations.txt":
                    for (int i = 0; i < Reservation.prompts().length; i++) {
                        System.out.println(Reservation.prompts()[i]);
                        System.out.flush();
                        attributesValues.add(scanner.next());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                        System.out.flush();
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "travels.txt":
                    if (isObjectNew) {
                        for (int i = 0; i < Travel.prompts().length; i++) {
                            System.out.print(Travel.prompts()[i]);
                            System.out.flush();
                            attributesValues.add(scanner.next());
                            printWriter.print(attributesValues.get(i));
                            printWriter.print(";");
                            System.out.flush();
                        }
                        printWriter.println();
                        newObjectWriter = lineMapper.apply(attributesValues);
                        list.add(newObjectWriter);
                    }
                    System.out.print(Travel.prompts()[0]);
                    System.out.flush();
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print(";");
                    printWriter.print(reservation.getClientNif());
                    printWriter.print(";");
                    System.out.print(Travel.prompts()[2]);
                    System.out.flush();
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print(";");
                    printWriter.print(reservation.getStartDateTime());
                    printWriter.print(";");
                    System.out.print(Travel.prompts()[4]);
                    System.out.flush();
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print(";");
                    printWriter.print(reservation.getOriginAddress());
                    printWriter.print(";");
                    printWriter.print(reservation.getDestinationAddress());
                    printWriter.print(";");
                    printWriter.print(reservation.getKms());
                    System.out.println(Travel.prompts()[8]);
                    System.out.flush();
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(3));
                    break;
            }
            System.out.println("Dados escritos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever ou ficheiro não encontrado/acessível! Detalhes: " + e.getMessage());
        }

        return list;
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
    public static <T extends HasNif> T findByNIF(int nif, ArrayList<T> objects, String typeOfObject) {
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
    public static ArrayList<Travel> findTravelByDate(LocalDate startDate, LocalDate endDate, int nif,
            ArrayList<Travel> travelsFound) {
        for (Travel travel : travels) {
            if (nif == travel.getClientNif() && !travel.getStartDateTime().toLocalDate().isBefore(startDate)
                    && !travel.getEndDateTime().toLocalDate().isAfter(endDate)) {
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
                writer.println(vehicle.getProductionYear());
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
        drivers = readFiles(line -> new Driver(
                line[0],
                Integer.parseInt(line[1]),
                line[2],
                Long.parseLong(line[3]),
                Integer.parseInt(line[4]),
                line[5],
                line[6]), "drivers.txt");

        // Ler vehicles
        vehicles = readFiles(line -> new Vehicle(
                line[0],
                line[1],
                line[2],
                Integer.parseInt(line[3])), "vehicles.txt");

        // Ler clients
        clients = readFiles(line -> new Client(
                line[0],
                Integer.parseInt(line[1]),
                line[2],
                line[3]), "clients.txt");

        // Ler reservations
        reservations = readFiles(line -> {
            Client client = findByNIF(Integer.parseInt(line[0]), clients, "clients");
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
        travels = readFiles(line -> new Travel(
                findByNIF(Integer.parseInt(line[0]), drivers, "drivers"),
                findByNIF(Integer.parseInt(line[1]), clients, "clients"),
                findVehicleByLicensePlate(line[2]),
                LocalDateTime.parse(line[3], DATE_TIME_FORMATTER),
                LocalDateTime.parse(line[4], DATE_TIME_FORMATTER),
                line[5],
                line[6],
                Double.parseDouble(line[7]),
                Double.parseDouble(line[8])), "travels.txt");

        System.out.println("\n✅ Todos os dados foram lidos com sucesso!");
    }
}
