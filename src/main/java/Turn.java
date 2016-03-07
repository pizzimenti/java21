import java.util.*;
import org.sql2o.*;

public class Turn {
  private int id;
  private String comp_turn;
  private String user_turn;

  public Turn(String comp_turn) {
    this.comp_turn = comp_turn;
  }

  public int getId() {
    return id;
  }

  public String getGeneratedColor() {
    return comp_turn;
  }

  public String getUserTurn() {
    return user_turn;
  }

  public static List<Turn> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM turns";
      return con.createQuery(sql).executeAndFetch(Turn.class);
    }
  }

  @Override
  public boolean equals(Object otherTurn) {
    if(!(otherTurn instanceof Turn)) {
      return false;
    } else {
      Turn newTurn = (Turn) otherTurn;
      return newTurn.getId() == (id) && newTurn.getGeneratedColor().equals(comp_turn);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO turns (comp_turn) VALUES (:comp_turn)";
      this.id = (int) con.createQuery(sql, true).addParameter("comp_turn", comp_turn).executeUpdate().getKey();
    }
  }

  public static Turn find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM turns WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Turn.class);    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM turns";
      con.createQuery(sql).executeUpdate();
    }
  }

  public void deleteUserGuess() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE turns SET user_turn = null";
      con.createQuery(sql).executeUpdate();
    }
  }

  public void update(String user_guess) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE turns SET user_turn = :user_guess WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).addParameter("user_guess", user_guess).executeUpdate();
    }
  }
}
