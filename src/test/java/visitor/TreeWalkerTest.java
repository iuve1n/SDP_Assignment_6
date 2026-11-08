package visitor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeWalkerTest {
    @Test
    void walkerVisitsEachReachableElementOnceInTreeOrder() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode nested = new DirectoryNode("nested");
        root.addChild(new FileNode("first.txt", 1));
        root.addChild(nested);
        nested.addChild(new FileNode("second.txt", 2));
        RecordingVisitor visitor = new RecordingVisitor();

        TreeWalker.walk(root, visitor);

        assertEquals(List.of("directory:root", "file:first.txt", "directory:nested", "file:second.txt"),
                visitor.visited);
        assertEquals(4, visitor.visited.size());
    }

    private static class RecordingVisitor implements Visitor {
        private final List<String> visited = new ArrayList<>();

        @Override
        public void visit(FileNode file) {
            visited.add("file:" + file.getName());
        }

        @Override
        public void visit(DirectoryNode directory) {
            visited.add("directory:" + directory.getName());
        }
    }
}
