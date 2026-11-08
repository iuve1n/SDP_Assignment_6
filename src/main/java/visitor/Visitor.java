package visitor;

public interface Visitor {
    default void begin(Element root) {
    }

    void visit(FileNode file);

    void visit(DirectoryNode directory);
}
