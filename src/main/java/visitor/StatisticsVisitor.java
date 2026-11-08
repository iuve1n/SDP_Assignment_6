package visitor;

public class StatisticsVisitor implements Visitor {
    private int fileCount;
    private int directoryCount;
    private int maxDepth;
    private Element root;

    public Statistics calculate(Element root) {
        TreeWalker.walk(root, this);
        return new Statistics(fileCount, directoryCount, maxDepth);
    }

    @Override
    public void begin(Element root) {
        this.root = root;
        fileCount = 0;
        directoryCount = 0;
        maxDepth = 0;
    }

    @Override
    public void visit(FileNode file) {
        fileCount++;
        updateMaxDepth(file);
    }

    @Override
    public void visit(DirectoryNode directory) {
        directoryCount++;
        updateMaxDepth(directory);
    }

    private void updateMaxDepth(Element element) {
        int depth = 0;
        Element current = element;

        while (current != root) {
            current = current.getParent();
            if (current == null) {
                throw new IllegalStateException("Element is outside the visited root");
            }
            depth++;
        }

        maxDepth = Math.max(maxDepth, depth);
    }

    public record Statistics(int fileCount, int directoryCount, int maxDepth) {
    }
}
