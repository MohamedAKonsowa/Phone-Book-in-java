import java.io.*;
import java.util.Scanner;

public class PhoneBook {

	public static int capacity = 100;
	public static int[] phoneNumbers = new int[capacity];
	public static String[] city = new String[capacity];
	public static String[] address = new String[capacity];
	public static String[] firstName = new String[capacity];
	public static String[] lastName = new String[capacity];
	public static int counter = 0;

	public static void removeNull() {
		for (int i = 0; i < capacity; i++) {
			city[i] = "";
			address[i] = "";
			firstName[i] = "";
			lastName[i] = "";
		}
	}

	private static void listCommands() {
		System.out.println("1.  Load - load old save data");
		System.out.println("2.  QUERY - finds a contact by last name");
		System.out.println("3.  ADD - saves a new contact entry into the phone book");
		System.out.println("4.  DELETE - removes a contact from the phone book");
		System.out.println("5.  MODIFY - modifies an existing contact");
		System.out.println("6.  PRINT - lists all saved contacts in alphabetical  order");
		System.out.println("7.  SAVE - saves the directory by writing it out to an external file in a format similar to that explained in the Load command. ");
		System.out.println("8.  Exit - exits form this program.");
	}

	public static void addNumber(String firstName1, String lastName1, String city1, String address1, int number) {
		firstName[counter] = firstName1;
		lastName[counter] = lastName1;
		city[counter] = city1;
		address[counter] = address1;
		phoneNumbers[counter] = number;
		counter++;
		System.out.println("The contact has been added successfully");
	}

	public static int[] findIndexByLastName(String lastName1) {
		int counter1 = 0;
		for (int i = 0; i < capacity; i++) {
			if (lastName[i].equals(lastName1) && phoneNumbers[i] != 0) {
				counter1++;
			}
		}
		int[] requiredIndex = new int[counter1];
		int counter2 = 0;
		for (int i = 0; i < capacity; i++) {
			if (lastName[i].equals(lastName1) && phoneNumbers[i] != 0) {
				requiredIndex[counter2] = i;
				counter2++;
			}
		}
		return requiredIndex;
	}

	public static void findByLastName(String requiredLastName) {
		int[] requiredIndex = findIndexByLastName(requiredLastName);
		if (requiredIndex.length != 0) {
			for (int index : requiredIndex) {
				System.out.println(lastName[index] + " , " + firstName[index] + " , " + address[index] + " , " + city[index] + " , " + phoneNumbers[index]);
			}
		} else System.out.println("Not found");
	}

	public static void removeContact(String firstName1, String lastName1) {
		int requiredIndex = 0;
		boolean check = false;
		for (int i = 0; i < capacity; i++) {
			if (firstName[i].equals(firstName1) && lastName[i].equals(lastName1)) {
				requiredIndex = i;
				check = true;
			}
		}
		if (check) {
			phoneNumbers[requiredIndex] = 0; // change the phone number to zero and when reading and saving not taking any number that equals 0 into account
			System.out.println("The contact has been removed successfully");
		} else System.out.println("There is no entry with the given input");
	}

	public static void showAllData() {
		for (int i = 0; i < capacity; i++)
			if (!(lastName[i].equals("") || lastName[i].equals("0")))
				System.out.println(lastName[i] + " , " + firstName[i] + " , " + address[i] + " , " + city[i] + " , " + phoneNumbers[i]);
		System.out.println("All contacts have been shown");
	}


	public static void save() throws IOException {
		File file = new File("data.txt");
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter pw = new BufferedWriter(fileWriter);
		for (int i = 0; i < capacity; i++) {
			if (!(lastName[i].equals("") || lastName[i].equals("0") || phoneNumbers[i] == 0))
				pw.write(lastName[i] + " , " + firstName[i] + " , " + address[i] + " , " + city[i] + " , " + phoneNumbers[i] + "\n");
		}
		pw.close();
	}

	public static void load() throws IOException {
		File file = new File("data.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			String[] reading = line.split(" , ");
			addNumber(reading[1], reading[0], reading[3], reading[2], Integer.parseInt(reading[4]));
		}
		br.close();
	}


