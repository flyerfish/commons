package net.dongliu.commons;


import net.dongliu.commons.annotation.Nullable;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Utils for print. The static method are delegated to {@link Printer}
 */
public class Prints {

    private static final Printer defaultPrinter = new Printer(" ", System.lineSeparator(), System.out);

    /**
     * @see Printer#print(Object)
     */
    public static void print(Object value) {
        defaultPrinter.print(value);
    }

    /**
     * @see Printer#print(Object...)
     */
    public static void print(@Nullable Object... values) {
        defaultPrinter.print(values);
    }

    /**
     * @see Printer#printValues(Iterable)
     */
    public void printValues(Iterable<@Nullable ?> iterable) {
        defaultPrinter.printValues(iterable);
    }

    /**
     * @see Printer#printMap(Map)
     */
    public void printMap(Map<@Nullable ?, @Nullable ?> map) {
        defaultPrinter.printMap(map);
    }

    /**
     * @see Printer#sep(String)
     */
    public static Printer sep(String sep) {
        requireNonNull(sep);
        return defaultPrinter.sep(sep);
    }

    /**
     * @see Printer#end(String)
     */
    public static Printer end(String end) {
        requireNonNull(end);
        return defaultPrinter.end(end);
    }

    /**
     * @see Printer#out(Writer)
     */
    public static Printer out(Writer writer) {
        requireNonNull(writer);
        return defaultPrinter.out(writer);
    }

    /**
     * @see Printer#out(PrintStream)
     */
    public static Printer out(PrintStream printStream) {
        requireNonNull(printStream);
        return defaultPrinter.out(printStream);
    }

    /**
     * Immutable class with setting for printing.
     */
    public static class Printer {
        private final String sep;
        private final String end;
        private final PrintStream printStream;
        private final Writer writer;

        private Printer(String sep, String end, PrintStream printStream, Writer writer) {
            this.sep = sep;
            this.end = end;
            this.printStream = printStream;
            this.writer = writer;
        }

        private Printer(String sep, String end, PrintStream printStream) {
            this(sep, end, printStream, null);
        }

        private Printer(String sep, String end, Writer writer) {
            this(sep, end, null, writer);
        }

        /**
         * Print one value
         */
        public void print(@Nullable Object value) {
            synchronized (lock()) {
                write(String.valueOf(value));
                write(end);
            }
        }

        /**
         * Print values
         *
         * @param values the values
         */
        public void print(@Nullable Object... values) {
            requireNonNull(values);
            if (values.length == 0) {
                return;
            }

            synchronized (lock()) {
                for (int i = 0; i < values.length; i++) {
                    write(String.valueOf(values[i]));
                    if (i < values.length - 1) {
                        write(sep);
                    }
                }
                write(end);
            }
        }

        /**
         * Print iterable values, with sep and end.
         *
         * @param iterable cannot be null
         */
        public void printValues(Iterable<@Nullable ?> iterable) {
            synchronized (lock()) {
                Iterator<?> iterator = iterable.iterator();
                boolean first = true;
                while (iterator.hasNext()) {
                    if (!first) {
                        write(sep);
                    }
                    Object value = iterator.next();
                    write(String.valueOf(value));
                    first = false;
                }
                write(end);
            }
        }

        /**
         * Print a map, with sep between entries, and end with a end string.
         *
         * @param map cannot be null
         */
        public void printMap(Map<@Nullable ?, @Nullable ?> map) {
            synchronized (lock()) {
                int i = 0;
                int size = map.size();
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    write(String.valueOf(entry.getKey()));
                    write(" = ");
                    write(String.valueOf(entry.getValue()));
                    if (i++ < size - 1) {
                        write(sep);
                    }
                }
                write(end);
            }
        }

        /**
         * Set separator for print multi values. Default is space " ".
         *
         * @param sep the separator
         */
        public Printer sep(String sep) {
            requireNonNull(sep);
            return new Printer(sep, end, printStream, writer);
        }

        /**
         * Additional print string after one print call finished. Default is system line end.
         *
         * @param end the end string
         */
        public Printer end(String end) {
            requireNonNull(end);
            return new Printer(sep, end, printStream, writer);
        }

        /**
         * The writer to print to, instead of default System.out.
         *
         * @param writer the writer
         */
        public Printer out(Writer writer) {
            requireNonNull(writer);
            return new Printer(sep, end, null, writer);
        }

        /**
         * The printStream to print to, instead of default System.out.
         *
         * @param printStream the printStream
         */
        public Printer out(PrintStream printStream) {
            requireNonNull(printStream);
            return new Printer(sep, end, printStream, null);
        }

        private void write(String str) {
            if (str.isEmpty()) {
                return;
            }
            if (printStream != null) {
                printStream.print(str);
            } else if (writer != null) {
                try {
                    writer.write(str);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            } else {
                throw new RuntimeException();
            }
        }

        private Object lock() {
            if (printStream != null) {
                return printStream;
            }
            return writer;
        }

    }
}
