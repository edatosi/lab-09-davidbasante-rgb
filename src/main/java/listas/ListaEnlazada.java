package listas;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Lista simplemente enlazada genérica.
 *
 * INSTRUCCIONES PARA EL ESTUDIANTE
 * --------------------------------
 * 1. Cada método marcado con "TODO" lanza UnsupportedOperationException.
 *    Reemplace esa línea por su implementación.
 * 2. NO modifique las pruebas (src/test). 
 * 3. Resuelva en orden (Etapa 1, 2, 3...): cada etapa usa las anteriores.
 * 4. Solo se permite usar nodos propios (clase Nodo). No use
 *    java.util.LinkedList ni ArrayList dentro de esta clase.
 */

public class ListaEnlazada<T> {

    /** Primer nodo de la lista (null cuando la lista está vacía). */
    private Nodo<T> cabeza;

    /** Cantidad de elementos. Debe mantenerse actualizada en cada operación. */
    private int cantidad;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    // =====================================================================
    // ETAPA 1. Estado de la lista
    // =====================================================================

    /** @return true si la lista no tiene elementos. */
    public boolean estaVacia() {
        return this.cantidad == 0;
    }

    /** @return número de elementos de la lista. */
    public int tamanio() {
        return this.cantidad;
    }

    // =====================================================================
    // ETAPA 2. Agregar en los extremos
    // =====================================================================

// =====================================================================
    // ETAPA 2. Agregar en los extremos
    // =====================================================================

    /** Inserta el dato como nuevo primer elemento. */
    public void agregarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        
        // PROBLEMA ENCONTRADO: Usar "nuevoNodo.siguiente = this.cabeza" da error porque "siguiente" es privado en Nodo.java.
        // SOLUCIÓN: Se utiliza el método "setSiguiente()" para modificar el enlace.
        nuevoNodo.setSiguiente(this.cabeza); 
        
        this.cabeza = nuevoNodo;
        this.cantidad++;
    }

    /** Inserta el dato como nuevo último elemento. */
    public void agregarAlFinal(T dato) {
        if (estaVacia()) {
            agregarAlInicio(dato);
        } else {
            Nodo<T> nuevoNodo = new Nodo<>(dato);
            Nodo<T> actual = this.cabeza;
            
            // PROBLEMA ENCONTRADO: La condición "actual.siguiente != null" y la asignación "actual = actual.siguiente" fallan por la visibilidad privada de los atributos.
            // SOLUCIÓN: Se reemplaza la lectura directa por "getSiguiente()".
            while (actual.getSiguiente() != null) { 
                actual = actual.getSiguiente();     
            }
            
            // SOLUCIÓN: Se usa "setSiguiente()" para enlazar el último nodo con el nuevo.
            actual.setSiguiente(nuevoNodo);         
            this.cantidad++;
        }
    }
    // =====================================================================
    // ETAPA 3. Consultar elementos
    // =====================================================================

