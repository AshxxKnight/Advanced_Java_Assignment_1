package org.example;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Main {

    private static final String CSV_DIRECTORY = "C:\\Users\\anshusaxena\\Desktop\\Advance Java\\Assigment Links";

    static List<Product> tShirts = new ArrayList<>();
    static boolean fileLoaded = false;

    public static void main(String[] args) {


        // Read input parameters
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter T-shirt color: ");
        String color = sc.nextLine();

        System.out.print("Enter T-shirt size: ");
        String size = sc.nextLine();

        System.out.print("Enter gender (M/F/U): ");
        String gender = sc.nextLine();

        System.out.print("Enter output preference (Price/Rating/Both): ");
        String outputPreference = sc.nextLine();

        sc.close();


        // Load CSV files in a separate thread to check for new files at runtime
        Thread fileLoaderThread = new Thread(() -> {
            while (true) {
                try {
                    if (!fileLoaded) {
                        loadCSV();
                        fileLoaded = true;
                    }
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        fileLoaderThread.start();

        // Wait for file loading to complete
        while (!fileLoaded) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        // Wait for the products to be loaded from CSV files
        while (tShirts.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //matching products
        List<Product> matchedList = searchProducts(color, size, gender);

        //sort selected tshirt
        List<Product> tshirtList = sortProducts(matchedList, outputPreference);

        //display output tShirt
        displayOutput(tshirtList);


    }

    public static void loadCSV() {
        // Load CSV files
        File[] csvFiles = new File(CSV_DIRECTORY).listFiles(file -> file.isFile() && file.getName().endsWith(".csv"));
        for (File filename : csvFiles) {
            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                List<String[]> rows = reader.readAll();
                boolean firstLine = true;
                for (String[] row : rows) {
                    if(firstLine) {
                        firstLine = false;
                        continue;
                    }
                    Product product = new Product();
                    product.setId(row[0]);
                    product.setName(row[1]);
                    product.setColor(row[2]);
                    product.setGender(row[3]);
                    product.setSize(row[4]);
                    product.setPrice(Double.parseDouble(row[5]));
                    product.setRating(Double.parseDouble(row[6]));
                    product.setAvailability(row[7]);
                    tShirts.add(product);
                }
            } catch (IOException | CsvException e) {
                System.err.println("Error loading CSV file: " + filename);
                e.printStackTrace();
            }
        }
    }

    //Matching Product
    private static List<Product> searchProducts(String color, String size, String gender) {
        List<Product> matchingProduct = new ArrayList<>();
        for (Product product : tShirts) {
            if (product.getColor().equalsIgnoreCase(color) && product.getSize().equalsIgnoreCase(size) && product.getGender().equalsIgnoreCase(gender)) {
                matchingProduct.add(product);
//				 product.getId(),product.getName(),product.getColor(),product.getGender(),product.getSize(),product.getPrice(),product.getRating(),product.getAvailability()
            }
        }
        return matchingProduct;
    }


    //Sort the result according to preference
    private static List<Product> sortProducts(List<Product> productList, String outputPreference) {
        Comparator<Product> comparator;
        switch (outputPreference.toLowerCase()) {
            case "price":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "rating":
                comparator = Comparator.comparing(Product::getRating).reversed();
                break;
            case "both":
                comparator = Comparator.comparing(Product::getPrice).thenComparing(Comparator.comparing(Product::getRating).reversed());
                break;
            default:
                System.err.println("Invalid output preference: " + outputPreference);
                return productList;
        }
        return productList.stream().sorted(comparator).collect(Collectors.toList());

    }

    //Display
    private static void displayOutput(List<Product> productList) {
        if (productList.isEmpty()) {
            System.out.println("No matching products found.");
        } else {
            System.out.println("\nMatching Products:\n");
            for (Product product : productList) {
                System.out.println("ID: " + product.getId()
                        + "\nName: " + product.getName()
                        + "\nColor: " + product.getColor()
                        + "\nGender: " + product.getGender()
                        + "\nSize: " + product.getSize()
                        + "\nPrice: $" + product.getPrice()
                        + "\nRating: " + product.getRating()
                        + "\nAvailability: " + product.getAvailability() + "\n");
            }
        }
    }

}
