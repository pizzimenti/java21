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
  public void equals_returnsTrueIfTurnsHaveSameId() {
    Turn newTurn = new Turn();
    Turn newTurn2 = new Turn();
    assertTrue(newTurn.equals(newTurn2));
  }

  @Test
  public void save_savesTurnToDatabase() {
    Turn newTurn = new Turn();
    newTurn.save();
    assertTrue(Turn.all().get(0).equals(newTurn));
  }

  @Test
  public void save_savesTurnIdToDatabase() {
    Turn newTurn = new Turn();
    newTurn.save();
    assertEquals(Turn.all().get(0).getId(),newTurn.getId());
  }

  @Test
  public void find_returnsTurnBasedOnId() {
    Turn newTurn = new Turn();
    newTurn.save();
    Turn newTurn2 = new Turn();
    newTurn2.save();
    assertTrue(Turn.find(newTurn.getId()).equals(newTurn));
    assertTrue(Turn.find(newTurn2.getId()).equals(newTurn2));
  }

  @Test
  public void delete_removesTurnFromDatabase() {
    Turn newTurn = new Turn();
    newTurn.save();
    newTurn.delete();
    assertEquals(Turn.all().size(), 0);
  }

  @Test
  public void update_updatesUserGuess() {
    Turn newTurn = new Turn();
    newTurn.save();
    newTurn.update("yellow");
    assertTrue(Turn.find(newTurn.getId()).getUserTurn().equals("yellow"));
  }

  @Test
  public void deleteUserGuess_removesUserTurnFromDatabase() {
    Turn newTurn = new Turn();
    newTurn.save();
    newTurn.update("yellow");
    Turn newTurn2 = new Turn();
    newTurn2.save();
    newTurn2.update("blue");
    Turn.deleteUserGuess();
    assertEquals(Turn.find(newTurn.getId()).getUserTurn(), null);
    assertEquals(Turn.find(newTurn2.getId()).getUserTurn(), null);
  }

  @Test
  public void isFull_reutrnsTrueIfUserHasGuessedAppropriateNumberOfTimes() {
    Turn newTurn = new Turn();
    newTurn.save();
    Turn newTurn2 = new Turn();
    newTurn2.save();
    newTurn.update("yellow");
    newTurn2.update("blue");
    assertTrue(Turn.isFull());
  }

  @Test
  public void getCurrentTurn_returnsFirstCompTurnInCompTurnSequence() {
    Turn newTurn = new Turn();
    newTurn.save();
    Turn newTurn2 = new Turn();
    newTurn2.save();
    newTurn.update("blue");
    assertTrue(Turn.getCurrentTurn().equals(newTurn2));
  }

  @Test
  public void getNextUnshownTurn_returnNextTurnThatHasNotBeenShown() {
    Turn newTurn = new Turn();
    newTurn.save();
    Turn newTurn2 = new Turn();
    newTurn2.save();
    newTurn.updateShownStatus();
    assertTrue(Turn.getNextUnshownTurn().equals(newTurn2));
  }

  @Test
  public void allShown_returnsTrueIfAllTurnsHaveBeenShown() {
    Turn newTurn = new Turn();
    newTurn.save();
    Turn newTurn2 = new Turn();
    newTurn2.save();
    newTurn.updateShownStatus();
    newTurn2.updateShownStatus();
    assertTrue(Turn.allShown());
  }
}
