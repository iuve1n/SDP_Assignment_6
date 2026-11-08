package visitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalSizeVisitorTest {
    @Test
    void sumsFilesAcrossSeveralDirectoryLevels() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode documents = new DirectoryNode("documents");
        DirectoryNode reports = new DirectoryNode("reports");
        root.addChild(new FileNode("readme.txt", 12));
        root.addChild(documents);
        documents.addChild(reports);
        documents.addChild(new FileNode("notes.txt", 8));
        reports.addChild(new FileNode("summary.pdf", 20));

        assertEquals(40, new TotalSizeVisitor().calculate(root));
    }

    @Test
    void emptyDirectoryHasZeroTotalSize() {
        assertEquals(0, new TotalSizeVisitor().calculate(new DirectoryNode("empty")));
    }

    @Test
    void reusingVisitorStartsEachCalculationFromZero() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        DirectoryNode first = new DirectoryNode("first");
        first.addChild(new FileNode("one.txt", 30));
        DirectoryNode second = new DirectoryNode("second");
        second.addChild(new FileNode("two.txt", 7));

        assertEquals(30, visitor.calculate(first));
        assertEquals(7, visitor.calculate(second));
    }
}
