package com.atlantico.srt.model;

import java.nio.file.Path;

public class FileDB {
	
	private String filename;
	private Path root;
    private String url;
    private Long size;
    
    // Constructor
    public FileDB() {}

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

	public Path getRoot() {
		return root;
	}

	public void setRoot(Path root) {
		this.root = root;
	}

}

