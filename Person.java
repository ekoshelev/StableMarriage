// Elizabeth Koshelev
// COSI 12B, Spring 2015 
// Programming Assignment #5, 3/26/16
// This class creates a Person method containing their name, number, whether they are taken, their preferences.

import java.util.ArrayList;

public class Person {
	String name; //Here I declare all the fieldso f a person.
	boolean taken;
	private ArrayList<Integer> preferences;
	int number;
	int engaged;
	public Person(String name, int number, boolean taken, ArrayList<Integer> preferences){
		this.name=name;
		this.number = Math.abs(number);
		this.taken = taken;
		this.preferences = preferences;
		engaged = -1;
	}
	//This method returns the name.
	public String getName(){
		return name;
	}
	//This method returns the number.
	public int getNumber(){
		return number;
	}
	//This method returns whether the person is taken.
	public boolean getStatus(){
		return taken;
	}
	//This method returns whether the person is engaged.
	public int getEngaged(){
		return engaged;
	}
	//This method returns the preference list.
	public ArrayList<Integer> getList(){
		return preferences;
	}
	//This method returns which person a person prefers more.
	public int preference(int y, int x){
		int n =0;
			if (preferences.indexOf(y) < preferences.indexOf(x)){
				n=0;
			} else {
				n=1;
			}
		
		return n;
	}
	//This method sets the person the person is engaged to.
	 public int setTaken(int engaged){
		 this.engaged=engaged;
		 taken = true;
		return engaged;
	 }
	//This method cuts off everything in the preference list after the engaged person. 
	public void cutOff(int engaged){
		int cutpoint=0;
		for (int i=0; i<preferences.size(); i++){
			 if (preferences.get(i) == engaged){
				cutpoint = i;
			 } 
		 }
		 for (int i=preferences.size()-1; i>cutpoint; i--){
			 preferences.remove(i);
		 }
	}
	//This method makes the person single.
	 public void setLonely(){
		 taken = false;
	 }
	//This method returns if the preference list is empty.
	public boolean emptyList(){
		if (preferences.size()==0 ){
			return true;
		} else {
			return false;
		}
	}
	//This method removes a person from a preference list.
	public void deletePerson(int person){
		preferences.remove(preferences.indexOf(person));
	}
	//This adjusts a person's number based on their gender.
	public void setNumber(int genderline){
		if (number>genderline){
			number = number-genderline;
		}
	}
	public void setNumber1(int newnumber){
		this.number=newnumber;
	}
}
