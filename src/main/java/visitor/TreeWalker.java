package visitor;

import java.util.Objects;

public final class TreeWalker {
    private TreeWalker() {
    }

    public static void walk(Element root, Visitor visitor) {
        Objects.requireNonNull(root, "root cannot be null");
        Objects.requireNonNull(visitor, "visitor cannot be null");

        visitor.begin(root);
        root.accept(new TraversalVisitor(visitor));
    }

    private static class TraversalVisitor implements Visitor {
        private final Visitor visitor;

        private TraversalVisitor(Visitor visitor) {
            this.visitor = visitor;
        }

        @Override
        public void visit(FileNode file) {
            visitor.visit(file);
        }

        @Override
        public void visit(DirectoryNode directory) {
            visitor.visit(directory);
            for (Element child : directory.getChildren()) {
                child.accept(this);
            }
        }
    }
}
