package com.riaylibrary.custom_component;

public class FileInfo implements Comparable<FileInfo> {
    private final String name;
    private final String data;
    private final String path;
    private final boolean folder;
    private final boolean parent;

    public FileInfo(String n, String d, String p, boolean folder, boolean parent) {
        this.name = n;
        this.data = d;
        this.path = p;
        this.folder = folder;
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public String getPath() {
        return this.path;
    }

    public int compareTo(FileInfo o) {
        if (this.name != null) {
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isFolder() {
        return this.folder;
    }

    public boolean isParent() {
        return this.parent;
    }
}