	public static void main(String[] args) throws IOException {
		double x = Math.atan2(-1,-1);
		double angle = Math.toDegrees(x);
		System.out.println(angle);

		Scanner input = new Scanner(System.in);
		removeNull();
		for (; ; ) {
			listCommands();
			int choice1 = input.nextInt();
			if (choice1 == 1) {
				load();
			} // load data


			else if (choice1 == 2) { // find by last name
				System.out.println("Enter the last name you want to find");
				String inputLastName = input.next();
				findByLastName(inputLastName);
			} else if (choice1 == 3) { // add number
				System.out.println("Enter the user's first name");
				String firstName = input.next();
				System.out.println("Enter the user's last name");
				String lastName = input.next();
				System.out.println("Enter the user's city");
				String city = input.next();
				System.out.println("Enter the user's address");
				String address = input.next();
				System.out.println("Enter the user's number");
				int number = input.nextInt();
				addNumber(firstName, lastName, city, address, number);
			} else if (choice1 == 4) { // remove a number
				System.out.println("Enter the first name of the contact to be removed");
				String inputFirstName = input.next();
				System.out.println("Enter the last name of the contact to be removed");
				String inputLastName = input.next();
				removeContact(inputFirstName, inputLastName);
			} else if (choice1 == 5) { // edit a number
				System.out.println("Enter the required last name");
				String inputLastName = input.next();
				int[] index = findIndexByLastName(inputLastName);
				if (index.length == 0) System.out.println("No contact");
				else if (index.length == 1){

					System.out.println("Select the entry you want to change");
					System.out.println("1- City\n2- Address\n3- Number\n4- First name\n5- Last name");
					int choice = input.nextInt();
					if (choice == 1) {
						System.out.println("Input the new city");
						String cityInput = input.next();
						city[index[0]] = cityInput;
					} else if (choice == 2) {
						System.out.println("Input the new address");
						String addressInput = input.next();
						address[index[0]] = addressInput;
					} else if (choice == 3) {
						System.out.println("Input the new number");
						int number = input.nextInt();
						phoneNumbers[index[0]] = number;
					} else if (choice == 4) {
						System.out.println("Input the new first name");
						String firstNameInput = input.next();
						firstName[index[0]] = firstNameInput;
					} else if (choice == 5) {
						System.out.println("Input the new last name");
						String lastNameInput = input.next();
						if (!lastNameInput.equals("0")) lastName[index[0]] = lastNameInput;
						else System.out.println("Last name cannot be changed to 0");
					} else System.out.println("Invalid field");


				}
				else {
					System.out.println("Enter the users first name");
					String firstname = input.next();
					int requiredIndex = -1;
					for (int j : index) {
						if (firstName[j].equals(firstname)) {
							requiredIndex = j;
							break;
						}
					}
					if (requiredIndex != -1){
						System.out.println("Select the entry you want to change");
						System.out.println("1- City\n2- Address\n3- Number\n4- First name\n5- Last name");
						int choice = input.nextInt();
						if (choice == 1) {
							System.out.println("Input the new city");
							String cityInput = input.next();
							city[requiredIndex] = cityInput;
						} else if (choice == 2) {
							System.out.println("Input the new address");
							String addressInput = input.next();
							address[requiredIndex] = addressInput;
						} else if (choice == 3) {
							System.out.println("Input the new number");
							int number = input.nextInt();
							phoneNumbers[requiredIndex] = number;
						} else if (choice == 4) {
							System.out.println("Input the new first name");
							String firstNameInput = input.next();
							firstName[requiredIndex] = firstNameInput;
						} else if (choice == 5) {
							System.out.println("Input the new last name");
							String lastNameInput = input.next();
							if (!lastNameInput.equals("0")) lastName[requiredIndex] = lastNameInput;
							else System.out.println("Last name cannot be changed to 0");
						} else System.out.println("Invalid field");

					}
					else System.out.println("No contact Exists");
				}
			} else if (choice1 == 6) { // show all data
				showAllData();
			} else if (choice1 == 7) { // save data
				save();
			} else if (choice1 == 8) { // close the program
				break;
			} else System.out.println("Invalid id");
		}
	}
}
