package dcs_hider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.ArrayUtils;

public class Dcs_hider {

    public static void main(String[] args) throws IOException {
        Scanner choose = new Scanner(System.in);
        String choice = null;
        String specialCharacter = "#";
        File[] directories;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the location of the DCS World folder. Example: D:\\DCS World \n");
        String folderPath = reader.readLine();
        
        while (!"end".equals(choice)) {
            System.out.println("\nType:\n1 - to see folder state\n2 - to show or hide all folders\n3 - to select folders to change its state\nand press enter.\nTo end the program type \"end\".");
            choice = choose.nextLine();
            if ("1".equals(choice)) {
                directories = buildDirectoryList(folderPath);
                for (int i = 0; i < directories.length; i++) {
                    System.out.println("NR " + i + " Folder Name " + directories[i].getName());
                }
            }
            if ("2".equals(choice)) {
                directories = buildDirectoryList(folderPath);
                for (int i = 0; i < directories.length; i++) {
                    changeDirectoryName(directories, i, specialCharacter);
                }
            }
            if ("3".equals(choice)) {
                directories = buildDirectoryList(folderPath);
                for (int i = 0; i < directories.length; i++) {
                    System.out.println("NR " + i + " Folder Name " + directories[i].getName());
                }
                System.out.println("Which folder state should be change - number separate by comma. example: 1, 4,6");
                String userList = reader.readLine();
                List<String> itemsToChange = Arrays.asList(userList.split("\\s*,\\s*"));

                for (String item : itemsToChange) {
                    changeDirectoryName(directories, Integer.parseInt(item), specialCharacter);
                }
            }
        }
        System.out.println("Created by Pastuszak");
        
        choose.close();
    }

    public static void changeDirectoryName(File[] directories, int pointer, String specialCharacter) {
        File sourceFile = new File(directories[pointer].toString());
        File destFile = new File(directories[pointer].getParent() + "\\" + specialCharacter + directories[pointer].getName());

        if (directories[pointer].getName().substring(0, 1).contains(specialCharacter) == false) {
            if (sourceFile.renameTo(destFile)) {
                System.out.println("Add " + specialCharacter + " to the " + directories[pointer].getName() + " directory name successfully");
            } else {
                System.out.println("Failed to add " + specialCharacter + " to the " + directories[pointer].getName() + " directory name");
            }
        } else {
            destFile = new File(directories[pointer].getParent() + "\\" + directories[pointer].getName().substring(1));
            if (sourceFile.renameTo(destFile)) {
                System.out.println("Remove " + specialCharacter + " from the " + directories[pointer].getName() + " directory name successfully");
            } else {
                System.out.println("Failed to remove " + specialCharacter + " from the " + directories[pointer].getName() + " directory name");
            }
        }
    }

    public static File[] buildDirectoryList(String folderPath) {
        File[] directoriesAircraft = new File(folderPath + "\\DemoMods\\aircraft").listFiles(File::isDirectory);
        File[] directoriesTech = new File(folderPath + "\\DemoMods\\tech").listFiles(File::isDirectory);
        File[] directoriesTerrains = new File(folderPath + "\\DemoMods\\terrains").listFiles(File::isDirectory);

        File[] directories;
        directories = ArrayUtils.addAll(directoriesAircraft, directoriesTech);
        directories = ArrayUtils.addAll(directories, directoriesTerrains);

        return directories;
    }
}