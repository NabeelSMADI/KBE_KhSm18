package de.htw.ai.kbe.runmerunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Smadi
 */
public class Main {

    private boolean fileCreated = false;
    private Class className = null;
    private String ouputFile = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();
        String result = main.checkArgs(args);
        if (!result.equals("True")) {
            System.out.println(result);
        }
        if (main.getClassName() != null) {
            main.readWithoutRunMe();
            main.readRunMe();
            main.readRunMeNotInvokebar();
        }
    }

    public String checkArgs(String[] args) {
        Options options = new Options();
        options.addRequiredOption("c", "class", true, "Main Class Name.");
        options.addOption("o", "output", true, "outbutfile.");

        try {
            //Create a parser
            CommandLineParser parser = new DefaultParser();

            //parse the options passed as command line arguments
            CommandLine cmd = parser.parse(options, args, true);

            if (cmd.hasOption("c")) {
                try {
                    className = Class.forName(cmd.getOptionValue("c"));
                } catch (ClassNotFoundException e) {
                    return e.getClass().getSimpleName();
                }
                System.out.println("Input class: " + cmd.getOptionValue("c"));
            }
            if (cmd.hasOption("o")) {
                System.out.println("Output File name: " + cmd.getOptionValue("o"));
                ouputFile = cmd.getOptionValue("o");
            } else {
                System.out.println("Default Output File name: Report.txt");
            }
        } catch (ParseException e) {
            boolean missingO = false;
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-o")) {
                    args[i] = "";
                    missingO = true;
                }
            }
            String massage = e.getMessage();
            if (missingO) {
                String checkMassage = checkArgs(args);
                 if(!checkMassage.equals("True")){
                     massage = checkMassage;
                 }
            }
            return massage;
        }
        return "True";
    }

    public List<Method> readWithoutRunMe() {
        List<Method> methodsWtihoutRunMe = new ArrayList<Method>();
        Class xClass = className;
        Method[] methods = xClass.getDeclaredMethods(); //.getMethods()
        log("Methodennamen ohne @RunMe:");
        for (Method m : methods) {
            if (!m.isAnnotationPresent(RunMe.class)) {
                methodsWtihoutRunMe.add(m);
                log(m.getName());
            }
        }
        return methodsWtihoutRunMe;
    }

    public List<Method> readRunMe() {
        List<Method> methodsWtihRunMe = new ArrayList<Method>();
        Class xClass = className;
        Method[] methods = xClass.getDeclaredMethods(); //.getMethods()
        log("Methodennamen mit @RunMe:");
        for (Method m : methods) {
            if (m.isAnnotationPresent(RunMe.class)) {
                methodsWtihRunMe.add(m);
                log(m.getName());
            }
        }
        return methodsWtihRunMe;
    }

    public List<Method> readRunMeNotInvokebar() {
        List<Method> methodsWtihException = new ArrayList<Method>();
        try {
            Class xClass = className;
            Object xclass = xClass.newInstance();
            Method[] methods = xClass.getDeclaredMethods(); //.getMethods()
            log("Nicht-invokierbare Methoden mit @RunMe:");
            for (Method m : methods) {
                if (m.isAnnotationPresent(RunMe.class)) {
                    try {
                        m.invoke(xclass);
                    } catch (Exception e) {
                        methodsWtihException.add(m);
                        log(m.getName() + " | wegen : " + e.getMessage());
                    }
                }
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return methodsWtihException;
    }

    public boolean log(String message) {
        System.out.println(message);
        try {
            if (ouputFile == null) {
                ouputFile = "Report.txt";
            }
            PrintWriter out = new PrintWriter(new FileWriter(ouputFile, true), true);
            if (!fileCreated) {
                out = new PrintWriter(new FileWriter(ouputFile, false), true);
                fileCreated = true;
            }
            out.write(message + "\n");
            out.close();
            return true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public String getOuputFile() {
        return ouputFile;
    }

    public void setOuputFile(String ouputFile) {
        this.ouputFile = ouputFile;
    }

}
