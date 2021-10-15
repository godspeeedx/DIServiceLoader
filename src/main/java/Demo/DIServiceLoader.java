package Demo;

import test.TestInterface;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DIServiceLoader {

    private HashMap<String, TestInterface> hash = new HashMap();

    public TestInterface findByClass(Class<?> beanClass) {
        ServiceLoader<TestInterface> serviceLoader = ServiceLoader.load(TestInterface.class);
        for (TestInterface t : serviceLoader) {
            if (t.getClass().getName().equals(beanClass.getName())) {
                return t;
            }
        }
        return null;
    }

    public TestInterface findByName(String beanName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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

    public Iterator<Map<String, TestInterface>> iterator() {
        return null;
    }

    public ArrayList<String> readFromFile() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(Objects.requireNonNull(DIServiceLoader.class.getResource("/META-INF/services/test.TestInterface")).getFile());
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                arrayList.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

}