package net.hybridcore.crowley.util;

import net.hybridcore.crowley.Crowley;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class DatastoreUtil {
    public static final ThreadLocal session = new ThreadLocal();

    public static Session currentSession() throws HibernateException {
        Session s = (Session) session.get();

        if (s == null) {
            s = Crowley.getDatastore().openSession();
            session.set(s);
        }

        return s;
    }

    public static void closeSession() throws HibernateException {
        Session s = (Session) session.get();
        session.set(null);

        if (s != null)
            s.close();
    }
}
