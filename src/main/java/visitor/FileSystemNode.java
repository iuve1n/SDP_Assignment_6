package visitor;

abstract class FileSystemNode implements Element {
    private final String name;
    private DirectoryNode parent;

    FileSystemNode(String name) {
        if (name == null || name.isBlank() || name.equals(".") || name.equals("..")
                || name.contains("/") || name.contains("\\")) {
            throw new IllegalArgumentException("Name must be a non-empty path segment");
        }
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final DirectoryNode getParent() {
        return parent;
    }

    final void setParent(DirectoryNode parent) {
        this.parent = parent;
    }
}
