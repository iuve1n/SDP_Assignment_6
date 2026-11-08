package visitor;

public interface Element {
    String getName();

    DirectoryNode getParent();

    void accept(Visitor visitor);
}
