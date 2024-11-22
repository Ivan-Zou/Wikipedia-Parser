import java.util.Scanner;

/*
 * This class takes in user input, where users can choose from a selection of questions and get answers to them
 * on different inputs
 */
public class Questions {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WikipediaParser oscarsWiki = new WikipediaParser();
        System.out.println("Which Question Do You Want to Answer?");

        while (true) {
            System.out.println("1) List all Discontinued categories for awards " +
                    "(i.e., categories that are no longer awarded)");
            System.out.println("2) How many award categories were added in the ____?");
            System.out.println("3) List all the movies that were nominated for at least __ awards in ____.");
            System.out.println("4) Which film won ____________________ in ____?");
            System.out.println("5) What was the budget for the ___________________ winner in 2022? " +
                    "How much did this movie make in the box office?");
            System.out.println("6) Which academic institution (university, college, etc.) has the highest number of " +
                    "alumni nominated for the __________ award?");
            System.out.println("7) For _______________________, for the countries that were nominated/won, " +
                    "how many times have they been nominated in the past (including this year)?");
            System.out.println("8) For everyone who was ever nominated for _____________, " +
                    "how many nominees were born in __________?");
            System.out.println();
            System.out.print("Enter a number from 1 to 8: ");

            int questionNum = sc.nextInt();
            System.out.println();
            while (!(questionNum >= 1 && questionNum <= 8)) {
                System.out.println("Invalid Number!");
                System.out.print("Enter a number from 1 to 8: ");
                questionNum = sc.nextInt();
                System.out.println();
            }

            switch (questionNum) {
                case 1:
                    System.out.println("1) List all Discontinued categories for awards " +
                            "(i.e., categories that are no longer awarded)");
                    oscarsWiki.printDiscontinuedCategories();
                    System.out.println();
                    break;
                case 2:
                    System.out.println("2) How many award categories were added in the ____?");
                    System.out.print("Enter a decade: ");
                    int decade2 = sc.nextInt();
                    while (decade2 % 10 != 0) {
                        System.out.println("Invalid Input");
                        System.out.print("Enter a decade: ");
                        decade2 = sc.nextInt();
                        System.out.println();
                    }
                    oscarsWiki.printCategoriesAdded(decade2);
                    System.out.println();
                    break;
                case 3:
                    System.out.println("3) List all the movies that were nominated for at least __ awards in ____.");
                    System.out.print("Enter a number of awards: ");
                    int numAwards3 = sc.nextInt();
                    while (numAwards3 < 0) {
                        System.out.println("Invalid Input");
                        System.out.print("Enter a positive number: ");
                        numAwards3 = sc.nextInt();
                    }
                    System.out.print("Enter a Year: ");
                    int year3 = sc.nextInt();
                    oscarsWiki.printMoviesNominated(numAwards3, year3);
                    System.out.println();
                    break;
                case 4:
                    System.out.println("4) Which film won ____________________ in ____?");
                    System.out.print("Enter an Award Category For a Film: ");
                    sc.nextLine();
                    String category4 = sc.nextLine();
                    System.out.print("Enter a Year: ");
                    int year4 = sc.nextInt();
                    oscarsWiki.printWinner(category4, year4);
                    System.out.println();
                    break;
                case 5:
                    System.out.println("5) What was the budget for the ___________________ winner in 2022? " +
                            "How much did this movie make in the box office?");
                    System.out.print("Enter an Award Category For a Film: ");
                    sc.nextLine();
                    String category5 = sc.nextLine();
                    oscarsWiki.printBudgetAndBoxOfficeSalesOfWinningMovie(category5, 2022);
                    System.out.println();
                    break;
                case 6:
                    System.out.println("6) Which academic institution (university, college, etc.) has the highest " +
                            "number of alumni nominated for the __________ award?");
                    System.out.print("Enter an Award Category For a Person: ");
                    sc.nextLine();
                    String category6 = sc.nextLine();
                    oscarsWiki.printAcademicInstitutionWithHighestNominees(category6);
                    System.out.println();
                    break;
                case 7:
                    System.out.println("7) For _______________________, for the countries that were nominated/won, " +
                            "how many times have they been nominated in the past (including this year)?");
                    System.out.print("Enter an Award Category For a Film: ");
                    sc.nextLine();
                    String category7 = sc.nextLine();
                    oscarsWiki.printTotalNumOfNominationsForNominatedCountries(category7);
                    System.out.println();
                    break;
                case 8:
                    System.out.println("8) For everyone who was ever nominated for _____________, " +
                            "how many nominees were born in __________?");
                    System.out.print("Enter an Award Category For a Person: ");
                    sc.nextLine();
                    String category8 = sc.nextLine();
                    System.out.print("Enter a Month: ");
                    String month8 = sc.nextLine();
                    oscarsWiki.printNumOfNomineesOfBirthMonth(category8, month8);
                    System.out.println();
                    break;
            }
            System.out.print("Enter \"Y\" If You Want to Answer Another Question: ");
            String response = sc.next();
            if (!response.equalsIgnoreCase("Y")) break;
            System.out.println();
        }
        sc.close();
    }
}