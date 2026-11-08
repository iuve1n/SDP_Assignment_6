package visitor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreeIntegrityTest {
    @Test
    void fileStoresItsNameAndNonNegativeSize() {
        FileNode file = new FileNode("notes.txt", 42);

        assertEquals("notes.txt", file.getName());
        assertEquals(42, file.getSizeBytes());
    }

    @Test
    void fileRejectsNegativeSize() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("notes.txt", -1));
    }

    @Test
    void nodeRejectsNamesThatCannotBeAPathSegment() {
        assertThrows(IllegalArgumentException.class, () -> new DirectoryNode("docs/archive"));
        assertThrows(IllegalArgumentException.class, () -> new FileNode("..", 0));
    }

    @Test
    void directoryRejectsDuplicateSiblingNames() {
        DirectoryNode root = new DirectoryNode("root");
        root.addChild(new FileNode("data.txt", 1));

        assertThrows(IllegalArgumentException.class, () -> root.addChild(new DirectoryNode("data.txt")));
    }

    @Test
    void elementCannotBelongToTwoDirectories() {
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        FileNode file = new FileNode("data.txt", 1);
        first.addChild(file);

        assertThrows(IllegalArgumentException.class, () -> second.addChild(file));
    }

    @Test
    void directoryCannotBeAddedToItselfOrAnAncestor() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode nested = new DirectoryNode("nested");
        root.addChild(nested);

        assertThrows(IllegalArgumentException.class, () -> root.addChild(root));
        assertThrows(IllegalArgumentException.class, () -> nested.addChild(root));
    }

    @Test
    void validNestingAndEmptyDirectoriesAreSupported() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode nested = new DirectoryNode("nested");
        DirectoryNode empty = new DirectoryNode("empty");
        root.addChild(nested);
        root.addChild(empty);
        nested.addChild(new FileNode("data.txt", 1));

        assertEquals(2, root.getChildren().size());
        assertEquals(1, nested.getChildren().size());
        assertEquals(List.of(), empty.getChildren());
        assertEquals(root, nested.getParent());
    }

    @Test
    void childListCannotModifyDirectoryContents() {
        DirectoryNode root = new DirectoryNode("root");
        root.addChild(new FileNode("data.txt", 1));

        assertThrows(UnsupportedOperationException.class, () -> root.getChildren().clear());
        assertEquals(1, root.getChildren().size());
    }
}
