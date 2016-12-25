// Elizabeth Koshelev
// COSI 12B, Spring 2015 
// Programming Assignment #5, 3/26/16
// This program runs the stable marriage algorithm on a list of people and preferences.
//I originally turned this set in on time, and then afterwords realized I made a bug while commenting.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StableMarriage {
	//This is the main class, that gets the information from the user to plug into the stable marriage algorithm.
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("File:");
		Scanner console = new Scanner(System.in);
		Scanner input = new Scanner(new File(console.next()));
		System.out.println("Do men or women get preference?");
		String pref = console.next();
		int genderr=0;
		if (pref.equals("men")||pref.equals("Men")){
			genderr =0;
			makePeople(input, genderr);
		} else if(pref.equals("women")||pref.equals("Women")){
			genderr = 1;
			makePeople(input, genderr);
			
		} else{
			System.out.println("Error: rerun and type men or women");
		}	
	}
	//This is the method that makes the people based on the file and gender.
	public static void makePeople(Scanner input, int gender){
		int line=0; //Here I declare all the variables.
		int genderline=0;
		int wgenderline=0;
		int counter=0;
		ArrayList<Person> z = new ArrayList<Person>(); 
		ArrayList<Person> o = new ArrayList<Person>(); 
		while (input.hasNextLine()){
			String readLine = input.nextLine();
			ArrayList<Integer> preferences = new ArrayList<Integer>(); 
			ArrayList<Integer> temp1 = new ArrayList<Integer>(); 
			line++; //This counts the line number to find the total number of people.
			int number = line;
			String name = "";
			Scanner lineScan = new  Scanner(readLine);
			name = lineScan.next();
			if (name.equals("Man") ){
				name = "Man " + lineScan.next(); //Adjusts the name if it begins in "Man".
			} else if (name.equals("Woman")){
				name = "Woman " + lineScan.next(); //Adjusts the name if it begins in "Woman".
			}
			while (lineScan.hasNextInt()){
				int u = lineScan.nextInt();
				preferences.add(u); //Creates the preference list.
				temp1.add(u);
			}
			number = line;
			Person y = new Person(name, number, false, preferences); //Two arraylists of people are made, one to later check the original preference list.
			Person t = new Person(name, 1, false, temp1);
			z.add(y); //An arraylist of people is made.
			o.add(t);
		}
		for (int w=0; w<line; w++){
			if (o.get(w).getName().equals("END")){ //The genderline is set at the line that contains the word "END".
				genderline=w+1;
				w=line;
				
			}	
		}
		if (gender== 1){ //The arraylist is reversed if the gender is female, as is the genderline.
				ArrayList<Person> temp = new ArrayList<Person>();
				temp.addAll(z.subList(genderline,line));
				temp.addAll(z.subList(0,genderline));
				z=temp;
				ArrayList<Person> temp1 = new ArrayList<Person>();
				temp1.addAll(o.subList(genderline,line));
				temp1.addAll(o.subList(0,genderline));
				o=temp1;
				for (int m=0; m<line; m++){
				z.get(m).setNumber1(z.indexOf(m));
				o.get(m).setNumber1(o.indexOf(m));
				}
				genderline=line-genderline;
		}
		for (int m=0; m<line; m++){
			z.get(m).setNumber(genderline); //This adjusts the numbers of the people based on the gender.
		}
		sortPeople(z,o,genderline,line); //This calls the method that sorts and prints the people.
	}
	//This is the method that sorts people with the stable marriage algorithm.		
	public static void sortPeople(ArrayList<Person> z, ArrayList<Person> o, int genderline, int line){
		int lonelypeople = line-genderline-genderline; //This deteremines if there are more people in one gender than another.
		boolean complete = false;
		int beloved = 0;
		while (complete==false){
			for (int x=0; x<genderline-1; x++){		//Runs through the gender of preference.
				if (z.get(x).getStatus()==false && z.get(x).emptyList()==false ){	//If the list is not empty and the person is not taken,
					beloved = z.get(x).getList().get(0)+ genderline; //The beloved is the first person in their list plus the genderline.
				if (z.get(beloved).preference(z.get(x).getNumber(),z.get(beloved).getEngaged())==0 || z.get(beloved).getStatus()==false){ //If beloved prefers this new person or is not taken,
					if (z.get(beloved).getStatus()==true){ //If beloved is taken
						z.get(z.get(beloved).getEngaged()).setLonely(); //Set beloved's currently engaged to be forever alone.
					}
					z.get(x).setTaken((beloved-genderline));//Set beloved and person to be engaged to eachother.
					z.get(beloved).setTaken(x);
					for (int i=z.get(beloved).getList().indexOf(x)+1; i< z.get(beloved).getList().size(); i++){
						 z.get(z.get(beloved).getList().get(i)).deletePerson((beloved-genderline)); //For people below beloved's engaged on beloved's list, remove beloved from their list.
					}
				z.get(beloved).cutOff(x); //Remove everyone below their engaged on beloved's list.
				} 
				}
			}
			int countlonelies=0;
			for (int x=0; x<genderline-1; x++){
				if (z.get(x).getStatus()==false && z.get(x).emptyList()==true){
					countlonelies++; //Count the people with empty lists as to be lonely people.
				}
			}
			if (lonelypeople<0){ //If lonely people exist,
				for (int x=0; x<genderline-1; x++){
					if (z.get(x).getStatus()==true || countlonelies==lonelypeople || z.get(x).emptyList()==true ){
						complete=true; //The algorithm is complete if everyone is taken or the number of lonely people is equal to the counted lonely people.
					} else {
						complete=false; //Else run through the while loop again.
						x=genderline-2;
					}
				}
			} else{
				for (int x=0; x<genderline-1; x++){
					if (z.get(x).getStatus()==true || z.get(x).emptyList()==true ){
						complete=true; //If everyone is taken, the while loop is done.
					} else {
						complete=false;
						x=genderline-1;
					}
				}
			}
		}
		printPeople(z,o,genderline,lonelypeople);
	}
	//This is the method that prints the results of the stable marriage algorithm.
	public static void printPeople(ArrayList<Person> z, ArrayList<Person> o, int genderline, int lonelypeople){
		System.out.printf("%s %20s %20s \n","Name", "Choice","Partner");
		System.out.println("--------------------------------------------------");
		double mean = 0;
		for (int x=0; x<genderline-1; x++){ //Print the people and their matches.
			if (z.get(x).getStatus()==false && z.get(x).emptyList()==true){
				System.out.printf("%-20s %-20s %s \n",z.get(x).getName(), "--","Nobody");	
				}else {
				System.out.printf("%-20s %-20s %s \n",z.get(x).getName(),o.get(x).getList().indexOf(z.get(x).getEngaged())+1,z.get(z.get(x).getEngaged()+genderline).getName());
				mean= mean+o.get(x).getList().indexOf(z.get(x).getEngaged())+1;
				}
		}
		System.out.print(lonelypeople);														
		if (lonelypeople>0){
		mean= mean/(genderline-1);
		}else{
		mean= mean/(genderline-1-Math.abs(lonelypeople));	//Calculate the mean not including the lonely people.
		}
		System.out.print("Mean choice = " + mean);
	}
}


