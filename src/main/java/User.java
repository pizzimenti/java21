import java.util.*;
import org.sql2o.*;

public class User {
  private int id;
  private String name;
  private String password;
  private String permissions;

  public User(String name, String password, String permissions) {
    this.name = name;
    this.password = password;
    this.permissions = permissions;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getPermissions() {
    return permissions;
  }

  public static List<User> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users";
      return con.createQuery(sql).executeAndFetch(User.class);
    }
  }

  @Override
  public boolean equals(Object otherUser) {
    if(!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return newUser.getId() == (id);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (name, password, permissions) VALUES (:name, :password, :permissions)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", name).addParameter("password", password).addParameter("permissions", permissions).executeUpdate().getKey();
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(User.class);    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM users WHERE id = :id";
      con.createQuery(sql).addParameter("id", id).executeUpdate();
    }
  }

  public String passwordHint() {
    String puzzlePassword = password;
    char[] vowels = {'A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u'};
    for(char vowel : vowels) {
      puzzlePassword = puzzlePassword.replace(vowel, '-');
    }
    return puzzlePassword;
  }
}
