import java.util.*;
import org.sql2o.*;

public class Turn {
  private int id;
  private String generatedColor;

  public Turn(String generatedColor) {
    this.generatedColor = generatedColor;
  }

  public getId() {
    return id;
  }

  public getGeneratedColor() {
    return generatedColor;
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
      return newTurn.getId().equals(id) && newTurn.getGeneratedColor().equals(getGeneratedColor);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO turns (generatedColor) VALUES (:generatedColor)";
      this.id = (int) con.createQuery(sql, true).addParameter("generatedColor", generatedColor).executeUpdate().getKey();
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
      String sql = "UPDATE turns SET user_guess = null";
      con.createQuery(sql).executeUpdate();
    }
  }

  public void update(String user_guess) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE turns SET user_guess = :user_guess WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).addParameter("user_guess", user_guess).executeUpdate();
    }
  }
}
