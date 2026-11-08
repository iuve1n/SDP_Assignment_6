package visitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsVisitorTest {
    @Test
    void countsElementsAndFindsDeepestLevel() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode documents = new DirectoryNode("documents");
        DirectoryNode reports = new DirectoryNode("reports");
        root.addChild(documents);
        documents.addChild(reports);
        documents.addChild(new FileNode("notes.txt", 10));
        reports.addChild(new FileNode("summary.pdf", 20));

        StatisticsVisitor.Statistics result = new StatisticsVisitor().calculate(root);

        assertEquals(2, result.fileCount());
        assertEquals(3, result.directoryCount());
        assertEquals(3, result.maxDepth());
    }

    @Test
    void emptyDirectoryCountsAsRootAtDepthZero() {
        StatisticsVisitor.Statistics result = new StatisticsVisitor().calculate(new DirectoryNode("empty"));

        assertEquals(0, result.fileCount());
        assertEquals(1, result.directoryCount());
        assertEquals(0, result.maxDepth());
    }

    @Test
    void fileRootHasDepthZero() {
        StatisticsVisitor.Statistics result = new StatisticsVisitor().calculate(new FileNode("notes.txt", 10));

        assertEquals(1, result.fileCount());
        assertEquals(0, result.directoryCount());
        assertEquals(0, result.maxDepth());
    }

    @Test
    void repeatedCalculationsDoNotKeepPreviousCounts() {
        StatisticsVisitor visitor = new StatisticsVisitor();
        DirectoryNode first = new DirectoryNode("first");
        first.addChild(new FileNode("one.txt", 1));
        DirectoryNode second = new DirectoryNode("second");

        assertEquals(new StatisticsVisitor.Statistics(1, 1, 1), visitor.calculate(first));
        assertEquals(new StatisticsVisitor.Statistics(0, 1, 0), visitor.calculate(second));
    }
}
