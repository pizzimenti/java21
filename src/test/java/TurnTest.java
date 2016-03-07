import java.util.*;
import org.sql2o.*;

import org.junit.*;
import static org.junit.Assert.*;

public class TurnTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Turn.all().size());
  }

  @Test
  public void equals_returnsTrueIfTurnsHaveSameNameAndMusic() {
    Turn newTurn = new Turn("yellow");
    Turn newTurn2 = new Turn("yellow");
    assertTrue(newTurn.equals(newTurn2));
  }

  @Test
  public void save_savesTurnToDatabase() {
    Turn newTurn = new Turn("yellow");
    newTurn.save();
    assertTrue(Turn.all().get(0).equals(newTurn));
  }

  @Test
  public void save_savesTurnIdToDatabase() {
    Turn newTurn = new Turn("yellow");
    newTurn.save();
    assertEquals(Turn.all().get(0).getId(),newTurn.getId());
  }

  @Test
  public void find_returnsTurnBasedOnId() {
    Turn newTurn = new Turn("yellow");
    newTurn.save();
    Turn newTurn2 = new Turn("blue");
    newTurn2.save();
    assertTrue(Turn.find(newTurn.getId()).equals(newTurn));
    assertTrue(Turn.find(newTurn2.getId()).equals(newTurn2));
  }

  @Test
  public void delete_removesTurnFromDatabase() {
    Turn newTurn = new Turn("yellow");
    newTurn.save();
    newTurn.delete();
    assertEquals(Turn.all().size(), 0);
  }

  @Test
  public void deleteUserGuess_removesUserTurnFromDatabase() {
    Turn newTurn = new Turn("yellow");
    newTurn.save();
    newTurn.update("yellow");
    Turn newTurn2 = new Turn("blue");
    newTurn2.save();
    newTurn2.update("blue");
    newTurn.deleteUserGuess();
    assertEquals(newTurn.getUserTurn(), null);
    assertEquals(newTurn2.getUserTurn(), null);
  }
}
