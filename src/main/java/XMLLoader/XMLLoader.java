package XMLLoader;

import MoviePortal.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class XMLLoader {
    public static void main(String[] args) {
        Set<User> userSet = new HashSet<User>();
        userSet.add(new User(
                        "Алекс",
                        new ArrayList<Rate>(),
                        "mail@example.com",
                        "asdad"));
        Set<Movie> movieSet = new HashSet<Movie>();
        MoviePortal portal = new MoviePortal("КиноПоиск", userSet, movieSet);
        save(portal);

        MoviePortal unmarshalled = load();
        System.out.println(unmarshalled);
    }

    public static void save(MoviePortal portal) {
        try {
            JAXBContext context = JAXBContext.newInstance(MoviePortal.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(portal, new File("portal.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static MoviePortal load() {
        try {
            JAXBContext context = JAXBContext.newInstance(MoviePortal.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (MoviePortal) unmarshaller.unmarshal(new File("portal.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
