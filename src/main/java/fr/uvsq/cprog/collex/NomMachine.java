package fr.uvsq.cprog.collex;

public class NomMachine {

  private final String nom;

  public NomMachine(String nom) {
    this.nom = nom;
  }

  @Override
  public String toString() {
    return this.nom;
  }
}
