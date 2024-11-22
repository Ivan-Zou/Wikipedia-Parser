import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;


/*
 * Class that uses JSoup to parse the Oscars Wikipedia Page and retrieve information from that page to answer questions
 */
public class WikipediaParser {
    private final String wikiURL = "https://en.wikipedia.org";
    private Document oscarsWiki;

    public WikipediaParser() {
        try {
            this.oscarsWiki = Jsoup.connect("https://en.wikipedia.org/wiki/Academy_Awards").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * This method prints all discontinued categories for awards.
     */
    public void printDiscontinuedCategories() {
        // get the discontinued heading using its ID
        Element discontinuedCategoriesHead = oscarsWiki.getElementById("Discontinued_categories").parent();
        // get the list right under this heading
        Element list = discontinuedCategoriesHead.nextElementSiblings().select("ul").first();
        // get the list items in this list
        Elements listItems = list.select("li");
        System.out.println("Discontinued Categories: ");
        // iterate through the list items and print each one out
        for (Element item : listItems) {
            // print the category without the date that is after ":"
            System.out.println("- " + item.text().split(":")[0]);
        }
    }

    /*
     * This method prints the number of award categories added in a given decade (ie 1940s).
     * @param decade    integer of the decade of interest
     */
    public void printCategoriesAdded(int decade) {
        int count = 0;
        // get the current categories heading with its ID
        Element currentCategoriesHead = oscarsWiki.getElementById("Current_categories").parent();
        // get the table right under the current categories heading
        Element table = currentCategoriesHead.nextElementSiblings().select("table.wikitable").first();
        // get the columns of the years that this category was introduced
        Elements yearsIntroduced = table.select("tr td:eq(0)");
        // iterate through this column of years
        for (Element year : yearsIntroduced) {
            // get the integer value of the year
            int currYear = Integer.parseInt(year.text().substring(0, 4));
            // if this integer is in the same decade as the given decade then increment count
            if (currYear >= decade && currYear < decade + 10) {
                count++;
            }
        }
        // get the mapping from discontinued category to its year introduced
        HashMap<String, Integer> map = getDiscontinuedCategoriesIntroducedYearMap();
        // iterate through the map and increment count accordingly
        for (String category : map.keySet()) {
            int yearIntroduced = map.get(category);
            if (yearIntroduced >= decade && yearIntroduced < decade + 10) {
                count++;
            }
        }
        System.out.println(count + " award categories were added in the " + decade + "s");
    }

    /*
     * This method returns the mapping of the discontinued categories to the year it was introduced where the
     * year is not in the same decade
     * @return      hashmap of the mapping
     */
    private HashMap<String, Integer> getDiscontinuedCategoriesIntroducedYearMap() {
        HashMap<String, Integer> map = new HashMap<>();
        // get the discontinued heading using its ID
        Element discontinuedCategoriesHead = oscarsWiki.getElementById("Discontinued_categories").parent();
        // get the list right under this heading
        Element categoryList = discontinuedCategoriesHead.nextElementSiblings().select("ul").first();
        // get the list items in this list
        Elements categories = categoryList.select("li");
        // iterate through the categories
        for (Element category : categories) {
            String categoryText = category.text();
            // split the text by : to separate the category from the date
            String[] categoryAndYear = categoryText.split(":");
            // get rid of trailing whitespace
            String year = categoryAndYear[1].trim();
            // if the length is less than 8, then the category was discontinued within a year, so it does not count
            // so go to the next category
            if (year.length() < 8) continue;
            String yearIntroduced = year.substring(0, 4);
            String yearDiscontinued = year.substring(year.length() - 4);
            // if the decade is the same, then we don't count it so go to the next category
            if (yearIntroduced.charAt(2) == yearDiscontinued.charAt(2)) continue;
            // otherwise, we add the category to the map with the year it was introduced
            map.put(categoryAndYear[0], Integer.parseInt(yearIntroduced));
        }
        return map;
    }

    /*
     * This method prints all the movies that were nominated for at least a number of awards in a given year.
     * @param numAwards integer of the number of awards
     * @param year      integer of the year of interest
     */
    public void printMoviesNominated(int numAwards, int year) {
        try {
            String ceremonyURL = ceremonyWikiURL(year);
            // get the ceremony of the given year's wiki page
            Document ceremonyWiki = Jsoup.parse(new URL(ceremonyURL).openStream(), "ISO-8859-1", ceremonyURL);
            // get the awards table
            Element awardsTable = ceremonyWiki.select("table.wikitable").first();
            // get all movies in the award table
            Elements movies = awardsTable.select("i");
            HashMap<String, Integer> movieToNominatedFreqMap = new HashMap<>();
            // iterate through the movies and update the frequency map
            for (Element movie : movies) {
                updateFreqMap(movieToNominatedFreqMap, movie.text());
            }
            // if the map is empty, then there is nothing to print, so end
            if (movieToNominatedFreqMap.isEmpty()) return;
            System.out.println("Movies nominated for at least " + numAwards + " in " + year + ": ");
            // iterate through the map, and print the results
            for (String movie : movieToNominatedFreqMap.keySet()) {
                // if the number of nominations is at least numAwards, then print the movie
                if (movieToNominatedFreqMap.get(movie) >= numAwards) {
                    System.out.println("- " + movie);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Format of Wikipedia Must Have Changed :(");
        } catch (Exception e) {
            System.out.println("Page Not Found: Insert a Different Year");
        }
    }

    /*
     * This method returns the URL of the Wikipedia page of the ceremony held in the given year
     * @param year      integer of the year of the ceremony
     * @return          the string of the URL
     */
    private String ceremonyWikiURL(int year) {
        // get the list of all the years of the oscars ceremony using an ID
        Element listOfYears = oscarsWiki.getElementById("fn_‡_back").parent().nextElementSibling()
                .select("ul").first();
        // get the relevant year
        Element ceremonyYear = getRelevantTagFromElementContainsKey(listOfYears, "a", Integer.toString(year));
        // if this year does not exist, then end
        if (ceremonyYear == null) return null;
        // otherwise, return the URL to this ceremony
        return wikiURL + ceremonyYear.attr("href");
    }

    /*
     * This method returns the relevant tag that contains the keyword the element, tag type, and the keyword
     * @param element       the element to look for the tag in
     * @param tagType       string of tag type to search through
     * @param keyword       string of keyword to look for in the tags
     * @return              element of the relevant tag
     */
    private Element getRelevantTagFromElementContainsKey(Element element, String tagType, String keyword) {
        // get all tags of the given tagType in the given element
        Elements tags = element.select(tagType);
        Element relevantTag = null;
        // iterate through all the tags to find the one we want
        for (Element tag : tags) {
            // if the tag contains the keyword, then it is the tag we want,
            // so we set that tag as the relevant tag and break out of the loop
            if (tag.text().toLowerCase().contains(keyword.toLowerCase())) {
                relevantTag = tag;
                break;
            }
        }
        return relevantTag;
    }

    /*
     * This method updates the frequency map
     * @param freqMap       hash map to be updated
     * @param input         the string to update the map with
     */
    private void updateFreqMap(HashMap<String, Integer> freqMap, String input) {
        if (freqMap.containsKey(input)) {
            freqMap.put(input, freqMap.get(input) + 1);
        } else {
            freqMap.put(input, 1);
        }
    }

    /*
     * This method prints the winner of a given category in a given year.
     * @param category  string of award category
     * @param year      integer of the year of interest
     */
    public void printWinner(String category, int year) {
        Element winner = getWinner(category, year);
        if (winner != null) {
            System.out.println("The winner of " + category + " in " + year + " is " + winner.text() + "!");
        }
    }

    /*
     * This method returns the HTML element of the winner of a category in a given year
     * @param category  string of award category
     * @param year      integer of the year of interest
     * @return          the Element of the winner
     */
    private Element getWinner(String category, int year) {
        Element winner = null;
        try {
            String ceremonyURL = ceremonyWikiURL(year);
            // get the ceremony of the given year's wiki page
            Document ceremonyWiki = Jsoup.parse(new URL(ceremonyURL).openStream(), "ISO-8859-1", ceremonyURL);
            // get the awards table
            Element awardsTable = ceremonyWiki.select("table.wikitable").first();
            // get the relevant award in the awards table
            Element relevantAward = getRelevantTagFromElementContainsKey(awardsTable, "a", category);
            // if this award does not exist, then end
            if (relevantAward == null) {
                System.out.println("The " + category + " Award did not exist in " + year);
                return null;
            }
            // get the winner of the award
            winner = relevantAward.parent().parent().nextElementSibling().select("a").first();
        } catch (NullPointerException e) {
            System.out.println("Format of Wikipedia Must Have Changed :(");
        } catch (Exception e) {
            System.out.println("Page Not Found: Insert a Different Year");
        }
        return winner;
    }


    /*
     * This method print the budget for winning movie in a given category in a given year, and the amount
     * this movie made in the box office.
     * @param category  string of award category
     * @param year      integer of the year of interest
     */
    public void printBudgetAndBoxOfficeSalesOfWinningMovie(String category, int year) {
        try {
            // get the winner of the given award category in the given year
            Element winner = getWinner(category, year);
            // if the winner does not exist, then end
            if (winner == null) return;
            // get the URL of the winning movie
            String movieURL = wikiURL + winner.attr("href");
            // get the winning movie's wiki page
            Document movieWiki = Jsoup.parse(new URL(movieURL).openStream(), "ISO-8859-1", movieURL);
            // get the info box of the movie
            Element infoBox = movieWiki.select("table.infobox").first();
            // get the relevant headings from the info box
            Element budgetHead = getRelevantTagFromElementContainsKey(infoBox, "th", "budget");
            Element boxOfficeHead = getRelevantTagFromElementContainsKey(infoBox, "th", "box office");
            // if there are no headings for the budget or box office, then end
            if (budgetHead == null || boxOfficeHead == null) {
                System.out.println("The winner of " + category + " is either not a movie, ");
                System.out.println("or does not have both a budget and box office sales on record");
                return;
            }
            // get the values from the headings, and get rid of the "[...]"
            String budget = budgetHead.nextElementSibling().text().split("\\[")[0];
            String boxOffice = boxOfficeHead.nextElementSibling().text().split("\\[")[0];
            System.out.println("The winner of " + category + " in " + year + ", " + winner.text() + ", ");
            System.out.println("has a budget of " + budget + ", and made " + boxOffice + " in box office sales!");
        } catch (NullPointerException e) {
            System.out.println("Format of Wikipedia Must Have Changed :(");
        } catch (Exception e) {
            System.out.println("Page Not Found");
        }
    }

    /*
     * This method prints the academic institution with the highest number of alumni
     * nominated for a given award category
     * @param category  string of award category
     */
    public void printAcademicInstitutionWithHighestNominees(String category) {
        try {
            // get the URL for the given award category
            String awardURL = awardWikiURL(category);
            if (awardURL == null) {
                System.out.println("The " + category + " Award does not exist");
                return;
            }
            // get the given award category's wiki page
            Document awardWiki = Jsoup.parse(new URL(awardURL).openStream(), "ISO-8859-1", awardURL);
            // get the winner and nominees heading
            Element winnerAndNomineesHead = getRelevantTagFromPage(awardWiki, "h2", "winners and nominees");
            // get the nominee tables under the year headings and store them in a list
            LinkedList<Element> nomineeTables = getNomineeTables(winnerAndNomineesHead);
            // get the nominee URLs from the nominee tables
            LinkedList<String> nomineeURLs = getNomineeURLs(nomineeTables);
            // Hash map to track the number of nominees in a school
            HashMap<String, Integer> schoolFreqMap = new HashMap<>();
            // iterate through the nominee's URLs to go to their wiki page and retrieve their school
            for (String nomineeURL : nomineeURLs) {
                Document nomineeWiki = Jsoup.parse(new URL(nomineeURL).openStream(), "ISO-8859-1", nomineeURL);
                Element infoBox = nomineeWiki.select("table.infobox").first();
                // if wiki page does not have an info box then go to the next nominee wiki page
                if (infoBox == null) continue;
                Element schoolHead = getRelevantTagFromElementContainsKey(infoBox, "th", "alma mater");
                // if there is no alma mater heading, check to see if there is an education heading
                if (schoolHead == null) {
                    schoolHead = getRelevantTagFromElementContainsKey(infoBox, "th", "education");
                }
                // if there is no education or alma mater heading in the info box then go to the next nominee wiki page
                if (schoolHead == null) continue;
                // get the schools the nominees went to
                Elements schools = schoolHead.nextElementSibling().select("a");
                // iterate through the schools the nominee had gone to
                for (Element school : schools) {
                    String schoolName = school.text();
                    // if the school name is not a degree (ie BA, MFA), then increment the schoolFreqMap accordingly
                    if (schoolName.length() > 5) {
                        updateFreqMap(schoolFreqMap, schoolName);
                    }
                }
            }
            // Find the school with the most nominees
            int max = 0;
            String mostNomineesSchool = null;
            for (String school : schoolFreqMap.keySet()) {
                int freq = schoolFreqMap.get(school);
                if (freq > max) {
                    max = freq;
                    mostNomineesSchool = school;
                }
            }
            System.out.println("The academic institution with the highest number of alumni nominated for ");
            System.out.println(category + " is " + mostNomineesSchool + "!!!");
        } catch (NullPointerException e) {
            System.out.println("Format of Wikipedia Must Have Changed :(");
            System.out.println("Or... You've inputted an Invalid Award Category!!!");
        } catch (Exception e) {
            System.out.println("Page Not Found: Insert a Different Category");
        }
    }

    /*
     * This method returns the URL of the Wikipedia page of a given award category
     * @param category  string of the award category
     * @return          the string of the URL
     */
    private String awardWikiURL(String category) {
        // Get the current categories heading
        Element currentCategoriesHead = oscarsWiki.getElementById("Current_categories").parent();
        // get the current categories table from the heading
        Element table = currentCategoriesHead.nextElementSiblings().select("table.wikitable").first();
        // get the relevant category from the table
        Element categories = getRelevantTagFromElementContainsKey(table, "tr td:eq(1)", category);
        // if the relevant category does not exist, return null
        if (categories == null) return null;
        // otherwise get the URL of the award category and return it
        return wikiURL + categories.select("a").first().attr("href");
    }

    /*
     * This method returns the relevant tag given the page, tag type, and the keyword
     * @param page          document to look for the tag in
     * @param tagType       string of tag type to search through
     * @param keyword       string of keyword to look for in the tags
     * @return              element of the relevant tag
     */
    private Element getRelevantTagFromPage(Document page, String tagType, String keyword) {
        Element relevantTag = null;
        // get all the tage of type tagType in the page
        Elements tags = page.select(tagType);
        // iterate through all the tags of type tagType to find to one we want
        for (Element tag : tags) {
            // if the tag contains the keyword, then it is the tag we want,
            // so we set that tag as the relevant tag and break out of the loop
            if (tag.text().toLowerCase().contains(keyword.toLowerCase())) {
                relevantTag = tag;
                break;
            }
        }
        return relevantTag;
    }

    /*
     * This method returns a list of all the nominee table under the year/decade heading
     * @param yearHeadings      element of year/decade headings
     * @returns                 the linked list of nominee tables
     */
    private LinkedList<Element> getNomineeTables(Element winnerNomineeHead) {
        LinkedList<Element> nomineeTables = new LinkedList<>();
        // get the year/decade heading that are after the winner and nominees heading
        Elements yearHeadings = winnerNomineeHead.nextElementSiblings().select("h3");
        // iterate through headings to retrieve each table
        for (Element yearHeading : yearHeadings) {
            Element possibleTable = yearHeading.nextElementSibling();
            // if the element exists and is not a table, then go to the next element
            while (possibleTable != null && !possibleTable.hasClass("wikitable")) {
                possibleTable = possibleTable.nextElementSibling();
            }
            // if the element exists and is a table, then add it to the list of nominee tables
            if (possibleTable != null && possibleTable.hasClass("wikitable")) {
                nomineeTables.add(possibleTable);
            }
        }
        return nomineeTables;
    }

    /*
     * This method returns the nominee's URLs inside the list of nominee tables
     * @param nomineeTables     linked list of nominee tables
     * @return                  linked list of URL strings
     */
    private LinkedList<String> getNomineeURLs(LinkedList<Element> nomineeTables) {
        LinkedList<String> nomineeURLs = new LinkedList<>();
        // iterate through the tables to retrieve the URL of each nominee in this table
        for (Element table : nomineeTables) {
            // get the column with the nominees
            Elements nomineeCol = table.select("tr td:eq(0)");
            for (Element nominee : nomineeCol) {
                // get the nominee's wiki URL
                String nomineeURL = nominee.select("a").attr("href");
                // if there is a URL, then add it to the list
                if (!nomineeURL.equals("")) {
                    nomineeURLs.add(wikiURL + nomineeURL);
                }
            }
        }
        return nomineeURLs;
    }

    /*
     * This method prints the total number of times a country has been nominated for given an award category
     * @param category      string of award category
     */
    public void printTotalNumOfNominationsForNominatedCountries(String category) {
        try {
            // get the URL for the given award category
            String awardURL = awardWikiURL(category);
            if (awardURL == null) {
                System.out.println("The " + category + " Award does not exist");
                return;
            }
            // get the given award category's wiki page
            Document awardWiki = Jsoup.parse(new URL(awardURL).openStream(), "ISO-8859-1", awardURL);
            // get the winner and nominees heading
            Element winnerAndNomineesHead = getRelevantTagFromPage(awardWiki, "h2", "winners and nominees");
            // get the nominee tables under the year headings and store them in a list
            LinkedList<Element> nomineeTables = getNomineeTables(winnerAndNomineesHead);
            // if there are no nominee tables, then this page must be the page with a URL
            if (nomineeTables.isEmpty()) {
                // get the URL under the winner and nominees heading
                String innerURL = wikiURL + winnerAndNomineesHead.nextElementSibling().nextElementSiblings().select("a")
                        .attr("href");
                // update the award wiki page to the new URL
                awardWiki =  Jsoup.parse(new URL(innerURL).openStream(), "ISO-8859-1", innerURL);
                // update the winner and nominees headings to that of the new award wiki page
                winnerAndNomineesHead = getRelevantTagFromPage(awardWiki, "h2", "winners and nominees");
                // update the list of nominees table to be the tables in this wiki page
                nomineeTables = getNomineeTables(winnerAndNomineesHead);
            }
            // get the nominee URLs from the nominee tables
            LinkedList<String> nomineeURLs = getNomineeURLs(nomineeTables);
            // Hash map to track the number of nominees in a school
            HashMap<String, Integer> countryFreqMap = new HashMap<>();
            // iterate through the nominee's URLs to go to their wiki page and retrieve their school
            for (String nomineeURL : nomineeURLs) {
                Document nomineeWiki = Jsoup.parse(new URL(nomineeURL).openStream(), "ISO-8859-1", nomineeURL);
                Element infoBox = nomineeWiki.select("table.infobox").first();
                // if wiki page does not have an info box then go to the next nominee wiki page
                if (infoBox == null) continue;
                Element countryHead = getRelevantTagFromElementEqualsKey(infoBox, "th", "country");
                // if there is no country heading, check to see if there is a countries heading
                if (countryHead == null) {
                    countryHead = getRelevantTagFromElementEqualsKey(infoBox, "th", "countries");
                }
                // if there is no country or countries heading in the info box then go to the next nominee wiki page
                if (countryHead == null) continue;
                // get the country of the nominee
                Element country = countryHead.nextElementSiblings().first();
                String countryName = "";
                // if the country element has no children, then it's just a single country
                if (country.childrenSize() == 0) {
                    // get the country name and get rid of [...] and (...)
                    countryName = country.text().split("\\[")[0].split("\\(")[0];
                    updateFreqMap(countryFreqMap, countryName);
                    // Otherwise, there is a list of countries
                } else {
                    Elements countries = country.select("li");
                    // iterate through the countries and update the map
                    for (Element c : countries) {
                        countryName = c.text().split("\\[")[0].split("\\(")[0];
                        updateFreqMap(countryFreqMap, countryName);
                    }
                }
            }
            // iterate through the country frequency map and print
            System.out.println("For the countries that were nominated for/won " + category + ": ");
            for (String country : countryFreqMap.keySet()) {
                System.out.println("- " + country + " was nominated " + countryFreqMap.get(country) + " times!");
            }
        } catch (NullPointerException e) {
            System.out.println("Format of Wikipedia Must Have Changed :(");
            System.out.println("Or... You've inputted an Invalid Award Category!!!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Page Not Found: Insert a Different Category");
            e.printStackTrace();
        }
    }

    /*
     * This method returns the relevant tag that equals the keyword given the element, tag type, and the keyword
     * @param element       the element to look for the tag in
     * @param tagType       string of tag type to search through
     * @param keyword       string of keyword to look for in the tags
     * @return              element of the relevant tag
     */
    private Element getRelevantTagFromElementEqualsKey(Element element, String tagType, String keyword) {
        // get all tags of the given tagType in the given element
        Elements tags = element.select(tagType);
        Element relevantTag = null;
        // iterate through all the tags to find the one we want
        for (Element tag : tags) {
            // if the tag equals the keyword, then it is the tag we want,
            // so we set that tag as the relevant tag and break out of the loop
            if (tag.text().equalsIgnoreCase(keyword)) {
                relevantTag = tag;
                break;
            }
        }
        return relevantTag;
    }


    /*
     * This method prints the number of nominees with the given birth month who was nominated for the given award
     * @param category      string of award category
     * @param birthMonth    string of a month in the year
     */
    public void printNumOfNomineesOfBirthMonth(String category, String birthMonth) {
        try {
            // get the URL for the given award category
            String awardURL = awardWikiURL(category);
            if (awardURL == null) {
                System.out.println("The " + category + " Award does not exist");
                return;
            }
            // get the given award category's wiki page
            Document awardWiki = Jsoup.parse(new URL(awardURL).openStream(), "ISO-8859-1", awardURL);
            // initialize the birth month frequency map with all months of the year with a frequency of 0
            HashMap<String, Integer> birthMonthFreqMap = new HashMap<>();
            birthMonthFreqMap.put("january", 0);
            birthMonthFreqMap.put("february", 0);
            birthMonthFreqMap.put("march", 0);
            birthMonthFreqMap.put("april", 0);
            birthMonthFreqMap.put("may", 0);
            birthMonthFreqMap.put("june", 0);
            birthMonthFreqMap.put("july", 0);
            birthMonthFreqMap.put("august", 0);
            birthMonthFreqMap.put("september", 0);
            birthMonthFreqMap.put("october", 0);
            birthMonthFreqMap.put("november", 0);
            birthMonthFreqMap.put("december", 0);

            if (!birthMonthFreqMap.containsKey(birthMonth.toLowerCase())) {
                System.out.println("Invalid Input: " + birthMonth + " is not a month");
                return;
            }
            // get the winner and nominees heading
            Element winnerAndNomineesHead = getRelevantTagFromPage(awardWiki, "h2", "winners and nominees");
            // get the nominee tables under the year headings and store them in a list
            LinkedList<Element> nomineeTables = getNomineeTables(winnerAndNomineesHead);
            // get the nominee URLs from the nominee tables
            LinkedList<String> nomineeURLs = getNomineeURLs(nomineeTables);
            // iterate through the URLs to go to the nominee's wiki page and get their birth month
            for (String nomineeURL : nomineeURLs) {
                Document nomineeWiki = Jsoup.parse(new URL(nomineeURL).openStream(), "ISO-8859-1", nomineeURL);
                Element infoBox = nomineeWiki.select("table.infobox").first();
                // if wiki page does not have an info box then go to the next nominee wiki page
                if (infoBox == null) continue;
                Element bornHead = getRelevantTagFromElementEqualsKey(infoBox, "th", "born");
                // if there is no born heading, then there is no birth month on record so go to the next nominee wiki page
                if (bornHead == null) continue;
                // get the string of information in the born head
                String bornInfo = bornHead.nextElementSibling().text();
                // for each month in the year, if the born info contains that month,
                // then increment the frequency of that month
                for (String month : birthMonthFreqMap.keySet()) {
                    if (bornInfo.toLowerCase().contains(month)) {
                        updateFreqMap(birthMonthFreqMap, month);
                    }
                }
            }
            System.out.println("For those who were nominated for " + category + ", " +
                    birthMonthFreqMap.get(birthMonth.toLowerCase()) + " were born in " + birthMonth + "!");
        } catch (NullPointerException e) {
            System.out.println("Format of Wikipedia Must Have Changed :(");
            System.out.println("Or... You've inputted an Invalid Award Category!!!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Page Not Found: Insert a Different Category");
            e.printStackTrace();
        }
    }
}
