package fr.uvsq.cprog.collex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class DnsTuiTest {

  private static DnsTui creerTui(String saisie, ByteArrayOutputStream sortieBuffer) {
    ByteArrayInputStream entree = new ByteArrayInputStream(saisie.getBytes());
    PrintStream sortie = new PrintStream(sortieBuffer, true);
    return new DnsTui(entree, sortie);
  }

  @Test
  void testQuitRetourneQuitCommand() {
    var buffer = new ByteArrayOutputStream();

    DnsTui tui = creerTui("quit\n", buffer);
    assertInstanceOf(QuitCommand.class, tui.nextCommande());

    tui = creerTui("q\n", buffer);
    assertInstanceOf(QuitCommand.class, tui.nextCommande());

    tui = creerTui("exit\n", buffer);
    assertInstanceOf(QuitCommand.class, tui.nextCommande());
  }

  @Test
  void testLigneVideRetourneNopCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("\n", buffer);
    assertInstanceOf(NopCommand.class, tui.nextCommande());
  }

  @Test
  void testLsSansOptionCreeListDomainCommandTriNom() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("ls uvsq.fr\n", buffer);
    assertInstanceOf(ListDomainCommand.class, tui.nextCommande());
  }

  @Test
  void testLsAvecOptionMoinsACreeListDomainCommandTriIp() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("ls -a uvsq.fr\n", buffer);
    assertInstanceOf(ListDomainCommand.class, tui.nextCommande());
  }

  @Test
  void testAddCreeAddCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("add 193.51.25.24 pikachu.uvsq.fr\n", buffer);
    assertInstanceOf(AddCommand.class, tui.nextCommande());
  }

  @Test
  void testAdresseIpRetourneLookupNameCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("193.51.31.90\n", buffer);
    assertInstanceOf(LookupNameCommand.class, tui.nextCommande());
  }

  @Test
  void testNomMachineRetourneLookupIpCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("www.uvsq.fr\n", buffer);
    assertInstanceOf(LookupIpCommand.class, tui.nextCommande());
  }

  @Test
  void testCommandeInvalideRetourneErrorCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("invalide truc\n", buffer);
    assertInstanceOf(ErrorCommand.class, tui.nextCommande());
  }

  @Test
  void testAfficheIgnoreNullEtVide() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("", buffer);

    tui.affiche(null);
    tui.affiche("   ");
    assertEquals("", buffer.toString());

    tui.affiche("OK");
    assertEquals("OK" + System.lineSeparator(), buffer.toString());
  }

  @Test
  void testEofRetourneQuitCommand() {
    var buffer = new ByteArrayOutputStream();
    DnsTui tui = creerTui("", buffer); // aucune ligne => !hasNextLine()
    assertInstanceOf(QuitCommand.class, tui.nextCommande());
  }

  @Test
  void testLsMauvaisUsageRetourneError() {
    var buffer = new ByteArrayOutputStream();

    // "ls" sans domaine
    DnsTui tui = creerTui("ls\n", buffer);
    assertInstanceOf(ErrorCommand.class, tui.nextCommande());

    // "ls -a" sans domaine
    tui = creerTui("ls -a\n", buffer);
    assertInstanceOf(ErrorCommand.class, tui.nextCommande());

    // "ls -b uvsq.fr" (mauvaise option)
    tui = creerTui("ls -b uvsq.fr\n", buffer);
    assertInstanceOf(ErrorCommand.class, tui.nextCommande());
  }

  @Test
  void testAddMauvaisUsageRetourneError() {
    var buffer = new ByteArrayOutputStream();

    // "add" sans arguments
    DnsTui tui = creerTui("add\n", buffer);
    assertInstanceOf(ErrorCommand.class, tui.nextCommande());

    // "add" avec un seul argument
    tui = creerTui("add 193.51.25.24\n", buffer);
    assertInstanceOf(ErrorCommand.class, tui.nextCommande());

    // "add" avec trop d'arguments
    tui = creerTui("add 193.51.25.24 a.b.c d\n", buffer);
    assertInstanceOf(ErrorCommand.class, tui.nextCommande());
  }

  @Test
  void testTokenInvalideSeulRetourneError() {
    var buffer = new ByteArrayOutputStream();

    // pas de point -> pas FQDN
    assertInstanceOf(ErrorCommand.class, creerTui("truc\n", buffer).nextCommande());

    // label vide (double point) -> invalide
    assertInstanceOf(ErrorCommand.class, creerTui("a..b\n", buffer).nextCommande());

    // caractère interdit -> invalide
    assertInstanceOf(ErrorCommand.class, creerTui("w#w.fr\n", buffer).nextCommande());
  }
}
