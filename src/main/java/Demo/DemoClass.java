package Demo;

import realisations.RealisationTwo;
import test.TestInterface;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.ServiceLoader;

public class DemoClass {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        findByName("bean2").testMethod();
        findByClass(RealisationTwo.class).testMethod();
    }

    public static TestInterface findByName(String beanName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ArrayList<String> something = readFromFile();
        String className = "";
        label:
        for (String s : something) {
            String bufferClassName = "";
            for (int j = 0; j < s.length(); j++) {
                String buffBeanName = "";
                if (s.charAt(j) == '#') {
                    j++;
                    while (j < s.length()) {
                        buffBeanName += s.charAt(j);
                        j++;
                    }
                    if (buffBeanName.equals(beanName)) {
                        className = bufferClassName;
                        break label;
                    }
                } else {
                    if (s.charAt(j) != ' ') {
                        bufferClassName += s.charAt(j);
                    }
                }
            }
        }
        Class<?> object = Class.forName(className);
        return (TestInterface) object.getDeclaredConstructor().newInstance();
    }

    public static TestInterface findByClass(Class<?> beanClass) {
        ServiceLoader<TestInterface> serviceLoader = ServiceLoader.load(TestInterface.class);
        for (TestInterface t : serviceLoader) {
            if (t.getClass().getName().equals(beanClass.getName())) {
                return t;
            }
        }
        return null;
    }

    public static ArrayList<String> readFromFile() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File("src\\main\\resources\\META-INF\\services\\test.TestInterface");
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                arrayList.add(line);//System.out.println(line);
                // считываем остальные строки в цикле
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
