import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class FactDatabase {
    
    private final String dbUrl;

    FactDatabase(String dbUrl) {
        this.dbUrl = dbUrl;
    }

        /** GET /facts */
        List<CatFact> getAllFacts() throws SQLException {

            // create an empty List<CatFact> 
            List<CatFact> newFactList = new ArrayList<>();

            String sql = "SELECT id, fact FROM facts ORDER BY id";
            // need to create the connection of pass it into the parameter for getAllFacts?
            try (Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

                System.out.println("Within our getAllFacts method");
                
                while (rs.next())
                {
                    newFactList.add(new CatFact(rs.getInt("id"), rs.getString("fact")));
                }

            } catch (Exception error)
            {
                error.printStackTrace();
            } 
            
            // finally {
            //     rs.close();
            //     pstmt.close();
            //     conn.close();
            // }

            return newFactList;
            // sendJson(exchange, 200, newFactList);


        }

        /** GET /facts/{id} */
        Optional<CatFact> findFactById(int id) throws SQLException {

            System.out.println("Within our findFactById method");
            String sql = "SELECT id, fact FROM facts WHERE id = ?";

            try (Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
                
                // set the first position 1 to id
                pstmt.setInt(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next())
                    {
                        // set a reference variable we can pass into send Json
                        CatFact catFact = new CatFact(rs.getInt("id"), rs.getString("fact"));

                        return Optional.of(catFact);

                    } else {

                        return Optional.empty();

                    }
                }

            } catch (Exception error)
            {
                error.printStackTrace();
            } 
            return Optional.empty();
        }

        CatFact insertFact(String text) throws SQLException {

        String sql = "INSERT INTO facts (fact) VALUES (?) RETURNING id";

        try (Connection conn = DriverManager.getConnection(dbUrl);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, text);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return new CatFact(rs.getInt("id"), text);
            }
        }
    }
}
