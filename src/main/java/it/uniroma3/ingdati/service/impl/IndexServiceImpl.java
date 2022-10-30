package it.uniroma3.ingdati.service.impl;

import it.uniroma3.ingdati.service.IndexService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class IndexServiceImpl implements IndexService {

    @Value("${ingdati.lucene.index.directory}")
    private String indexDirectory;
    private static final String DOCUMENTS_PATH = "./src/main/resources/documents/";
    private static final String STOPWORDS_FILE = "stopwords.txt";

    public IndexServiceImpl(){}

    public IndexServiceImpl(String indexDirectory){
        this.indexDirectory = indexDirectory;
    }

    @Override
    public void createIndex() throws IOException {
        Directory directory = FSDirectory.open(Paths.get(indexDirectory));
        IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig());
        Document doc1 = new Document();
        doc1.add(new TextField("TITLE", "Come diventare un ingegnere dei dati, Data Engineer?", Field.Store.YES));
        doc1.add(new TextField("CONTENT", "Sembra che oggigiorno tutti vogliano diventare un Data Scientist  ...", Field.Store.YES));
        Document doc2 = new Document();
        doc2.add(new TextField("TITLE", "Curriculum Ingegneria dei Dati - Sezione di Informatica e Automazione", Field.Store.YES));
        doc2.add(new TextField("CONTENT", "Curriculum. Ingegneria dei Dati. Laurea Magistrale in Ingegneria Informatica ...", Field.Store.YES));
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.commit();
        writer.close();
        directory.close();
    }

    @Override
    public void createIndex(String[] fileNames, IndexWriterConfig.OpenMode openMode) throws IOException {
        Directory directory = FSDirectory.open(Paths.get(indexDirectory));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(createCustomPerFieldAnalyzerWrapper());
        indexWriterConfig.setCodec(new SimpleTextCodec());
        indexWriterConfig.setOpenMode(openMode);
        IndexWriter writer = new IndexWriter(directory, indexWriterConfig);

        for (String fileName : fileNames) {
            Document document = new Document();
            System.out.println("\nFile: " + fileName + ".txt");
            File file = new File(DOCUMENTS_PATH + fileName + ".txt");
            Scanner fileScanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            if (fileScanner.hasNextLine()) {
                String nextLine = fileScanner.nextLine();
                System.out.println("title - " + nextLine);
                sb.append(nextLine);
            }
            String title = sb.toString();
            sb.delete(0, sb.toString().length());
            while (fileScanner.hasNextLine()) {
                String nextLine = fileScanner.nextLine();
                System.out.println("content - " + nextLine);
                sb.append(nextLine + System.lineSeparator());
            }
            String content = sb.toString();
            document.add(new TextField("TITLE", title, Field.Store.YES));
            document.add(new TextField("CONTENT", content, Field.Store.YES));
            writer.addDocument(document);
        }
        writer.commit();
        writer.close();
        directory.close();
    }

    @Override public void deleteIndexes() throws IOException {
        File directory = new File(indexDirectory);
        FileUtils.deleteDirectory(directory);
        directory.delete();
    }

    /*-----------------*
     * PRIVATE METHODS *
     *-----------------*/
    private Analyzer createCustomPerFieldAnalyzerWrapper() throws IOException {
        Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();
        perFieldAnalyzers.put("TITLE", new WhitespaceAnalyzer());
        perFieldAnalyzers.put("CONTENT", createCustomContentAnalizer());
        return new PerFieldAnalyzerWrapper(new ItalianAnalyzer(), perFieldAnalyzers);
    }

    private CustomAnalyzer createCustomContentAnalizer() throws IOException {
        return CustomAnalyzer.builder()
            .withTokenizer(WhitespaceTokenizerFactory.class)
            .addTokenFilter(LowerCaseFilterFactory.class)
            //.addTokenFilter(WordDelimiterGraphFilterFactory.class)
            .addTokenFilter(StopFilterFactory.NAME, "words", STOPWORDS_FILE)
            .build();
    }
}
