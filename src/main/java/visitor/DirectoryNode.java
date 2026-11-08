package visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DirectoryNode extends FileSystemNode {
    private final List<FileSystemNode> children = new ArrayList<>();

    public DirectoryNode(String name) {
        super(name);
    }

    public void addChild(FileNode child) {
        addChildNode(child);
    }

    public void addChild(DirectoryNode child) {
        addChildNode(child);
    }

    public List<Element> getChildren() {
        List<Element> copy = new ArrayList<>(children);
        return Collections.unmodifiableList(copy);
    }

    @Override
    public void accept(Visitor visitor) {
        Objects.requireNonNull(visitor, "visitor cannot be null").visit(this);
    }

    private void addChildNode(FileSystemNode child) {
        Objects.requireNonNull(child, "child cannot be null");

        if (child.getParent() != null) {
            throw new IllegalArgumentException("Element already belongs to a directory");
        }

        for (FileSystemNode existingChild : children) {
            if (existingChild.getName().equals(child.getName())) {
                throw new IllegalArgumentException("Sibling names must be unique");
            }
        }

        DirectoryNode ancestor = this;
        while (ancestor != null) {
            if (ancestor == child) {
                throw new IllegalArgumentException("Adding this directory would create a cycle");
            }
            ancestor = ancestor.getParent();
        }

        child.setParent(this);
        children.add(child);
    }
}
