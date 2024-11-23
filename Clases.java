import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Main {

    // Clase Cafe
    static class Cafe {
        private int basePrice = 15;

        public int calcularPrecio() {
            return basePrice;
        }
    }

    // Clase Vaso
    static class Vaso {
        private String tamano;
        private int precio;

        public Vaso(String tipo) {
            this.tamano = tipo;
            this.precio = obtenerPrecioVaso(tipo);
        }

        private int obtenerPrecioVaso(String tipo) {
            switch (tipo.toLowerCase()) {
                case "chico":
                    return 5;
                case "mediano":
                    return 7;
                case "grande":
                    return 10;
                default:
                    throw new IllegalArgumentException("Tamaño de vaso no válido.");
            }
        }

        public int calcularPrecio() {
            return precio;
        }

        public String getTamano() {
            return tamano;
        }
    }

    // Clase Ingrediente
    static class Ingrediente {
        private String nombre;
        private int precio;

        public Ingrediente(String nombre) {
            this.nombre = nombre;
            this.precio = obtenerPrecioIngrediente(nombre);
        }

        private int obtenerPrecioIngrediente(String nombre) {
            Map<String, Integer> precios = Map.of(
                "chocolate", 7,
                "caramelo", 12,
                "crema", 10,
                "rocklets", 15,
                "azúcar", 1
            );
            if (precios.containsKey(nombre.toLowerCase())) {
                return precios.get(nombre.toLowerCase());
            } else {
                throw new IllegalArgumentException("Ingrediente no válido.");
            }
        }

        public int calcularPrecio() {
            return precio;
        }

        public String getNombre() {
            return nombre;
        }
    }

    // Clase CafePersonalizado
    static class CafePersonalizado {
        private Cafe cafeBase;
        private Vaso vaso;
        private List<Ingrediente> ingredientes;

        public CafePersonalizado(Vaso vaso, List<Ingrediente> ingredientes) {
            this.cafeBase = new Cafe();
            this.vaso = vaso;
            this.ingredientes = ingredientes;
        }

        public int calcularPrecio() {
            int precioTotal = cafeBase.calcularPrecio() + vaso.calcularPrecio();
            for (Ingrediente ingrediente : ingredientes) {
                precioTotal += ingrediente.calcularPrecio();
            }
            return precioTotal;
        }

        public void imprimirResumen() {
            System.out.println("Resumen del pedido:");
            System.out.println("Base del café: $" + cafeBase.calcularPrecio());
            System.out.println("Vaso " + vaso.getTamano() + ": $" + vaso.calcularPrecio());
            for (Ingrediente ingrediente : ingredientes) {
                System.out.println(ingrediente.getNombre().substring(0, 1).toUpperCase() +
                                   ingrediente.getNombre().substring(1) + ": $" +
                                   ingrediente.calcularPrecio());
            }
            System.out.println("Total: $" + calcularPrecio());
        }
    }

    // Método principal
    public static void main(String[] args) {
        try {
            Vaso vaso = new Vaso("mediano");
            List<Ingrediente> ingredientes = new ArrayList<>();
            ingredientes.add(new Ingrediente("chocolate"));
            ingredientes.add(new Ingrediente("crema"));

            CafePersonalizado cafe = new CafePersonalizado(vaso, ingredientes);
            cafe.imprimirResumen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Clase de pruebas
    static class CafeTests {
        @Test
        void testPrecioCafeBase() {
            Cafe cafe = new Cafe();
            assertEquals(15, cafe.calcularPrecio());
        }

        @Test
        void testPrecioVasoChico() {
            Vaso vaso = new Vaso("chico");
            assertEquals(5, vaso.calcularPrecio());
        }

        @Test
        void testPrecioVasoMediano() {
            Vaso vaso = new Vaso("mediano");
            assertEquals(7, vaso.calcularPrecio());
        }

        @Test
        void testPrecioIngredienteChocolate() {
            Ingrediente ingrediente = new Ingrediente("chocolate");
            assertEquals(7, ingrediente.calcularPrecio());
        }

        @Test
        void testPrecioCafePersonalizado() {
            Vaso vaso = new Vaso("grande");
            List<Ingrediente> ingredientes = new ArrayList<>();
            ingredientes.add(new Ingrediente("chocolate"));
            ingredientes.add(new Ingrediente("azúcar"));
            CafePersonalizado cafePersonalizado = new CafePersonalizado(vaso, ingredientes);

            assertEquals(15 + 10 + 7 + 1, cafePersonalizado.calcularPrecio());
        }
    }
}
