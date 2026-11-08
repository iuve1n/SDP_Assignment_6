package visitor;

public class TotalSizeVisitor implements Visitor {
    private long totalBytes;

    public long calculate(Element root) {
        TreeWalker.walk(root, this);
        return totalBytes;
    }

    @Override
    public void begin(Element root) {
        totalBytes = 0;
    }

    @Override
    public void visit(FileNode file) {
        totalBytes = Math.addExact(totalBytes, file.getSizeBytes());
    }

    @Override
    public void visit(DirectoryNode directory) {
    }
}
