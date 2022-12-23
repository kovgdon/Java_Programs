package org.example;

import java.io.FileReader;
import java.lang.reflect.*;
import java.util.Properties;

import org.example.AutoInjectable;

public class Injector {
    private Injector() {}
    public static <Type> Type inject(Type obj) {

        try {
            Class cls = obj.getClass();

            Properties injectorProps = new Properties();
            FileReader reader = new FileReader("my_injector.properties");
            injectorProps.load(reader);
            reader.close();

            Field fieldlist[] = cls.getDeclaredFields();
            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                if(fld.isAnnotationPresent(AutoInjectable.class)) {
                    Class fieldclass = Class.forName(injectorProps.getProperty(fld.getType().getName()));
                    fld.setAccessible(true);
                    fld.set(obj, fieldclass.getDeclaredConstructor().newInstance());
                    fld.setAccessible(false);

                }

            }
        }
        catch (Throwable e) {
            System.err.println(e);
        }
        return obj;
    }


}
