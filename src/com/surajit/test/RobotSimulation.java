package com.surajit.test;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RobotSimulation {

    public static void main(String[] args) {
    	//Sample Input
    	//int[] commands={5,-1,1,-2,3,-2,-2,3};
    	//int[][] obstacles={{0,2}};
    	int[][] obstacles= {{}};
    	int[] commands= {};
    	try  
    	{  
    	LineNumberReader lis=new LineNumberReader(new FileReader("src/download.txt"));       
    	String l;
    	int number_of_obstacles = 0;
    	int number_of_commands = 0;
    	int pos = 0;

    	while ((l = lis.readLine()) != null) {
    	    Scanner s = new Scanner(l);
    	    if(lis.getLineNumber() == 1) {
    	    	number_of_obstacles = Integer.parseInt(s.next());
    	    	obstacles = new int[number_of_obstacles][2];
    	    	while (s.hasNext()) {
    	    		number_of_commands = Integer.parseInt(s.next());
    	    		commands = new int[number_of_commands];
        	    }
	        }else if(lis.getLineNumber() >1 && lis.getLineNumber()<= number_of_obstacles+1) {
        		obstacles[lis.getLineNumber()-2][0] = Integer.parseInt(s.next());
        		obstacles[lis.getLineNumber()-2][1] = Integer.parseInt(s.next());
	        }
	        else {
	        	String command = s.next();
	        	if(command.equalsIgnoreCase("M")) {
	        		while (s.hasNext()) {
		    	        commands[pos] = Integer.parseInt(s.next());
		    	        pos++;
		    	    }
	        	}else if(command.equalsIgnoreCase("L")) {
	        		commands[pos] = -2;
	        		pos++;
	        	}else if (command.equalsIgnoreCase("R")) {
	        		commands[pos] = -1;
	        		pos++;
	        	}else {
	        		pos++;
	        	}
	        }
    	} 
    	}  
    	catch(IOException e)  
    	{  
    	e.printStackTrace();  
    	}
        int maxDistSq = robotSim(commands,obstacles);
        Double maxDistSqDbl = Math.sqrt(Double.valueOf(maxDistSq));
        System.out.println("So, Final distance is = "+maxDistSqDbl);
    }


    public static int robotSim(int[] commands, int[][] obstacles) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        Set<String> obstaclesSet = new HashSet<>();
        for (int[] obstacle : obstacles) {
            obstaclesSet.add(obstacle[0] + " " + obstacle[1]);
        }

        int x = 0, y = 0, direction = 0, maxDistSquare = 0;
        for (int i = 0; i < commands.length; i++) {
            if (commands[i] == -2) { // Turns left
                direction = (direction + 3) % 4;
            } else if (commands[i] == -1) { // Turns right
                direction = (direction + 1) % 4;
            } else { // Moves forward commands[i] steps
                int step = 0;
                while (step < commands[i]
                        && (!obstaclesSet.contains(
                        (x + directions[direction][0]) + " " + (y + directions[direction][1]))
                )
                ) {
                    x += directions[direction][0];
                    y += directions[direction][1];
                    step++;
                }
            }
            maxDistSquare = Math.max(maxDistSquare, x * x + y * y);
        }

        return maxDistSquare;
    }
}