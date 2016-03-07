import java.util.*;
import org.sql2o.*;

import org.junit.*;
import static org.junit.Assert.*;

public class TurnTest {

  @Rule
  DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Turn.all().size());
  }
}
