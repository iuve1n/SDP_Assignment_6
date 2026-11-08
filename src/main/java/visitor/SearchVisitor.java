package visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Predicate;

public class SearchVisitor implements Visitor {
    private final Predicate<String> filenameCriterion;
    private final long minimumSizeBytes;
    private final TreeSet<String> matchingPaths = new TreeSet<>();
    private Element root;

    public SearchVisitor(Predicate<String> filenameCriterion, long minimumSizeBytes) {
        this.filenameCriterion = Objects.requireNonNull(filenameCriterion, "filename criterion cannot be null");
        if (minimumSizeBytes < 0) {
            throw new IllegalArgumentException("Minimum size cannot be negative");
        }
        this.minimumSizeBytes = minimumSizeBytes;
    }

    public List<String> search(Element root) {
        TreeWalker.walk(root, this);
        return getMatchingPaths();
    }

    public List<String> getMatchingPaths() {
        return List.copyOf(matchingPaths);
    }

    @Override
    public void begin(Element root) {
        this.root = root;
        matchingPaths.clear();
    }

    @Override
    public void visit(FileNode file) {
        if (filenameCriterion.test(file.getName()) && file.getSizeBytes() >= minimumSizeBytes) {
            matchingPaths.add(relativePath(file));
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
    }

    private String relativePath(FileNode file) {
        if (file == root) {
            return file.getName();
        }

        List<String> segments = new ArrayList<>();
        Element current = file;
        while (current != null && current != root) {
            segments.add(current.getName());
            current = current.getParent();
        }

        if (current == null) {
            throw new IllegalStateException("File is outside the searched root");
        }

        Collections.reverse(segments);
        return String.join("/", segments);
    }
}
