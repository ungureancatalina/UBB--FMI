package ui;

import domain.Utilizator;
import domain.validator.ValidationException;
import service.ComunitateService;
import service.UtilizatorService;
import domain.Prietenie;

import java.util.Scanner;
import java.util.List;

public class Console
{
    private UtilizatorService utilizatorService;
    private ComunitateService comunitateService;

    public Console(UtilizatorService utilizatorService)
    {
        this.utilizatorService = utilizatorService;
        this.comunitateService = new ComunitateService(utilizatorService);
    }

    void meniu()
    {
        System.out.println("Meniu <3:");
        System.out.println("1. Adauga utilizator");
        System.out.println("2. Sterge utilizator");
        System.out.println("3. Adauga prietenie");
        System.out.println("4. Sterge prietenie");
        System.out.println("5. Comunitatea cea mai sociabila");
        System.out.println("6. Afisare utilizatori");
        System.out.println("7. Afisare prietenie");
        System.out.println("8. Afisare comunitati");
        System.out.println("9. Iesire din aplicatie");
    }

    public void run()
    {
        Scanner sc = new Scanner(System.in);
        boolean ok=true;
        meniu();
        while (ok)
        {
            System.out.print("Ce optiune alegeti?  ");
            String input = sc.nextLine();
            switch (input)
            {
                case "1": {
                    addUtilizator();
                    break;
                }
                case "2": {
                    removeUtilizator();
                    break;
                }
                case "3": {
                    addPrietenie();
                    break;
                }
                case "4": {
                    removePrietenie();
                    break;
                }
                case "5": {
                    comunitateSociabila();
                    break;
                }
                case "6": {
                    printUtilizatori();
                    break;
                }
                case "7": {
                    printPrietenie();
                    break;
                }
                case "8": {
                    printComunitate();
                    break;
                }
                case "9": {
                    System.out.println("Iesire din aplicatie <3");
                    ok=false;
                    break;
                }
                default: {
                    System.out.println("Optiune incorecta");
                    break;
                }
            }
        }
    }

    void printUtilizatori()
    {
        System.out.println("Utilizatorii sunt: ");
        for(Utilizator utilizator: utilizatorService.getUtilizator())
            System.out.println("Id: " + utilizator.getId()+ " Prenume: "+ utilizator.getFirstName()+ " Nume: "+ utilizator.getLastName());
    }

    void printPrietenie()
    {
        for(Utilizator utilizator: utilizatorService.getUtilizator())
        {
            boolean k=false;
            System.out.println("Prietenii utilizatorului: "+ utilizator.getFirstName()+" "+ utilizator.getLastName()+ " sunt: ");
            if(utilizator.getFriends()!=null)
                for(Utilizator prieten: utilizator.getFriends()) {
                    System.out.println("Id: " + prieten.getId() + ", " + prieten.getFirstName() + " " + prieten.getLastName());
                    k=true;
                }
            if(k==false)
            {
                System.out.println(" nu sunt utilizatori de afisat ");
            }
        }
    }

    void printComunitate()
    {
        System.out.println("Comunitatea este formata din: ");
        int nr= comunitateService.connectComunitate();
        System.out.println("Numarul de comunitati existente este: "+ nr);
    }

    void addUtilizator()
    {
        System.out.println("Adaugati un utilizator!");
        Scanner sc = new Scanner(System.in);
        System.out.println("Prenume: ");
        String prenume = sc.nextLine();
        System.out.println("Nume: ");
        String nume = sc.nextLine();
        try {
            utilizatorService.addUtilizator(new Utilizator(prenume, nume));
        }
        catch (ValidationException e)
        {
            System.out.println("Date invalide");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Argument invalid");
        }
    }
    void removeUtilizator()
    {
        System.out.println("Stergeti un utilizator!");
        printUtilizatori();
        Scanner sc = new Scanner(System.in);
        System.out.println("Id: ");
        String nr = sc.nextLine();
        try {
            Long id=Long.parseLong(nr);
            Utilizator utilizator=utilizatorService.removeUtilizator(id);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Argument invalid");
        }
    }

    void addPrietenie()
    {
        System.out.println("Adaugati o prietenie!");
        Scanner sc = new Scanner(System.in);
        System.out.println("Id utilizator1: ");
        String id1 = sc.nextLine();
        System.out.println("Id utilizator2: ");
        String id2 = sc.nextLine();
        try
        {
            Long i1=0L,i2=0L;
            try
            {
                i1=Long.parseLong(id1);
                i2=Long.parseLong(id2);
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Argument invalid");
            }
            utilizatorService.addPrietenie(new Prietenie(i1,i2));
        }
        catch (ValidationException e)
        {
            System.out.println("Date invalide");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Argument invalid");
        }
    }

    void removePrietenie()
    {
        System.out.println("Stergeti o prietenie!");
        printPrietenie();
        Scanner sc = new Scanner(System.in);
        System.out.println("Id utilizator1: ");
        String id1 = sc.nextLine();
        System.out.println("Id utilizator2: ");
        String id2 = sc.nextLine();
        try
        {
            Long i1=0L,i2=0L;
            try
            {
                i1=Long.parseLong(id1);
                i2=Long.parseLong(id2);
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("Argument invalid");
            }
            utilizatorService.removePrietenie(i1,i2);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Argument invalid");
        }
    }

    void comunitateSociabila()
    {
        System.out.println("Comunitatea cea mai sociabila este formata din: ");
        List<Long> com= comunitateService.comunitateSociabila();
        com.forEach(System.out::println);

    }
}
