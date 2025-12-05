public class Main {
    public static void main(String[] args){

        boolean isFirstUse = false;

        if(isFirstUse) {
            Header();
        }
        else{
            System.out.println("----------------------------------");
            System.out.println("SISTEMA DE GESTÃO DE NEGÓCIO TVDE");
            System.out.println("----------------------------------");

            System.out.println("\n👋 Bem-vindo/a de volta!");
            System.out.println("Em que posso ajudar hoje? (Digite 'menu' para opções)");
        }

    }
    static void Header(){
        System.out.println("=========================================");
        System.out.println("       🚗 SISTEMA DE GESTÃO TVDE 📊       ");
        System.out.println("=========================================");

    }

    static void FirstMenu() {
        System.out.println("\n👋 Olá! Eu sou o seu novo Gestor TVDE.");
        System.out.println("Para começarmos a trabalhar, escolha uma das opções abaixo:");
        System.out.println("-----------------------------------------");
        System.out.println("  [1] 📂 Ler dados de ficheiros existentes");
        System.out.println("  [2] 📝 Iniciar um novo negócio (Criar ficheiros)");
        System.out.println("-----------------------------------------");
    }
}

