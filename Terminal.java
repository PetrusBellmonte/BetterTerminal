package edu.kit.informatik;

import java.io.*;

public final class Terminal {

    private static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));
    private static Terminal singleton;

    public static final String MAIN_PREFIX="# ";
    public static final String MESSAGE_PREFIX ="$ ";
    public static final String COMMAND_PREFIX="> ";
    public static final String COMMENT_PREFIX="//";

    private BufferedReader FIN;
    private int linenumber = 0;
    private Line commandbuffer;
    private Line argsbuffer;
    private Thread actual;

    public static Terminal getInstance() {
        if (singleton == null)
            singleton = new Terminal();
        return singleton;
    }

    private Terminal() {
        try {
            FIN = new BufferedReader(new FileReader(
                    new File("C:\\Fill\\in\\the\\path\\to\\your\\test.file"))); //TODO Fill in the absolute path to your test-file
        } catch (FileNotFoundException e) {
            System.out.println("Please add File");
            e.printStackTrace();
        }
    }

    static class Runab implements Runnable{
        String t;
        Runab(String a){
            this.t =a;
        }
        @Override
        public void run() {
            YourMain.main(t.split(" ")); //TODO Fill in the name of your Main-Class
        }
    }

    public static void printLine(Object o) {
        if (singleton == null) {
            System.out.println(o);
            return;
        }
        getInstance().printLineAndCompare(o);
    }

    public static String readLine() {
        if (singleton == null)
            try {
                return IN.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return "Terminal-Error";
            }
        return getInstance().readLineAndCompare();
    }

    public static void printError(final String message) {
        Terminal.printLine("Error, " + message);
    }

    public static void printLine(final char[] charArray) {
        printLine(new String(charArray));
    }

    public static void main(String[] args) {
        do {
            Line tmp;
            boolean gg = true;
            do {
                if(getInstance().argsbuffer==null)
                    tmp = getInstance().getNext();
                else {
                    tmp = getInstance().argsbuffer;
                    getInstance().argsbuffer = null;
                }
                if (tmp.getLine() == -1)
                    return;
                if (tmp.getText().startsWith("> ")) {
                    System.out.println("Programm finished to early. Line " + tmp.getLine() + " could not be called");
                    gg = false;
                } else if ((!tmp.getText().startsWith(MAIN_PREFIX)) & gg)
                    getInstance().issueError(tmp, "<nothing>");
            } while (!tmp.getText().startsWith(MAIN_PREFIX));
            getInstance().actual = new Thread(new Runab(tmp.getText().substring(2)));
            getInstance().actual.start();
            try {
                getInstance().actual.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //BoardControlMain.main(tmp.getText().substring(2).split(" ")); //TODO
        } while (singleton != null);
    }

    private String readLineAndCompare() {
        if (commandbuffer != null) {
            return commandbuffer.getText();
        }
        if (argsbuffer != null) {
            System.out.println("Error in line " + linenumber + ": Program should already be terminated!");
            actual.stop();
            return "Game should be terminated";
        }
        Line tmp;
        do {
            tmp = getNext();
            if (tmp.getLine() == -1) {
                try {
                    return IN.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            }
            if (tmp.getText().startsWith("# ")) {
                argsbuffer = tmp;
                System.out.println("Error in line " + linenumber + ": Program should already be terminated!");
                actual.stop();
                return readLineAndCompare();
            }
            if (!tmp.getText().startsWith("> "))
                issueError(new Line(linenumber,"<nothing>"),tmp.getText());
        } while (!tmp.getText().startsWith("> "));
        return tmp.getText().substring(2);
    }

    private Line getNext() {
        String tmp;
        do {
            try {
                linenumber++;
                tmp = FIN.readLine();
            } catch (IOException e) {
                tmp = null;
            }
            if (tmp == null) {
                singleton = null;
                return new Line(-1, "");
            }
            if (tmp.startsWith(MESSAGE_PREFIX)) {
                System.out.println(tmp.substring(2));
            }
        } while (tmp.equals("") | tmp.startsWith(COMMENT_PREFIX) | tmp.startsWith(MESSAGE_PREFIX));
        return new Line(linenumber, tmp);
    }

    private void printLineAndCompare(Object o) {
        if (o.toString().contains("\n")) {
            for (String s : o.toString().split("\n"))
                printLineAndCompare(s);
            return;
        }
        if (commandbuffer != null) {
            issueError(new Line(linenumber, "<nothing>"), o.toString());
            return;
        }
        if (argsbuffer != null) {
            issueError(new Line(linenumber, "<nothing>"), o.toString());
            return;
        }
        Line tmp = getNext();
        if (tmp.getLine() == -1) {
            System.out.println(o.toString());
        }
        if (tmp.getText().startsWith(COMMAND_PREFIX)) {
            commandbuffer = tmp;
            issueError(new Line(linenumber, "<nothing>"), o.toString());
        } else if (tmp.getText().startsWith("# ")) {
            argsbuffer = tmp;
            issueError(new Line(linenumber, "<nothing>"), o.toString());
        } else {
            if (!o.toString().matches(tmp.getText()))
                issueError(tmp, o.toString());
        }
    }

    private void issueError(Line expected, String error) {
        System.out.println("Error in line " + expected.getLine() + ": Expected: \"" + expected.getText() + "\""
                + "\nYour Output: \"" + error + "\"\n");
    }

    private class Line {
        private int line;
        private String text;

        Line(int line, String text) {
            this.line = line;
            this.text = text;
        }

        public int getLine() {
            return line;
        }

        public String getText() {
            return text;
        }
    }
}