import bpTree.BpTree;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String command = args[0];
        String fileName = args[1];
        Scanner input;
        PrintWriter output;

        BpTree bptree;

        switch (command) {
            case "-c":
                // Data File Creation
                // -c index.dat b
                if(args.length != 3) {
                    System.out.println("Not enough arguments.");
                }

                try {
                    output = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                    output.println("Order: " + args[2]);
                    output.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "-i":
                // Insertion
                // -i index.dat input.csv
                if(args.length != 3) {
                    System.out.println("Not enough arguments.");
                }
                String inputFile = args[2];

                try {
                    input = new Scanner(new BufferedReader(new FileReader(fileName)));

                    input.next(); // "Order:"
                    int order = input.nextInt();

                    bptree = BpTree.load(input, order);

                    input = new Scanner(new BufferedReader(new FileReader(inputFile)));
                    while(input.hasNextLine()) {
                        String inputInfo = input.nextLine(); // key,value\n
                        String[] pair = inputInfo.split(",");
                        bptree.insert(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
                    }
                    input.close();

                    output = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                    output.println("Order: " + order);
                    bptree.save(output, bptree.getRoot());
                    output.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "-d":
                // Deletion
                // -d index.dat delete.csv
                if(args.length != 3) {
                    System.out.println("Not enough arguments.");
                }

                String deleteFile = args[2];
                int deleteNum = 0;
                try {
                    input = new Scanner(new BufferedReader(new FileReader(fileName)));

                    input.next(); // "Order:"
                    int order = input.nextInt();

                    bptree = BpTree.load(input, order);
                    input.close();

                    input = new Scanner(new BufferedReader(new FileReader(deleteFile)));
                    while(input.hasNextInt()) {
                        deleteNum = input.nextInt();
                        bptree.delete(deleteNum);
                    }
                    input.close();

                    output = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                    output.println("Order: " + order);
                    bptree.save(output, bptree.getRoot());
                    output.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "-s":
                // Single Key Search
                // -s index.dat 125
                if(args.length != 3) {
                    System.out.println("Not enough arguments.");
                }
                int key = Integer.parseInt(args[2]);

                try {
                    input = new Scanner(new BufferedReader(new FileReader(fileName)));

                    input.next(); // "Order:"
                    int order = input.nextInt();

                    bptree = BpTree.load(input, order);
                    input.close();

                    int value = bptree.keySearch(key);
                    if(value != -1) {
                        System.out.println(value);
                    }
                    else {
                        System.out.println("NOT FOUND");
                    }

                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case "-r":
                // Ranged Search
                // -r index.dat 100 200
                if(args.length != 4) {
                    System.out.println("Not enough arguments.");
                }
                int startKey = Integer.parseInt(args[2]);
                int endKey = Integer.parseInt(args[3]);
                try {
                    input = new Scanner(new BufferedReader(new FileReader(fileName)));

                    input.next(); // "Order:"
                    int order = input.nextInt();

                    bptree = BpTree.load(input, order);
                    input.close();

                    bptree.rangeSearch(startKey, endKey);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
