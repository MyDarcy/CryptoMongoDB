package data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author by darcy
 * Date on 17-4-22 下午2:17.
 * Description:
 */
public class DataToMongo {

    private static MongoClient mongoClient = new MongoClient();

    public static void main(String[] args) {
        // have already inserto into world database and city collection;
//        cityToPlainMongoDB();

        // country data has been to mongo;
//        countryToPlainMongoDB();

//        test3();
        countryLanguageToPlainMongoDB();
    }

    private static void countryLanguageToPlainMongoDB() {
        String databaseName = "world";
        String collectionName = "countryLanguage";
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        // for insert many;
        List<Document> countryLanguagies = new ArrayList<>();

        try {


            Connection connection = JDBCUtils.getConnection();
            Statement statement = connection.createStatement();

            String queryStr = "select * from countrylanguage";
            ResultSet resultSet = statement.executeQuery(queryStr);

            while (resultSet.next()) {
                String countryCode = resultSet.getString("CountryCode");
                String language = resultSet.getString("Language");
                String isOfficial = resultSet.getString("IsOfficial");
                double percentage = resultSet.getDouble("Percentage");

                Document document = new Document("countryCode", countryCode)
                        .append("language", language);
                if (isOfficial.equals("T")) {
                    document.append("isOfficial", true);
                } else {
                    document.append("isOfficial", false);
                }

                document.append("percentage", percentage);
                countryLanguagies.add(document);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        collection.insertMany(countryLanguagies);
    }

    private static void countryToPlainMongoDB() {
        String databaseName = "world";
        String collectionName = "country";
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        // for insert many;
        List<Document> countries = new ArrayList<>();
        try {
            Connection connection = JDBCUtils.getConnection();
            Statement statement = connection.createStatement();
            String cityQuery = "select * from country";
            ResultSet resultSet = statement.executeQuery(cityQuery);

            while (resultSet.next()) {
                String code = resultSet.getString("Code");
                String name = resultSet.getString("Name");
                String continent = resultSet.getString("Continent");
                String region = resultSet.getString("Region");
                double surfaceArea = resultSet.getDouble("SurfaceArea");
                int indepYear = resultSet.getInt("IndepYear");
                int population = resultSet.getInt("Population");
                double lifeExpectancy = resultSet.getDouble("LifeExpectancy");
                double GNP = resultSet.getDouble("GNP");
                double GNPOld = resultSet.getDouble("GNPOld");
                String localName = resultSet.getString("LocalName");
                String governmentForm = resultSet.getString("GovernmentForm");
                String headOfState = resultSet.getString("HeadOfState");
                String capital = resultSet.getString("capital");
                String code2 = resultSet.getString("Code2");

                Document document = new Document("code", code)
                        .append("name", name)
                        .append("continent", continent)
                        .append("region", region)
                        .append("surfaceArea", surfaceArea)
                        .append("indepYear", indepYear)
                        .append("population", population)
                        .append("lifeExpectancy", lifeExpectancy)
                        .append("GNP", GNP)
                        .append("GNPOld", GNPOld)
                        .append("localName", localName)
                        .append("governmentForm", governmentForm)
                        .append("headOfState", headOfState)
                        .append("capital", capital)
                        .append("code2", code2);

                countries.add(document);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        collection.insertMany(countries);
    }

    private static void cityToPlainMongoDB() {
        String databaseName = "world";
        String collectionName = "city";
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        // for insert many;
        List<Document> list = new ArrayList<>();
        try {
            Connection connection = JDBCUtils.getConnection();
            Statement statement = connection.createStatement();
            String cityQuery = "select * from city";
            ResultSet resultSet = statement.executeQuery(cityQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String countryCode = resultSet.getString("CountryCode");
                String district = resultSet.getString("District");
                int population = resultSet.getInt("Population");

                Document document = new Document("name", name)
                        .append("countryCode", countryCode)
                        .append("district", district)
                        .append("population", population);

                list.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        collection.insertMany(list);

    }

    private static void test() {
        try {
            /*Class.forName("com.data.jdbc.Driver");
            String jdbcUrl = "jdbc:data://localhost:3306/world";
            String root = "root";
            String passwd = "1993";
            Connection connection = DriverManager.getConnection(jdbcUrl, root, passwd);
            Statement statement = connection.createStatement();*/

            Connection connection = JDBCUtils.getConnection();
            Statement statement = connection.createStatement();

            String queryStr = "select * from city";
            ResultSet resultSet = statement.executeQuery(queryStr);

            System.out.println("id\tname\tcountryCode\tdistrict\tpopulation");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String countryCode = resultSet.getString("CountryCode");
                String district = resultSet.getString("District");
                int population = resultSet.getInt("Population");
                System.out.println(id + "\t" + name + "\t" + countryCode + "\t" + district + "\t" + population);

            }

            resultSet.close();
            statement.close();
            connection.close();

        }  catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void test2() {
        try {


            Connection connection = JDBCUtils.getConnection();
            Statement statement = connection.createStatement();

            String queryStr = "select * from country";
            ResultSet resultSet = statement.executeQuery(queryStr);

            System.out.println("Code\tName\tContinent\tRegion\tSurfaceArea\tIndepYear\tPopulation\tLifeExpectancy\tGNP\tGNPOld" +
                    "\tLocalName\tGovernmentForm\tHeadOfState\tCapital\tCode2");
            while (resultSet.next()) {
                String code = resultSet.getString("Code");
                String name = resultSet.getString("Name");
                String continent = resultSet.getString("Continent");
                String region = resultSet.getString("Region");
                double surfaceArea = resultSet.getDouble("SurfaceArea");
                int indepYear = resultSet.getInt("IndepYear");
                int population = resultSet.getInt("Population");
                double lifeExpectancy = resultSet.getDouble("LifeExpectancy");
                double GNP = resultSet.getDouble("GNP");
                double GNPOld = resultSet.getDouble("GNPOld");
                String localName = resultSet.getString("LocalName");
                String governmentForm = resultSet.getString("GovernmentForm");
                String headOfState = resultSet.getString("HeadOfState");
                String capital = resultSet.getString("capital");
                String code2 = resultSet.getString("Code2");

                System.out.println(code + "\t" + name + "\t" + continent + "\t" + region + "\t" + surfaceArea
                        + "\t" + indepYear + "\t" + population + "\t" + lifeExpectancy + "\t" + GNP + "\t" + GNPOld
                        + "\t" + localName + "\t" + governmentForm + "\t" + headOfState + "\t" + capital + "\t" + code2);

            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void test3(){
        try {


            Connection connection = JDBCUtils.getConnection();
            Statement statement = connection.createStatement();

            String queryStr = "select * from countrylanguage";
            ResultSet resultSet = statement.executeQuery(queryStr);

            System.out.println("countryCode\tlanguage\tisOfficial\tpercentage");
            while (resultSet.next()) {
                String countryCode = resultSet.getString("CountryCode");
                String language = resultSet.getString("Language");
                String isOfficial = resultSet.getString("IsOfficial");
                double percentage = resultSet.getDouble("Percentage");

                System.out.println(countryCode + "\t" + language + "\t" + isOfficial + "\t" + percentage);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
