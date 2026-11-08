package visitor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchVisitorTest {
    @Test
    void searchCombinesFilenameAndInclusiveMinimumSize() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode reports = new DirectoryNode("reports");
        root.addChild(reports);
        reports.addChild(new FileNode("annual.txt", 100));
        reports.addChild(new FileNode("draft.txt", 99));
        reports.addChild(new FileNode("image.bin", 100));
        SearchVisitor visitor = new SearchVisitor(name -> name.endsWith(".txt"), 100);

        assertEquals(List.of("reports/annual.txt"), visitor.search(root));
    }

    @Test
    void resultsUseSortedPathsRelativeToTheChosenRoot() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode nested = new DirectoryNode("folder");
        root.addChild(new FileNode("z.txt", 1));
        root.addChild(nested);
        nested.addChild(new FileNode("b.txt", 1));
        root.addChild(new FileNode("a.txt", 1));
        SearchVisitor visitor = new SearchVisitor(name -> name.endsWith(".txt"), 0);

        assertEquals(List.of("a.txt", "folder/b.txt", "z.txt"), visitor.search(root));
    }

    @Test
    void emptyDirectoryAndNoMatchReturnAnEmptyList() {
        SearchVisitor visitor = new SearchVisitor(name -> name.endsWith(".pdf"), 0);

        assertEquals(List.of(), visitor.search(new DirectoryNode("empty")));

        DirectoryNode root = new DirectoryNode("root");
        root.addChild(new FileNode("notes.txt", 10));
        assertEquals(List.of(), visitor.search(root));
    }

    @Test
    void reusingVisitorClearsResultsFromThePreviousSearch() {
        SearchVisitor visitor = new SearchVisitor(name -> name.endsWith(".txt"), 0);
        DirectoryNode first = new DirectoryNode("first");
        first.addChild(new FileNode("one.txt", 1));
        DirectoryNode second = new DirectoryNode("second");
        second.addChild(new FileNode("two.txt", 2));

        assertEquals(List.of("one.txt"), visitor.search(first));
        assertEquals(List.of("two.txt"), visitor.search(second));
    }

    @Test
    void searchCanUseAFileAsItsRoot() {
        FileNode file = new FileNode("notes.txt", 5);
        SearchVisitor visitor = new SearchVisitor(name -> name.endsWith(".txt"), 5);

        assertEquals(List.of("notes.txt"), visitor.search(file));
    }

    @Test
    void negativeMinimumSizeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new SearchVisitor(name -> true, -1));
    }
}
