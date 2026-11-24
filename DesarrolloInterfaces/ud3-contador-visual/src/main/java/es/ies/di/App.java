package es.ies.di;

import es.ies.di.view.ContadorVisual;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private static void name() {

    }

    @Override
    public void start(Stage stage) throws Exception {

        Label titulo = new Label("Ejemplo de componentes reutilizables");

        ContadorVisual c1 = new ContadorVisual();
        ContadorVisual c2 = new ContadorVisual();
        Label estado = new Label("Primer valor del primer contador: " + c1.getValor());

        c1.valorProperty().addListener(
                (obs, oldVal, newVal) -> estado.setText("Valor del primer contador actualizado: " + newVal));

        // Creamos el contenedor principal (Vertical) con todos los elementos visuales separados por 15px. Quedaría así en modo vertical:
        // Titulo
        // Etiqueta: Primer contador
        // Componente: Primer contador
        // Etiqueta: Segundo contador
        // Componente: Segundo contador
        // Etiqueta: Estado del primer contador
        // Botón para reiniciar
        VBox raiz = new VBox(15, titulo, new Label("Primer contador: "), c1,
                new Label("Segundo contador:"), c2, estado);

        raiz.setAlignment(Pos.CENTER);

        // La escena que contiene todos los elementos visuales que se mostrarán en la ventana (tamaño 400x300)
        Scene escena = new Scene(raiz, 400, 300);

        // Indicamos la escena a mostrar dentro del stage (que es lña ventana)
        stage.setScene(escena);

        stage.setTitle("UD3 - Contador Visual");

        stage.setMinHeight(250);
        stage.setMinWidth(350);

        // Mostramos la ventana en pantalla
        stage.show();

    }

    public static void main(String[] args) {
        // Con el método launch() iniciamos el ciclo de vida de la app en JavaFX
        // Recordad que termina llamando al método start() definido arriba
        launch(args);
    }

}