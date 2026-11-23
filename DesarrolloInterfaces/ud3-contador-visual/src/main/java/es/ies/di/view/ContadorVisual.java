package es.ies.di.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ContadorVisual extends HBox {
  private final Button botonMenos = new Button(" - ");
  private final Label etiquetaValor = new Label();
  private final Button botonMas = new Button(" + ");
  // Un int guarda un valor, IntegerProperty guarda un valor y además puede avisar de cambios y enlazarlas con otras propiedades (bindings y listeners)
  private final IntegerProperty valor = new SimpleIntegerProperty(0);

  public ContadorVisual() {

    super(10);

    etiquetaValor.textProperty().bind(valor.asString());
    etiquetaValor.setTextFill(Color.RED);

    botonMas.setOnAction(e -> incrementar());
    botonMenos.setOnAction(e -> decrementar());

    getChildren().addAll(botonMenos, etiquetaValor, botonMas);
    setAlignment(Pos.CENTER);

  }

  public int getValor() {
    return valor.get();
  }

  private void setValor(int nuevoValor) {
    valor.set(nuevoValor);
  }

  private void incrementar() {
    setValor(getValor() + 1);
  }

  private void decrementar() {
    setValor(getValor() - 1);
  }

  public IntegerProperty valorProperty() {
    return this.valor;
  }

}
