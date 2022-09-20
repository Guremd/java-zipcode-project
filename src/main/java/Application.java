import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.app.mailingaddress.data.dto.ZipCodeDTO;
import com.kenzie.app.mailingaddress.data.format.AddressFormatter;
import com.kenzie.app.mailingaddress.data.http.HTTPConnector;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try {
            //variables
            String BASE_URL = "https://api.zippopotam.us/";
            String path;
            Scanner scanner = new Scanner(System.in);
            String streetAddress;
            String city;
            String state; //= ENUM
            String zipCode;
            String urlPath;
            String httpResponse;
            String recipientName;

            //scanner
            System.out.println("Enter recipient name:");
            recipientName = scanner.nextLine();
            System.out.println("Enter street address:");
            streetAddress = scanner.nextLine();
            System.out.println("Enter city:");
            city = scanner.nextLine();
            System.out.println("Enter state:");
            state = scanner.nextLine();

            System.out.println("City: " + city);

            //user input
            String city_URL = city.replaceAll(" ", "%20");
            System.out.println(city_URL);

            //URL
            //append BASE_URL to the path
            urlPath = BASE_URL + "us/" + state + "/" + city_URL;
            System.out.println(urlPath);

            //Call HTTP Get
            httpResponse = HTTPConnector.makeGETRequest(urlPath);

            if (httpResponse.equals("\\{\\}")) {
                System.out.println("No valid zip code found");
            } else {


                //Object Mapper
                ObjectMapper objectMapper = new ObjectMapper();
                ZipCodeDTO zipObj = objectMapper.readValue(httpResponse, ZipCodeDTO.class);

                System.out.println("First zip:" + zipObj.getPlaces().get(0).getPostCode());

                //Loop and ask user for zip code if more than one
                //zipObj.getPlaces()

                if(zipObj.getPlaces().size()==1){
                    zipCode = zipObj.getPlaces().get(0).getPostCode();
                } else {
                    System.out.println("Choose a zip code.");
                    for (int i = 0; i < zipObj.getPlaces().size(); i++) {
                        System.out.println(zipObj.getPlaces().get(i).getPostCode());
                    }

                    //read in choice
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    //set zipCode to Choice
                    zipCode = zipObj.getPlaces().get(choice).getPostCode();
                }
                System.out.println("User chose zipcode: " + zipCode);

                AddressFormatter.initializeAddressMap();

                //address info
                System.out.println("Name: " + AddressFormatter.formatStreetAddress(recipientName));
                System.out.println("Street address: " + AddressFormatter.formatStreetAddress(streetAddress));
                System.out.println("City: " + AddressFormatter.formatAddress(city));
                System.out.println("State: " + AddressFormatter.formatAddress(state));
                System.out.println("Zipcode: " + AddressFormatter.formatAddress(zipCode));

            }
        } catch (Exception e) {
            System.out.println("ERROR ERROR ERROR: " + e);
        }
    }

    public static void main_backup(String[] args) {
        //API - https://api.zippopotam.us/us/ca/los%20angeles
        //jamboard - https://jamboard.google.com/d/1n9-68L50Ba8Yn1EMtG_Ri1jVhkridA7kb9uo7HPc7N4/viewer?pli=1&f=0

        try {
            //variables
            final String TEST_URL = "https://api.zippopotam.us/us/ca/los%20angeles";
            final String TEST_FAIL = "https://api.zippopotam.us/us/ca/los%20%20angeles";
            String httpResponse;

            //print out
            httpResponse = HTTPConnector.makeGETRequest(TEST_URL);
//            System.out.println(HTTPConnector.makeGETRequest(TEST_URL));

            //1. Instantiate ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            //2. Declare DTO
            ZipCodeDTO zipObj;

            //3. read Data - readValue
            zipObj = objectMapper.readValue(httpResponse, ZipCodeDTO.class);

            //print out
            System.out.println("City:" + zipObj.getPlaces().get(0).getPlace_name());
            System.out.println("Zip:" + zipObj.getPlaces().get(0).getPostCode());
            System.out.println("State:" + zipObj.getState());

            //loop and print list of zip codes
            if(zipObj.getPlaces().size() > 1) {
                for (int i = 0; i < zipObj.getPlaces().size(); i++) {
                    System.out.println("City:" + zipObj.getPlaces().get(i).getPlace_name());
                    System.out.println("Zip:" + zipObj.getPlaces().get(i).getPostCode());
                    System.out.println("State:" + zipObj.getState());
                }
            }

        }
        catch (Exception e) {
            System.out.println("Unexpected error: " + e);
        }


        /*System.out.println("\nFailure case");
        System.out.println(HTTPConnector.makeGETRequest(TEST_FAIL));*/
    }
}
