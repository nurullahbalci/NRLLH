import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class IOApp {
    static String filename= "transactions.txt";
    static int limitRecord=100;
    static Record[] entries;
    static int lastIndex;
    static class Record
    {
        String name ;
        Integer price, number;
    }
    public static void main(String[] args) throws IOException {
        initialProcess();


        showMenu();



    }
    private static void showMenu() throws IOException {
        System.out.println("-------------------MENU-------------------");
        System.out.println("-----------1- ADD A PRODUCT --------------");
        System.out.println("-----------2- REMOVE A PRODUCT -----------");
        System.out.println("-----------3- SEARCH A PRODUCT -----------");
        System.out.println("-----------4- LIST ALL PRODUCTS ----------");
        System.out.println("-----------5- DELETE ALL PRODUCTS---------");
        System.out.println("-----------6- CLOSE THE APP---------------");
        System.out.println("Please select an operation.");

        Scanner in = new Scanner(System.in);
        int selection = in.nextInt();
        switch (selection) {
            case 1 -> {
                System.out.println("Please enter name of the Product");
                String name = in.next();
                System.out.println("Please enter its price");
                int price = in.nextInt();
                System.out.println("How many do you have? " +
                        "Please enter a number for it");
                int number = in.nextInt();
                addRecord(name, price, number);
                update();
                System.out.println("Product is added successfully to the system");
                showMenu();
            }
            case 2 -> {
                System.out.println("Please enter name of the Product");
                String name = in.next();
                if (removeRecord(name)) {
                    lastIndex--;
                    update();
                    System.out.println("Product is removed from system.");
                } else
                    System.out.println("Product is not available.");
                showMenu();
            }
            case 3 -> {
                System.out.println("Please enter name of the Product");
                String name = in.next();
                searchRecord(name);
                showMenu();
            }
            case 4 -> {
                listRecord();
                showMenu();
            }
            case 5 -> {
                System.out.println("If you do this operation all of the records will be deleted. Are you sure ? y/n");
                String ask = in.next();
                if (ask.equals("y"))
                    deleteAll();
                showMenu();
            }
            case 6 -> System.exit(0);
        }
    }
    private static void listRecord()
    {
        if (lastIndex == 0)
            System.out.println("There is no product to list!");

        for   (int i = 0; i<lastIndex ; i++) {
            System.out.println(entries[i].name + " " + entries[i].number + " " + entries[i].price);
        }


    }


    private static void addRecord(String name, Integer price, Integer number)
    {

        Record newRecord = new Record();
        newRecord.name=name;
        newRecord.price=price;
        newRecord.number=number;

        entries[lastIndex]=newRecord;
        lastIndex++;
    }
    private static void update() throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(filename)));


        for (int i = 0 ; i<lastIndex ; i++){
            dataOutputStream.writeBytes(entries[i].name+"\t"+entries[i].price+"\t"+entries[i].number+"\n");
        }

        dataOutputStream.close();

    }
    private static boolean removeRecord(String name) {
        boolean process = false;
        int i ;
        for (i = 0 ;i<lastIndex ; i++){
            if (entries[i].name.equals(name)){
                process = true;
                break;
            }


        }
        entries[i]=new Record();
        int a ;
        for (a = i ; a<lastIndex-i ;a++){
            entries[a]=entries[a+1];
        }
        entries[a]=new Record();

        return process;
    }
    private static void searchRecord(String name)
    {
        boolean process = false;
        int i ;
        for (i = 0 ;i<lastIndex ; i++){
            if (entries[i].name.equals(name)){
                process = true;
                break;
            }
        }

        if (process)
            System.out.println(entries[i].name+"\t"+entries[i].price+"\t"+entries[i].number);
        else
            System.out.println("Product is not available!");
    }

    private static void deleteAll() throws IOException {
        entries=new Record[limitRecord];
        for (int i=0;i<limitRecord;i++)
        {
            entries[i]=new Record();
        }
        System.out.println("All records are deleted successfully!");
        lastIndex=0;
        update();
    }

    // initialProcess() method must not be changed.
    private static void initialProcess()
    {
        entries=new Record[limitRecord];
        for (int i=0;i<limitRecord;i++)
        {
            entries[i]=new Record();
        }

        try {
            Reader reader=new InputStreamReader(new FileInputStream(filename),"Windows-1254");
            BufferedReader br=new BufferedReader(reader);

            String strLine;
            int i=0;
            while((strLine=br.readLine())!=null)
            {
                StringTokenizer tokens=new StringTokenizer(strLine,"\t");
                String[] t=new String[3];
                int j=0; while (tokens.hasMoreTokens())
            {
                t[j]=tokens.nextToken();
                j++;
            }
                entries[i].name=t[0];
                entries[i].price=Integer.valueOf(t[1]);
                entries[i].number=Integer.valueOf(t[2]);
                i++;
            }
            lastIndex=i;
            reader.close();
        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
