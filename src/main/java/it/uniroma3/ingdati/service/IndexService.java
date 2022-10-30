package it.uniroma3.ingdati.service;

import org.apache.lucene.index.IndexWriterConfig;

import java.io.IOException;

public interface IndexService {
    void createIndex() throws IOException;

    void createIndex(String[] fileNames, IndexWriterConfig.OpenMode openMode) throws IOException;

    void deleteIndexes() throws IOException;
}
