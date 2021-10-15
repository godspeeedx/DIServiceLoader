package Demo;

import realisations.RealisationTwo;

import java.lang.reflect.InvocationTargetException;

public class DemoClass {
    public static void main(String[] args) {

        DIServiceLoader diServiceLoader = new DIServiceLoader();

        diServiceLoader.load();

        diServiceLoader.findByName("bean1").testMethod();
        diServiceLoader.findByClass(RealisationTwo.class).testMethod();
    }
}
