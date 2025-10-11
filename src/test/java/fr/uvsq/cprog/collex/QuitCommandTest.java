package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class QuitCommandTest {

  @Test
  void testExecute() {
    QuitCommand cmd = new QuitCommand();
    assertDoesNotThrow(() -> cmd.execute(null));
    assertEquals("", cmd.execute(null));
  }
}
