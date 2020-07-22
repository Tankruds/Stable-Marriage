import java.util.ArrayList;
import java.util.Scanner;

public class Stable {
	public static void main(String[] args) {
		// First we need to set up both of our groups, the "men" and "women"
		ArrayList<ArrayList<Integer>> men = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> women = new ArrayList<ArrayList<Integer>>();
		
		// Next we need to fill each of the men and women with how many people we want.
		Scanner input = new Scanner(System.in);
		System.out.print("How many \"men\" are there(the amount of \"women\" will be the same): ");
		int number = input.nextInt();
		for (int i = 0; i < number; i++) {
			men.add(new ArrayList<Integer>());
			women.add(new ArrayList<Integer>());
		}
		
		// Now we fill in the preference list from most preferred to least for the "men"
		for (int i = 0; i < men.size(); i++) {
			System.out.println("\nFill in the preference list for \"man\" " + i + ".");
			for (int j = 0; j < women.size(); j++) {
				System.out.print("Number " + (j + 1) + " preference: ");
				men.get(i).add(input.nextInt());
			}
		}
		
		// Now we fill in the preference list from most preferred to least for the "women"
		for (int i = 0; i < women.size(); i++) {
			System.out.println("\nFill in the preference list for \"woman\" " + i + ".");
			for (int j = 0; j < men.size(); j++) {
				System.out.print("Number " + (j + 1) + " preference: ");
				women.get(i).add(input.nextInt());
			}
		}
		
		// We no longer need any input from the user.
		input.close();
		
		// This array keeps track of the marriages, the man at index i will be married to the int that is stored.
		int[] marriages = new int[men.size()];
		for (int i = 0; i < marriages.length; i++) marriages[i] = -1;
		
		System.out.println();
		// Do the G-S Algorithm until there are no single "men"
		int single;
		while ((single = singleManExists(marriages)) != -1) {
			int preference = 0;
			do {
				int interest = men.get(single).get(preference);
				if (singleWoman(interest, marriages)) {
					marriages[single] = interest;
					System.out.println("Man " + single + " is engaged to woman " + interest);
				} else {
					int currentMan = getCurrentMan(interest, marriages);
					int betterMan = compareMen(single, currentMan, interest, women);
					if (betterMan == currentMan) {
						preference++;
						System.out.println("Man " + single + " got denied by woman " + interest);
					} else {
						System.out.println("Man " + single + " stole woman " + interest + " from man "+ currentMan);
						marriages[currentMan] = -1;
						marriages[single] = interest;
					}
				}
			} while (marriages[single] == -1);
		}
		
		// Now the list should be stable, so we print out our results.
		System.out.println("\nFinal Marriages: ");
		for (int i = 0; i < marriages.length; i++)
			System.out.println("Man " + i + " is married to woman " + marriages[i]);
	}
	
	public static int compareMen(int single, int currentMan, int woman, ArrayList<ArrayList<Integer>> women) {
		ArrayList<Integer> preferences = women.get(woman);
		for (int i = 0; i < preferences.size(); i++)
			if (preferences.get(i) == single || preferences.get(i) == currentMan)
				return preferences.get(i);
		return -1;
	}
	
	public static int getCurrentMan(int woman, int[] marriages) {
		for (int i = 0; i < marriages.length; i++)
			if (marriages[i] == woman) return i;
		return -1;
	}
	
	public static boolean singleWoman(int woman, int[] marriages) {
		for (int i = 0; i < marriages.length; i++)
			if (marriages[i] == woman) return false;
		return true;
	}
	
	public static int singleManExists(int[] marriages) {
		for (int i = 0; i < marriages.length; i++)
			if (marriages[i] == -1)
				return i;
		return -1;
	}
}