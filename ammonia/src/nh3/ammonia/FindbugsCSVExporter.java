package nh3.ammonia;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FindbugsCSVExporter {

    public static void main(String[] args) throws IOException {
        final FBParser parser = new FBParser(args[0]);
        parser.perform();
        final String projectName = args[1];
        final String version = args[2];

        try (final BufferedWriter writer = new BufferedWriter(new PrintWriter(System.out))) {

            writer.write("project,version,tool,filename,linebeg,lineend,type,index");
            writer.newLine();

            for (int i = 0; i < parser.getBugInstances().size(); i++) {
                final BugInstance bug = parser.getBugInstances().get(i);

                for (final SourceLine line : bug.getSourceLines()) {

                    final List<String> items = new ArrayList<>();
                    items.add(projectName);
                    items.add(version);
                    items.add("Findbugs");
                    items.add(line.sourcepath);
                    items.add(String.valueOf(line.start));
                    items.add(String.valueOf(line.end));
                    items.add(bug.pattern.type);
                    items.add(String.valueOf(i));

                    writer.write(String.join(",", items.toArray(new String[items.size()])));
                    writer.newLine();
                }
            }
        }
    }
}
