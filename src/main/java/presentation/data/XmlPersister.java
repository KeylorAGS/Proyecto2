package presentation.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Clase responsable de la persistencia de datos en formato XML.
 *
 * Utiliza JAXB (Jakarta XML Binding) para serializar y deserializar objetos
 * de la clase {@link Data} hacia/desde un archivo XML.
 *
 * Se implementa como un Singleton mediante el método {@link #instance()},
 * de manera que toda la aplicación comparta una única instancia de {@code XmlPersister}.
 */
public class XmlPersister {

    /** Ruta del archivo XML donde se almacenarán los datos. */
    private String path;

    /** Instancia única de {@code XmlPersister} (patrón Singleton). */
    private static XmlPersister theInstance;

    /**
     * Devuelve la instancia única de {@code XmlPersister}.
     * Si aún no existe, la crea con el archivo por defecto "Hospital.xml".
     *
     * @return Instancia única de {@code XmlPersister}.
     */
    public static XmlPersister instance() {
        if (theInstance == null) {
            theInstance = new XmlPersister("Hospital.xml");
        }
        return theInstance;
    }

    /**
     * Constructor que recibe la ruta del archivo XML donde se guardarán los datos.
     *
     * @param p Ruta del archivo XML.
     */
    public XmlPersister(String p) {
        path = p;
    }

    /**
     * Carga los datos desde el archivo XML y los convierte en un objeto {@link Data}.
     *
     * @return Objeto {@link Data} con la información deserializada del archivo.
     * @throws Exception si ocurre un error durante la lectura o deserialización.
     */
    public Data load() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        FileInputStream is = new FileInputStream(path);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Data result = (Data) unmarshaller.unmarshal(is);
        is.close();
        return result;
    }

    /**
     * Almacena los datos en un archivo XML serializando el objeto {@link Data}.
     *
     * @param d Objeto {@link Data} que será persistido en el archivo XML.
     * @throws Exception si ocurre un error durante la escritura o serialización.
     */
    public void store(Data d) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        FileOutputStream os = new FileOutputStream(path);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // <-- salida formateada
        marshaller.marshal(d, os);
        os.flush();
        os.close();
    }
}
