package Demo;

import test.TestInterface;

import java.io.*;
import java.util.*;

public class DIServiceLoader {

    private HashMap<String, TestInterface> hash = new HashMap<String, TestInterface>();
    private ServiceLoader<TestInterface> serviceLoader = ServiceLoader.load(TestInterface.class);

    public TestInterface findByClass(Class<?> beanClass) {
        ServiceLoader<TestInterface> serviceLoader = ServiceLoader.load(TestInterface.class);
        for (TestInterface t : serviceLoader) {
            if (t.getClass().getName().equals(beanClass.getName())) {
                return t;
            }
        }
        return null;
    }

    public TestInterface findByName(String beanName) {
        return hash.get(beanName);
    }

    public Iterator<Map<String, TestInterface>> iterator() {
        return (Iterator) hash.entrySet().iterator();
    }

    public void load() {
        try {
            File file = new File(Objects.requireNonNull(DIServiceLoader.class.getResource("/META-INF/services/test.TestInterface")).getFile());
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                StringBuilder bufferClassName = new StringBuilder();
                StringBuilder buffBeanName = new StringBuilder();
                for (int j = 0; j < line.length(); j++) {

                    if (line.charAt(j) == '#') {
                        j++;
                        while (j < line.length()) {
                            buffBeanName.append(line.charAt(j));
                            j++;
                        }
                        TestInterface testInterface = null;
                        for (TestInterface t : serviceLoader) {
                            if (t.getClass().getName().equals(bufferClassName.toString())) {
                                testInterface = t;
                                break;
                            }
                        }
                        hash.put(buffBeanName.toString(), testInterface);
                    } else {
                        if (line.charAt(j) != ' ') {
                            bufferClassName.append(line.charAt(j));
                        }
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}