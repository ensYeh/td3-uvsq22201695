package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ErrorCommandTest {

  @Test
  void testExecuteRetourneMessageErreur() {
    ErrorCommand cmd = new ErrorCommand("Nom de domaine invalide");
    String resultat = cmd.execute(null);
    assertEquals("ERREUR : Nom de domaine invalide", resultat);
  }

  @Test
  void testExecuteAvecMessageVide() {
    ErrorCommand cmd = new ErrorCommand("");
    String resultat = cmd.execute(null);
    assertEquals("ERREUR : ", resultat);
  }

}
