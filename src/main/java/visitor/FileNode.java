package visitor;

import java.util.Objects;

public class FileNode extends FileSystemNode {
    private final long sizeBytes;

    public FileNode(String name, long sizeBytes) {
        super(name);
        if (sizeBytes < 0) {
            throw new IllegalArgumentException("File size cannot be negative");
        }
        this.sizeBytes = sizeBytes;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    @Override
    public void accept(Visitor visitor) {
        Objects.requireNonNull(visitor, "visitor cannot be null").visit(this);
    }
}
