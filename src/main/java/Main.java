import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.lang.reflect.*;

public class Main {
    public static String fileName;
    public static String filePath;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    static public Scanner scanner = new Scanner(System.in);
    public static ArrayList<Driver> drivers = new ArrayList<>();
    public static ArrayList<Vehicle> vehicles = new ArrayList<>();
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<Travel> travels = new ArrayList<>();
    public static String[] line;
    public static ArrayList<String> attributeValues;

    public static void Header(){
        System.out.println("=========================================");
        System.out.println("       🚗 SISTEMA DE GESTÃO TVDE 📊       ");
        System.out.println("=========================================");

    }

    public static void MainMenu(){
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
        System.out.print("Selecione uma opção: ");
    }


    public static void main(String[] args){

        boolean isFirstUse = true;

        if(isFirstUse) {
            Header();
            System.out.println("\n👋 Olá! Eu sou o seu novo Gestor TVDE.");
            System.out.println("Para começarmos a trabalhar, escolha uma das opções abaixo:");
            System.out.println("-----------------------------------------");
            System.out.println("  [1] 📂 Ler dados de ficheiros existentes");
            System.out.println("  [2] 📝 Iniciar um novo negócio (Gravar dados)");
            System.out.println("-----------------------------------------");
            System.out.print("Opção desejada: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    drivers = readFiles(line -> new Driver(
                            line[0],
                            Integer.parseInt(line[1]),
                            line[2],
                            Long.parseLong(line[3]),
                            Integer.parseInt(line[4]),
                            line[5],
                            line[6]
                    ), "drivers.txt");

                    vehicles = readFiles(line -> new Vehicle(
                            line[0],
                            line[1],
                            line[2],
                            Integer.parseInt(line[3])
                    ), "vehicles.txt");

                    clients = readFiles(line -> new Client(
                            line[0],
                            Integer.parseInt(line[1]),
                            line[2],
                            line[3]
                    ), "clientes.txt");

                    reservations = readFiles(line -> new Reservation(
                            findClientByNIF(line, "reservations.txt"),
                            LocalDateTime.parse(line[1], FORMATTER),
                            line[2],
                            line[3],
                            Double.parseDouble(line[4])
                    ), "reservations.txt");

                    travels = readFiles(line -> new Travel(
                            findDriverByNIF(line),
                            findClientByNIF(line,"travels.txt"),
                            findVehiculeBylicensePlate(line),
                            LocalDateTime.parse(line[3], FORMATTER),
                            LocalDateTime.parse(line[4], FORMATTER),
                            line[5],
                            line[6],
                            Double.parseDouble(line[7]),
                            Double.parseDouble(line[8])
                    ), "travels.txt");

                case 2:
                    drivers = writeFiles(line -> new Driver(
                            attributeValues.get(0),
                            Integer.parseInt(attributeValues.get(1)),
                            attributeValues.get(2),
                            Long.parseLong(attributeValues.get(3)),
                            Integer.parseInt(attributeValues.get(4)),
                            attributeValues.get(5),
                            attributeValues.get(6)
                    ), "drivers.txt");

                    vehicles = writeFiles(line -> new Vehicle(
                            attributeValues.get(0),
                            attributeValues.get(1),
                            attributeValues.get(2),
                            Integer.parseInt(attributeValues.get(3))
                    ), "vehicles.txt");

                    clients = writeFiles(line -> new Client(
                            attributeValues.get(0),
                            Integer.parseInt(attributeValues.get(1)),
                            attributeValues.get(2),
                            attributeValues.get(3)
                    ), "clientes.txt");

                    reservations = writeFiles(line -> new Reservation(
                            findClientByNIF(attributeValues.toArray(new String[10]), "reservations.txt"),
                            LocalDateTime.parse(attributeValues.get(1), FORMATTER),
                            attributeValues.get(2),
                            attributeValues.get(3),
                            Double.parseDouble(attributeValues.get(4))
                    ), "reservations.txt");

                    travels = writeFiles(line -> new Travel(
                            findDriverByNIF(attributeValues.toArray(new String[10])),
                            findClientByNIF(attributeValues.toArray(new String[10]),"travels.txt"),
                            findVehiculeBylicensePlate(attributeValues.toArray(new String[10])),
                            LocalDateTime.parse(attributeValues.get(3), FORMATTER),
                            LocalDateTime.parse(attributeValues.get(4), FORMATTER),
                            attributeValues.get(5),
                            attributeValues.get(6),
                            Double.parseDouble(attributeValues.get(7)),
                            Double.parseDouble(attributeValues.get(8))
                    ), "travels.txt");

            }
        }
        else{
            Header();

            System.out.println("\n👋 Bem-vindo/a de volta!");
            System.out.println("Em que posso ajudar hoje?");

        }
    }

    //Ver no yt ou usando a IA o que é Function e como usar!
    static <T> ArrayList<T> readFiles(Function<String[], T> lineMapper, String fileName){
        ArrayList<T> list = new ArrayList<>();
        System.out.print("Insira o caminho do ficheiro onde tem os dados do seu " + fileName + ": " );
        filePath = scanner.nextLine();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String [] line;
            while (bufferedReader.ready()){
                line = bufferedReader.readLine().split(";");
                T newObjectReader = lineMapper.apply(line);
                list.add(newObjectReader);
            }
            System.out.println("Dados lidos com sucesso!");
        }
        catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        }
        catch (IOException e){
            System.out.println("Alguma coisa correu mal!");
        }
        return list;
    }

    static <T> ArrayList<T> writeFiles(Function<ArrayList<String>, T> lineMapper, String fileName){
        ArrayList<T> list = new ArrayList<>();
        System.out.print("Insira o caminho do ficheiro " + fileName + "onde deseja imprimir os dados");
        filePath = scanner.nextLine();
        try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))){
            ArrayList<String> attributesValues = new ArrayList<>();
            T newObjectWriter = null;
            switch (fileName) {
                case "drivers":
                    System.out.println(Driver.prompts()[0]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print("");
                    System.out.println(Driver.prompts()[1]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print("");
                    System.out.println(Driver.prompts()[2]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print("");
                    System.out.println(Driver.prompts()[3]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print("");
                    System.out.println(Driver.prompts()[4]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(4));
                    printWriter.print("");
                    System.out.println(Driver.prompts()[5]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(5));
                    printWriter.print("");
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "vehicles":
                    System.out.println(Vehicle.prompts()[0]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print("");
                    System.out.println(Vehicle.prompts()[1]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print("");
                    System.out.println(Vehicle.prompts()[2]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print("");
                    System.out.println(Vehicle.prompts()[3]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print("");
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "clientes":
                    System.out.println(Client.prompts()[0]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print("");
                    System.out.println(Client.prompts()[1]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print("");
                    System.out.println(Client.prompts()[2]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print("");
                    System.out.println(Client.prompts()[3]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print("");
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "reservations":
                    System.out.println(Reservation.prompts()[0]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print("");
                    System.out.println(Reservation.prompts()[1]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print("");
                    System.out.println(Reservation.prompts()[2]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print("");
                    System.out.println(Reservation.prompts()[3]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print("");
                    System.out.println(Reservation.prompts()[4]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(4));
                    printWriter.print("");
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "travels":
                    System.out.println(Travel.prompts()[0]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print("");
                    System.out.println(Travel.prompts()[1]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print("");
                    System.out.println(Travel.prompts()[2]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print("");
                    System.out.println(Travel.prompts()[3]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print("");
                    System.out.println(Travel.prompts()[4]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(4));
                    printWriter.print("");
                    System.out.println(Travel.prompts()[5]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(5));
                    printWriter.print("");
                    System.out.println(Travel.prompts()[6]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(6));
                    printWriter.print("");
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                default:
                    System.out.println("nome de ficheiro incorreto!");
            }System.out.println("Dados escritos com sucesso!");
        }
        catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        }
        catch (IOException e){
            System.out.println("Alguma coisa correu mal!");
        }
        return list;
    }

    public static Client findClientByNIF(String [] line, String fileName){
        if (fileName == "reservations.txt")
        for (Client client : clients){
            if(client.getNif() == Integer.parseInt(line[0])){
                return  client;
            }
        }
        else{
            for (Client client : clients){
                if(client.getNif() == Integer.parseInt(line[1])){
                    return  client;
                }
            }
        }
        return null;
    }

    public static Driver findDriverByNIF(String [] line){
        for (Driver driver : drivers){
            if (driver.getNif() == Integer.parseInt(line[0])){
                return  driver;
            }
        }
        return null;
    }

    public static Vehicle findVehiculeBylicensePlate(String[] line){
        for (Vehicle vehicle : vehicles){
            if(vehicle.getLicensePlate().equals(line[2])){
                return  vehicle;
            }
        }
        return null;
    }
}

