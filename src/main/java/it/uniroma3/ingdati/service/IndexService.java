package it.uniroma3.ingdati.service;

import java.io.IOException;

public interface IndexService {
    void createIndex() throws IOException;

    void createIndex(String[] fileNames) throws IOException;

    void deleteIndexes() throws IOException;
}
