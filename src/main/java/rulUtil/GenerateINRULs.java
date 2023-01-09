package rulUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author memo
 */
public class GenerateINRULs {

  static class MyComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {

      String[] parts1 = s1.trim().split(",");
      String[] parts2 = s2.trim().split(",");

      int a = Integer.parseInt(parts1[0]);
      int b = Integer.parseInt(parts2[0]);
      if (a != b) return a - b;

      int c = Integer.parseInt(parts1[1]);
      int d = Integer.parseInt(parts2[1]);
      return c - d;
    }
  }

  /**
   * @param lines
   */
  public static void main(String[] args) {

    if (args.length == 0) throw new RuntimeException("No arguments were passed.");
    else {
      List<String> list = new LinkedList<String>();
      try {
        FileReader reader = new FileReader(new File(args[0]));
        BufferedReader bufReader = new BufferedReader(reader);
        String line;
        while ((line = bufReader.readLine()) != null) {
          list.add(line);
        }

      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      String[] lines = (String[]) list.toArray(new String[list.size()]);

      String firstLine= lines[0].trim();
      String[] parameters = firstLine.split(" ");
      int[][] rotations = new int[parameters.length][];
      for (int i = 0; i < parameters.length; i++) {
        if (!parameters[i].matches("(0|1|2|3),(0|1)")) throw new RuntimeException("Rotation parameters of wrong format.");
        else {
          rotations[i] = new int[2];
          rotations[i][0] = Integer.parseInt(parameters[i].substring(0, 1));
          rotations[i][1] = Integer.parseInt(parameters[i].substring(2,3));
        }
      }
      String headSection = lines[1];
      Queue<String> middleSection = new LinkedList<String>();
      Queue<String> endSection = new LinkedList<String>();
      int i;
      for (i = 2; i < lines.length && lines[i].startsWith("2"); i++) {
        middleSection.add(lines[i]);
      }
      for ( ; i < lines.length; i++) {
        endSection.add(lines[i]);
      }

      String[] middleArray = (String[]) middleSection.toArray(new String[middleSection.size()]);
      String[] endArray = (String[]) endSection.toArray(new String[endSection.size()]);

      for(int j = 0; j < rotations.length; j++) {
        generateRuls(rotations[j][0], rotations[j][1], headSection, middleArray, endArray);
      }
    }
  }

  private static void generateRuls(int rot, int mir, String head, String[] middle, String[] end) {
    System.out.println(";");
    // head
    String[] headParts = head.trim().split(",");
    String headResult = "1,";
    if (mir == 0) {
      for (int i = 0, j = 4-rot; i < 4; i++, j++) {
        headResult += headParts[j % 4 + 1];
        if (i < 3) headResult += ",";
      }
    } else {
      for (int i = 0, j = 4+(rot % 2 == 1 ? rot : rot + 2); i < 4; i++, j--) {
        headResult += getMirFlag(Integer.parseInt(headParts[j % 4 + 1]));
        if (i < 3) headResult += ",";
      }
    }
    System.out.println(headResult);

    // middle
    List<String> middleList = new LinkedList<String>();
    for (String s : middle) {
      String[] parts = s.trim().split(",");
      String result = "2,";
      int field = Integer.parseInt(parts[1]);
      if (field <= 8) field = (field + 2*rot - 1) % 8 + 1;
      else field = (field + 4*rot - 9) % 16 + 9;

      if (mir == 1) {
        if (field <= 8) field = (14 - field - 1) % 8 + 1;
        else field = (42 - field - 9) % 16 + 9;
      }
      result += field;
      if (mir == 0) {
        for (int i = 0, j = 4-rot; i < 4; i++, j++) {
          result += "," + parts[j % 4 + 2];
        }
      } else {
        for (int i = 0, j = 4+(rot % 2 == 1 ? rot : rot + 2); i < 4; i++, j--) {
          String part = parts[j % 4 + 2];
          if (part.trim().equals("?")) result += ",?";
          else result += "," + getMirFlag(Integer.parseInt(parts[j % 4 + 2]));
        }
      }
      middleList.add(result);
    }
    Collections.sort(middleList, new MyComparator());

    for (String s : middleList) {
      System.out.println(s);
    }

    // end
    List<String> endList = new LinkedList<String>();
    for (String s : end) {
      String[] parts = s.trim().split(",");
      String result = "3,";
      int field = Integer.parseInt(parts[1]);
      if (field <= 0);
      else if (field <= 8) field = (field + 2*rot - 1) % 8 + 1;
      else field = (field + 4*rot - 9) % 16 + 9;
      if (mir == 1) {
        if (field <= 0);
        else if (field <= 8) field = (14 - field - 1) % 8 + 1;
        else field = (42 - field - 9) % 16 + 9;
      }
      result += field + "," + parts[2] + ",";

      if (Integer.parseInt(parts[4]) == 0) {
        result += (Integer.parseInt(parts[3]) + rot) % 4;
        result += mir == 0 ? ",0" : ",1";
      } else {
        result += ((Integer.parseInt(parts[3]) - rot) + 8) % 4;
        result += mir == 0 ? ",1" : ",0";
      }
      endList.add(result);
    }
    Collections.sort(endList, new MyComparator());
    for (String s : endList) {
      System.out.println(s);
    }
  }

  private static int getMirFlag(int flag) {
    if (flag == 0) return 0;
    else if (flag == 1) return 3;
    else if (flag == 2) return 2;
    else if (flag == 3) return 1;
    else if (flag == 4) return 4;
    else if (flag == 11) return 13;
    else if (flag == 13) return 11;
    else throw new RuntimeException("Flag " + flag + " not supported.");
  }
}
