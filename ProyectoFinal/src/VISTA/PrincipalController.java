package VISTA;

import Producto.Producto;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author LUIS ANGEL FLOREZ
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtid;
    @FXML
    private TextField txtnomproduc;
    @FXML
    private TextField txtprecio_u;
    @FXML
    private TextField txtFiltrarId;
    @FXML
    private TextField txtFiltrarNombre;
    @FXML
    private TextField txtFiltrarLote;
    @FXML
    private TextField txtFiltrarVencimiento;
    @FXML
    private TextField txtFiltrarPrecio;
    @FXML
    private DatePicker lot_produc;
    @FXML
    private DatePicker ven_produc;
    @FXML
    private TableView<Producto> tablaproduc;
    @FXML
    private TableColumn colum_id;
    @FXML
    private TableColumn colum_nom;
    @FXML
    private TableColumn colum_felo;
    @FXML
    private TableColumn colum_feven;
    @FXML
    private TableColumn colum_preciou;
    @FXML
    private ComboBox<String> comboBoxOpciones;
    @FXML
    private Button btnVerCarrito;
    @FXML
    private Button btnAgregarCarrito;
    @FXML
    private Button btnMostrarRecibo;

    private ObservableList<Producto> Productos;
    private ObservableList<Producto> filtroProductos;

    private Stack<Producto> stackPrecioMayor = new Stack<>();
    private Stack<Producto> stackPrecioMenor = new Stack<>();
    private Stack<Producto> carrito = new Stack<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Productos = FXCollections.observableArrayList();
        filtroProductos = FXCollections.observableArrayList();

        this.colum_id.setCellValueFactory(new PropertyValueFactory("idproducto"));
        this.colum_nom.setCellValueFactory(new PropertyValueFactory("nomproducto"));
        this.colum_felo.setCellValueFactory(new PropertyValueFactory("fechalote"));
        this.colum_feven.setCellValueFactory(new PropertyValueFactory("fechavence"));
        this.colum_preciou.setCellValueFactory(new PropertyValueFactory("preciou"));

        // Configura el ComboBox con las opciones
        List<String> opciones = new ArrayList<>();
        opciones.add("Mostrar Precio Mayor");
        opciones.add("Mostrar Precio Menor");
        opciones.add("Mostrar Precio Promedio");
        opciones.add("Mostrar Productos Mayor al Promedio");
        opciones.add("Mostrar Productos Menor al Promedio");

        comboBoxOpciones.setItems(FXCollections.observableArrayList(opciones));

        // Establece el manejador de eventos para el cambio de selección en el ComboBox
        comboBoxOpciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Maneja el evento de selección según la opción elegida en el ComboBox
                if (newValue.equals("Mostrar Precio Mayor")) {
                    mostrarPrecioMayor(null);
                } else if (newValue.equals("Mostrar Precio Menor")) {
                    mostrarPrecioMenor(null);
                } else if (newValue.equals("Mostrar Precio Promedio")) {
                    mostrarPrecioPromedio(null);
                } else if (newValue.equals("Mostrar Productos Mayor al Promedio")) {
                    mostrarProductosMayoresAlPromedio(null);
                } else if (newValue.equals("Mostrar Productos Menor al Promedio")) {
                    mostrarProductosMenoresAlPromedio(null);
                }
                // Limpia la selección en el ComboBox después de la ejecución
                comboBoxOpciones.getSelectionModel().clearSelection();
            }
        });
        btnVerCarrito.setOnAction(this::verCarritoDeCompras);
    }

    @FXML
    private void verCarritoDeCompras(ActionEvent event) {
        if (carrito.isEmpty()) {
            mostrarMensaje("Carrito de Compras", "El carrito de compras está vacío.");
        } else {
            StringBuilder mensaje = new StringBuilder("Productos en el Carrito de Compras:\n\n");
            for (Producto producto : carrito) {
                mensaje.append("ID: ").append(producto.getIdproducto()).append("\n")
                        .append("Nombre: ").append(producto.getNomproducto()).append("\n")
                        .append("Precio Unitario: ").append(producto.getPreciou()).append("\n\n");
            }
            mostrarMensaje("Carrito de Compras", mensaje.toString());
        }
    }

    @FXML
    private void agregarAlCarrito(ActionEvent event) {
        // Verificar si hay un producto seleccionado en la tabla
        Producto productoSeleccionado = tablaproduc.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {
            mostrarMensaje("Error", "Selecciona un producto de la tabla antes de agregar al carrito.");
            return;
        }

        // Agregar el producto seleccionado al carrito
        carrito.push(productoSeleccionado);

        mostrarMensaje("Carrito de Compras", "Producto agregado al carrito:\n\n"
                + "ID: " + productoSeleccionado.getIdproducto() + "\n"
                + "Nombre: " + productoSeleccionado.getNomproducto() + "\n"
                + "Precio Unitario: " + productoSeleccionado.getPreciou());
    }

    @FXML
    private void mostrarRecibo(ActionEvent event) {
        if (carrito.isEmpty()) {
            mostrarMensaje("Recibo de Compras", "El carrito de compras está vacío. Agrega productos antes de generar un recibo.");
            return;
        }

        StringBuilder recibo = new StringBuilder();
        recibo.append("Recibo de Compras:\n\n");

        float total = 0.0f;

        for (Producto producto : carrito) {
            recibo.append("ID: ").append(producto.getIdproducto()).append("\n")
                    .append("Nombre: ").append(producto.getNomproducto()).append("\n")
                    .append("Precio Unitario: ").append(producto.getPreciou()).append("\n\n");

            total += producto.getPreciou();
        }

        recibo.append("Total: ").append(total);

        mostrarMensaje("Recibo de Compras", recibo.toString());
    }

    @FXML
    private void ingresar(ActionEvent event) {
        try {
            String idproducto = this.txtid.getText();
            String nomproducto = this.txtnomproduc.getText();
            LocalDate fechalote = lot_produc.getValue();
            LocalDate fechavence = ven_produc.getValue();
            Float preciou = Float.parseFloat(this.txtprecio_u.getText());

            // Comprobar si el ID ya existe en la lista
            if (productoConIdRepetido(idproducto)) {
                mostrarMensaje("Error", "El producto con el ID " + idproducto + " ya existe.");
                return;
            }

            Producto p = new Producto(idproducto, nomproducto, fechalote, fechavence, preciou);

            this.Productos.add(p);
            this.tablaproduc.setItems(Productos);

            // Actualizar pilas de precio mayor y menor
            if (stackPrecioMayor.isEmpty() || preciou > stackPrecioMayor.peek().getPreciou()) {
                stackPrecioMayor.clear();
                stackPrecioMayor.push(p);
            } else if (preciou == stackPrecioMayor.peek().getPreciou()) {
                stackPrecioMayor.push(p);
            }

            if (stackPrecioMenor.isEmpty() || preciou < stackPrecioMenor.peek().getPreciou()) {
                stackPrecioMenor.clear();
                stackPrecioMenor.push(p);
            } else if (preciou == stackPrecioMenor.peek().getPreciou()) {
                stackPrecioMenor.push(p);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato incorrecto");
            alert.showAndWait();
        }
    }
    
    @FXML

    private void eliminar(ActionEvent event) {
    
        // Elimina los datos de la tabla
        Productos.clear();
        // Elimina los elementos del carrito
        carrito.clear();
        // Muestra un mensaje informando que los datos han sido eliminados
        mostrarMensaje("Eliminación", "Los datos han sido eliminados correctamente.");
    }

    private boolean productoConIdRepetido(String id) {
        for (Producto producto : Productos) {
            if (producto.getIdproducto().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void mostrarPrecioMayor(ActionEvent event) {
        Producto precioMayor = getPrecioMayor();
        if (precioMayor != null) {
            mostrarProducto(precioMayor);
        }
    }

    @FXML
    private void mostrarPrecioMenor(ActionEvent event) {
        Producto precioMenor = getPrecioMenor();
        if (precioMenor != null) {
            mostrarProducto(precioMenor);
        }
    }

    private void mostrarProducto(Producto producto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Producto");
        alert.setContentText("ID: " + producto.getIdproducto() + "\n"
                + "Nombre: " + producto.getNomproducto() + "\n"
                + "Precio Unitario: " + producto.getPreciou());
        alert.showAndWait();
    }

    public Producto getPrecioMayor() {
        return stackPrecioMayor.isEmpty() ? null : stackPrecioMayor.peek();
    }

    public Producto getPrecioMenor() {
        return stackPrecioMenor.isEmpty() ? null : stackPrecioMenor.peek();
    }

    @FXML
    private void filtrarId(Event event) {
        String filtroId = this.txtFiltrarId.getText();

        if (filtroId.isEmpty()) {
            this.tablaproduc.setItems(Productos);
        } else {
            this.filtroProductos.clear();

            for (Producto p : this.Productos) {
                if (p.getIdproducto().toLowerCase().contains(filtroId.toLowerCase())) {
                    this.filtroProductos.add(p);
                }
            }
            this.tablaproduc.setItems(filtroProductos);
        }
    }

    @FXML
    private void filtrarNombre(Event event) {
        String filtroNombre = this.txtFiltrarNombre.getText();

        if (filtroNombre.isEmpty()) {
            this.tablaproduc.setItems(Productos);
        } else {
            this.filtroProductos.clear();

            for (Producto p : this.Productos) {
                if (p.getNomproducto().toLowerCase().contains(filtroNombre.toLowerCase())) {
                    this.filtroProductos.add(p);
                }
            }
            this.tablaproduc.setItems(filtroProductos);
        }
    }

    @FXML
    private void filtrarLote(Event event) {
        String filtroLote = this.txtFiltrarLote.getText();

        if (filtroLote.isEmpty()) {
            this.tablaproduc.setItems(Productos);
        } else {
            this.filtroProductos.clear();

            for (Producto p : this.Productos) {
                if (p.getFechalote().toString().toLowerCase().contains(filtroLote.toLowerCase())) {
                    this.filtroProductos.add(p);
                }
            }
            this.tablaproduc.setItems(filtroProductos);
        }
    }

    @FXML
    private void filtrarVencimiento(Event event) {
        String filtroVencimiento = this.txtFiltrarVencimiento.getText();

        if (filtroVencimiento.isEmpty()) {
            this.tablaproduc.setItems(Productos);
        } else {
            this.filtroProductos.clear();

            for (Producto p : this.Productos) {
                if (p.getFechavence().toString().toLowerCase().contains(filtroVencimiento.toLowerCase())) {
                    this.filtroProductos.add(p);
                }
            }
            this.tablaproduc.setItems(filtroProductos);
        }
    }

    @FXML
    private void filtrarPrecio(Event event) {
        String filtroPrecio = this.txtFiltrarPrecio.getText();

        if (filtroPrecio.isEmpty()) {
            this.tablaproduc.setItems(Productos);
        } else {
            this.filtroProductos.clear();

            for (Producto p : this.Productos) {
                if (p.getPreciou().toString().toLowerCase().contains(filtroPrecio.toLowerCase())) {
                    this.filtroProductos.add(p);
                }
            }
            this.tablaproduc.setItems(filtroProductos);
        }
    }

    @FXML
    private void mostrarPrecioPromedio(ActionEvent event) {
        float precioPromedio = calcularPrecioPromedio();
        mostrarMensaje("Precio Promedio", "El precio promedio de los productos es: " + precioPromedio);
    }

    private float calcularPrecioPromedio() {
        if (Productos.isEmpty()) {
            return 0.0f; // Puedes manejar este caso como desees
        }

        float sumaPrecios = 0.0f;
        for (Producto producto : Productos) {
            sumaPrecios += producto.getPreciou();
        }

        return sumaPrecios / Productos.size();
    }

    @FXML
    private void mostrarProductosMayoresAlPromedio(ActionEvent event) {
        float precioPromedio = calcularPrecioPromedio();
        List<Producto> productosMayoresAlPromedio = filtrarProductosPorPrecio(precioPromedio, true);
        mostrarProductos("Productos con precio mayor al promedio", productosMayoresAlPromedio);
    }

    @FXML
    private void mostrarProductosMenoresAlPromedio(ActionEvent event) {
        float precioPromedio = calcularPrecioPromedio();
        List<Producto> productosMenoresAlPromedio = filtrarProductosPorPrecio(precioPromedio, false);
        mostrarProductos("Productos con precio menor al promedio", productosMenoresAlPromedio);
    }

    private List<Producto> filtrarProductosPorPrecio(float precioPromedio, boolean mayorAlPromedio) {
        List<Producto> productosFiltrados = new ArrayList<>();
        for (Producto producto : Productos) {
            if (mayorAlPromedio && producto.getPreciou() > precioPromedio) {
                productosFiltrados.add(producto);
            } else if (!mayorAlPromedio && producto.getPreciou() < precioPromedio) {
                productosFiltrados.add(producto);
            }
        }
        return productosFiltrados;
    }

    private void mostrarProductos(String titulo, List<Producto> productos) {
        if (productos.isEmpty()) {
            mostrarMensaje(titulo, "No hay productos que cumplan el criterio.");
        } else {
            StringBuilder mensaje = new StringBuilder();
            for (Producto producto : productos) {
                mensaje.append("ID: ").append(producto.getIdproducto()).append("\n")
                        .append("Nombre: ").append(producto.getNomproducto()).append("\n")
                        .append("Precio Unitario: ").append(producto.getPreciou()).append("\n\n");
            }
            mostrarMensaje(titulo, mensaje.toString());
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
