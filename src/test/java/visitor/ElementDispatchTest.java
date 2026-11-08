package visitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementDispatchTest {
    @Test
    void fileDispatchesToFileVisitMethod() {
        RecordingVisitor visitor = new RecordingVisitor();

        new FileNode("notes.txt", 10).accept(visitor);

        assertEquals("file:notes.txt", visitor.lastVisit);
    }

    @Test
    void directoryDispatchesToDirectoryVisitMethod() {
        RecordingVisitor visitor = new RecordingVisitor();

        new DirectoryNode("docs").accept(visitor);

        assertEquals("directory:docs", visitor.lastVisit);
    }

    private static class RecordingVisitor implements Visitor {
        private String lastVisit;

        @Override
        public void visit(FileNode file) {
            lastVisit = "file:" + file.getName();
        }

        @Override
        public void visit(DirectoryNode directory) {
            lastVisit = "directory:" + directory.getName();
        }
    }
}
