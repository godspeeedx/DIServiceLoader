package Demo;

import realisations.RealisationTwo;

import java.lang.reflect.InvocationTargetException;

public class DemoClass {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        DIServiceLoader diServiceLoader = new DIServiceLoader();

        diServiceLoader.findByName("bean2").testMethod();
        diServiceLoader.findByClass(RealisationTwo.class).testMethod();
    }
}