/** @throws NoSuchElementException si la lista está vacía */
    public T obtenerPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        
        // PROBLEMA ENCONTRADO: "return this.cabeza.dato" genera error de acceso al atributo privado 'dato'.
        // SOLUCIÓN: Se invoca "getDato()" para obtener el valor de forma segura.
        return this.cabeza.getDato();
    }

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        
        Nodo<T> actual = this.cabeza;
        
        // SOLUCIÓN: Iteramos y consultamos usando los getters provistos por la clase Nodo para respetar su encapsulamiento.
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        
        return actual.getDato();
    }

    /**
     * @param indice posición (0 = primer elemento)
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= this.cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + indice);
        }
        
        Nodo<T> actual = this.cabeza;
        
        // SOLUCIÓN: Recorremos la lista usando "getSiguiente()" para avanzar.
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        
        // SOLUCIÓN: Retornamos el valor usando "getDato()".
        return actual.getDato();
    }

    // =====================================================================
    // ETAPA 4. Búsqueda
    // =====================================================================

    /** @return true si algún elemento es igual (equals) al dato. Admite null. */
    public boolean contiene(T dato) {
        // SOLUCIÓN: Reutilizamos el método indiceDe. Si devuelve algo distinto a -1, es porque existe.
        return indiceDe(dato) != -1;
    }

    /** @return posición de la primera aparición del dato, o -1 si no está. */
    public int indiceDe(T dato) {
        int indice = 0;
        Nodo<T> actual = this.cabeza;
        
        while (actual != null) {
            // SOLUCIÓN: Usamos Objects.equals para prevenir NullPointerException si el dato o el valor del nodo son null.
            // Accedemos al valor mediante getDato().
            if (Objects.equals(dato, actual.getDato())) {
                return indice;
            }
            actual = actual.getSiguiente();
            indice++;
        }
        
        return -1; // No se encontró
    }

    // =====================================================================
    // ETAPA 5. Insertar y reemplazar en cualquier posición
    // =====================================================================

    /**
     * Inserta el dato en la posición indicada, desplazando los siguientes.
     * Se permite indice == tamanio() (equivale a agregar al final).
     *
     * @throws IndexOutOfBoundsException si indice < 0 o indice > tamanio()
     */
    public void insertar(int indice, T dato) {
        if (indice < 0 || indice > this.cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + indice);
        }
        
        if (indice == 0) {
            agregarAlInicio(dato);
        } else if (indice == this.cantidad) {
            agregarAlFinal(dato);
        } else {
            Nodo<T> nuevoNodo = new Nodo<>(dato);
            Nodo<T> actual = this.cabeza;
            
            // Recorremos hasta el nodo ANTERIOR a la posición de inserción
            for (int i = 0; i < indice - 1; i++) {
                actual = actual.getSiguiente();
            }
            
            // SOLUCIÓN: Enlazamos usando los setters y getters correspondientes
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
            this.cantidad++;
        }
    }

    /**
     * Reemplaza el dato de la posición indicada.
     *
     * @return el dato que había antes
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T reemplazar(int indice, T dato) {
        if (indice < 0 || indice >= this.cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + indice);
        }
        
        Nodo<T> actual = this.cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        
        // SOLUCIÓN: Guardamos el dato anterior usando getDato() y lo actualizamos con setDato()
        T datoAnterior = actual.getDato();
        actual.setDato(dato);
        
        return datoAnterior;
    }

    // =====================================================================
    // ETAPA 6. Eliminar
    // =====================================================================

    /**
     * Elimina y retorna el primer elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        
        T datoEliminado = this.cabeza.getDato();
        // SOLUCIÓN: Movemos la cabeza al siguiente nodo
        this.cabeza = this.cabeza.getSiguiente();
        this.cantidad--;
        
        return datoEliminado;
    }

    /**
     * Elimina y retorna el último elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        
        // Caso: la lista tiene un solo elemento
        if (this.cabeza.getSiguiente() == null) {
            return eliminarPrimero();
        }
        
        Nodo<T> actual = this.cabeza;
        // SOLUCIÓN: Recorremos hasta el PENÚLTIMO nodo
        while (actual.getSiguiente().getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        
        T datoEliminado = actual.getSiguiente().getDato();
        // Rompemos el enlace para que el recolector de basura lo limpie
        actual.setSiguiente(null);
        this.cantidad--;
        
        return datoEliminado;
    }

    /**
     * Elimina y retorna el elemento de la posición indicada.
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T eliminar(int indice) {
        if (indice < 0 || indice >= this.cantidad) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + indice);
        }
        
        if (indice == 0) {
            return eliminarPrimero();
        }
        
        Nodo<T> actual = this.cabeza;
        // Recorremos hasta el nodo ANTERIOR al que queremos eliminar
        for (int i = 0; i < indice - 1; i++) {
            actual = actual.getSiguiente();
        }
        
        Nodo<T> nodoAEliminar = actual.getSiguiente();
        T datoEliminado = nodoAEliminar.getDato();
        
        // SOLUCIÓN: Saltamos el nodo a eliminar enlazando el actual con el siguiente del eliminado
        actual.setSiguiente(nodoAEliminar.getSiguiente());
        this.cantidad--;
        
        return datoEliminado;
    }

    /**
     * Elimina la primera aparición del dato.
     * @return true si se eliminó algo, false si el dato no estaba
     */
    public boolean eliminarValor(T dato) {
        int indice = indiceDe(dato);
        if (indice == -1) {
            return false;
        }
        
        eliminar(indice);
        return true;
    }

    /** Deja la lista vacía. */
    public void vaciar() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    // =====================================================================
    // ETAPA 7. Representación en texto
    // =====================================================================

    /**
     * Formato: "[1, 2, 3]". Lista vacía: "[]".
     * Un elemento null se imprime como "null".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> actual = this.cabeza;
        
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) {
                sb.append(", ");
            }
            actual = actual.getSiguiente();
        }
        
        sb.append("]");
        return sb.toString();
    }

    // =====================================================================
    // ETAPA 8. Retos
    // =====================================================================

    /**
     * Invierte la lista EN SITIO: no cree nodos nuevos, solo cambie
     * las referencias 'siguiente' de los nodos existentes.
     */
    public void invertir() {
        Nodo<T> anterior = null;
        Nodo<T> actual = this.cabeza;
        Nodo<T> siguiente = null;
        
        // SOLUCIÓN: Inversión clásica de punteros usando setters y getters
        while (actual != null) {
            siguiente = actual.getSiguiente(); // Guardamos el resto de la lista
            actual.setSiguiente(anterior);     // Invertimos el enlace actual
            anterior = actual;                 // Avanzamos 'anterior'
            actual = siguiente;                // Avanzamos 'actual'
        }
        
        this.cabeza = anterior; // El último nodo procesado es la nueva cabeza
    }

    /**
     * Retorna el elemento del medio. Si el tamaño es par, el segundo de los
     * dos centrales (ej.: [1,2,3,4] -> 3). Pista: dos punteros, uno avanza
     * de a 1 y otro de a 2.
     *
     * @throws NoSuchElementException si la lista está vacía
     */
    public T obtenerMedio() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        
        // SOLUCIÓN: Técnica de la liebre y la tortuga (slow/fast pointers)
        Nodo<T> lento = this.cabeza;
        Nodo<T> rapido = this.cabeza;
        
        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();                        // Avanza de a 1
            rapido = rapido.getSiguiente().getSiguiente();       // Avanza de a 2
        }
        
        return lento.getDato();
    }

    /**
     * Elimina los elementos repetidos dejando solo la primera aparición
     * de cada uno, conservando el orden. Ej.: [1,2,1,3,2] -> [1,2,3]
     */
    public void eliminarDuplicados() {
        Nodo<T> actual = this.cabeza;
        
        // SOLUCIÓN: Doble bucle para comparar sin usar estructuras adicionales
        while (actual != null) {
            Nodo<T> corredor = actual;
            
            while (corredor.getSiguiente() != null) {
                if (Objects.equals(actual.getDato(), corredor.getSiguiente().getDato())) {
                    // Si encontramos un duplicado, lo saltamos
                    corredor.setSiguiente(corredor.getSiguiente().getSiguiente());
                    this.cantidad--;
                } else {
                    corredor = corredor.getSiguiente();
                }
            }
            actual = actual.getSiguiente();
        }
    }
}