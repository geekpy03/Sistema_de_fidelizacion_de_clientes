// Administrar la logica de negocio de la actividad de nuestra entidad Cliente
// ClienteDAO ( DAO: Data access Object )
package ejb;

import model.Cliente;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import java.util.List;

@Stateless // No tiene estado, vamos a usar el ejb sin estado, es lo que se acostumbra.
public class ClienteDAO {
    @PersistenceContext(unitName = "sfcPU")
    // The EntityManager.persist() operation is used to insert a new object into the database.
    private EntityManager em;  // Un objeto que nos permite administrar y manipular nuestras entidades y realiza el mapeo correspondiente en la base de datos

//    @Inject
    // Por defecto el contenedor hace que esto sea transaccional: que si existiese un error no se comitee a la base de datos y se revierta la escritura

    /*
           --- Create ---
    */
    public void nuevoCliente(Cliente c){
        //The persist operation can only be called within a transaction
        this.em.persist(c);
    }
    /*
          --- Read ---
   */
    @SuppressWarnings("unchecked")
    public List<Cliente> listarClientes(){
        Query q=this.em.createQuery( "select c from Cliente c");
        List<Cliente> listadoClientes = (List<Cliente>) q.getResultList();
        return  listadoClientes;
    }

    public Cliente obtenerClienteById(Integer id) {
        Cliente c = this.em.find(Cliente.class, id);
        if (c == null) {
            throw new EntityNotFoundException("No se puede encontrar al cliente con el ID " + id);
        }
        return c;
    }

     /*
           --- Update ---
    */
    // Nose si hice lo correcto en actualizar pero es una idea interesante, simple y sencilla.
    public void actualizarClienteById(Integer id, Cliente cliente){
        // Primero vemos si esta en la base de datos para poder actualizar
        Cliente c = this.em.find(Cliente.class, id);
        if (  c == null) {
            throw new EntityNotFoundException("No se puede encontrar al cliente con el ID " + id);
        }else{
            c.merge(cliente);
        }
    }
    /*
           --- Delete ---
    */
    // Por lo visto el delete ya hace que sea transacional y que deje consiste la base de datos y commite los nuevos cambios
    public void borrarClienteById(Integer id){
            Cliente c = this.em.find(Cliente.class, id);
            if (c == null) {
                throw new EntityNotFoundException("No se puede encontrar al cliente con el ID " + id);
            }else{
                this.em.remove(c);
            }
    }
}