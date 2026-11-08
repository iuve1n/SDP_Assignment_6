package visitor;

public class Client {
    public static void main(String[] args) {
        DirectoryNode root = createFileSystem();

        long totalSize = new TotalSizeVisitor().calculate(root);
        SearchVisitor searchVisitor = new SearchVisitor(name -> name.endsWith(".java"), 1000);
        StatisticsVisitor.Statistics statistics = new StatisticsVisitor().calculate(root);

        System.out.println("Total size: " + totalSize + " bytes");
        System.out.println("Java files with at least 1000 bytes:");
        for (String path : searchVisitor.search(root)) {
            System.out.println("- " + path);
        }
        System.out.println("Files: " + statistics.fileCount());
        System.out.println("Directories: " + statistics.directoryCount());
        System.out.println("Maximum depth: " + statistics.maxDepth());
    }

    private static DirectoryNode createFileSystem() {
        DirectoryNode root = new DirectoryNode("project");
        DirectoryNode documents = new DirectoryNode("docs");
        DirectoryNode source = new DirectoryNode("src");

        root.addChild(documents);
        root.addChild(source);
        root.addChild(new FileNode("todo.md", 200));
        documents.addChild(new FileNode("design.pdf", 2048));
        documents.addChild(new FileNode("notes.txt", 400));
        source.addChild(new FileNode("Main.java", 1200));
        source.addChild(new FileNode("Utils.java", 800));

        return root;
    }
}
