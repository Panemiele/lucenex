package lucenex;

import it.uniroma3.ingdati.service.IndexService;
import it.uniroma3.ingdati.service.SearchService;
import it.uniroma3.ingdati.service.enums.FieldName;
import it.uniroma3.ingdati.service.impl.IndexServiceImpl;
import it.uniroma3.ingdati.service.impl.SearchServiceImpl;
import it.uniroma3.ingdati.service.vo.DocumentVO;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Homework2Test {

    private static final String INDEX_DIRECTORY = "./src/test/resources/index";
    private static final String FIRST_TEST_FILE = "First_test_file";
    private static final String SECOND_TEST_FILE = "Second_test_file";
    private static final String BIG_FILE = "Parole_uniche_italiane";
    private static final String LOWERCASE_FILE_QUERY = "file";
    private static final String CAMELCASE_FILE_QUERY = "File";
    private static final String LOWERCASE_SECONDO_QUERY = "secondo";
    private static final String LOWERCASE_NOVECENTOMILA_QUERY = "novecentomila";
    private static final List<String> FIRST_TEST_FILE_TITLE = Arrays.asList("contenente", "frasi");
    private static final List<String> FIRST_TEST_FILE_CONTENT = Arrays.asList("file", "contiene", "frasi", "tutte", "identiche");
    private static final List<String> FIRST_TEST_FILE_CONTENT_WITH_STOPWORDS = Arrays.asList("questo", "file", "contiene", "frasi", "tutte", "identiche", "tra", "loro");
    private static final List<String> BIG_FILE_TITLE = Arrays.asList("circa", "novecentomila", "parole", "italiane");
    private static final List<String> BIG_FILE_CONTENT = Arrays.asList("aba", "abaco");

    private final IndexService indexService = new IndexServiceImpl(INDEX_DIRECTORY);
    private final SearchService searchService = new SearchServiceImpl(INDEX_DIRECTORY);

    @Test
    public void shouldCreateCustomIndexForASingleDocument() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldCreateCustomIndexForASingleDocument - time: " + (endTimeMillis - startTimeMillis));
    }

    @Test
    public void shouldCreateCustomIndexForAMultipleDocuments() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE, SECOND_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldCreateCustomIndexForAMultipleDocuments - time: " + (endTimeMillis - startTimeMillis));
    }

    @Test
    public void shouldCreateCustomIndexBigFile() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{BIG_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldCreateCustomIndexBigFile - time: " + (endTimeMillis - startTimeMillis));
    }

    @Test
    public void shouldExecuteATermQueryForTitleOnSingleDocumentIndex() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForTitleOnSingleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(CAMELCASE_FILE_QUERY, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForTitleOnSingleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldExecuteATermQueryForContentOnSingleDocumentIndex() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForContentOnSingleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_FILE_QUERY, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForContentOnSingleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldExecuteAPhraseQueryForTitleOnSingleDocumentIndex() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAPhraseQueryForTitleOnSingleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchForPhraseInIndex(FIRST_TEST_FILE_TITLE, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAPhraseQueryForTitleOnSingleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldExecuteAPhraseQueryForContentOnSingleDocumentIndex() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAPhraseQueryForContentOnSingleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchForPhraseInIndex(FIRST_TEST_FILE_CONTENT, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAPhraseQueryForContentOnSingleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldFailExecutingAPhraseQueryForContentWithStopwords() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingAPhraseQueryForContentWithStopwords - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchForPhraseInIndex(FIRST_TEST_FILE_CONTENT_WITH_STOPWORDS, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingAPhraseQueryForContentWithStopwords - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(0, documentVOList.size());
    }

    @Test
    public void shouldFailExecutingATermQueryForTitleWithLowercaseQuery() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingATermQueryForTitleWithLowercaseQuery - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_FILE_QUERY, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingATermQueryForTitleWithLowercaseQuery - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(0, documentVOList.size());
    }

    @Test
    public void shouldFailExecutingATermQueryForContentWithLowercaseQuery() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingATermQueryForContentWithLowercaseQuery - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(CAMELCASE_FILE_QUERY, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingATermQueryForContentWithLowercaseQuery - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(0, documentVOList.size());
    }

    @Test
    public void shouldExecuteATermQueryForTitleOnMultipleDocumentIndex() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE, SECOND_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForTitleOnMultipleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(CAMELCASE_FILE_QUERY, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForTitleOnMultipleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(2, documentVOList.size());
    }

    @Test
    public void shouldExecuteATermQueryForContentOnMultipleDocumentIndex() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE, SECOND_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForContentOnMultipleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_FILE_QUERY, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForContentOnMultipleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(2, documentVOList.size());
    }

    @Test
    public void shouldExecuteATermQueryForTitleOnMultipleDocumentIndexFindOneDocument() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE, SECOND_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForTitleOnMultipleDocumentIndexFindOneDocument - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_SECONDO_QUERY, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForTitleOnMultipleDocumentIndexFindOneDocument - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldExecuteATermQueryForContentOnMultipleDocumentIndexFindOneDocument() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE, SECOND_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForContentOnMultipleDocumentIndexFindOneDocument - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_SECONDO_QUERY, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForContentOnMultipleDocumentIndexFindOneDocument - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldFailExecutingATermQueryForTitleWithLowercaseQueryInMultipleDocumentIndex() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE, SECOND_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingATermQueryForTitleWithLowercaseQueryInMultipleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_NOVECENTOMILA_QUERY, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingATermQueryForTitleWithLowercaseQueryInMultipleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(0, documentVOList.size());
    }

    @Test
    public void shouldFailExecutingATermQueryForContentWithLowercaseQueryInMultipleDocumentIndex() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{FIRST_TEST_FILE, SECOND_TEST_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingATermQueryForContentWithLowercaseQueryInMultipleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_SECONDO_QUERY, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldFailExecutingATermQueryForContentWithLowercaseQueryInMultipleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test

    public void shouldExecuteATermQueryForTitleOnBigFile() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{BIG_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForTitleOnBigFile - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_NOVECENTOMILA_QUERY, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForTitleOnBigFile - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldExecuteATermQueryForContentOnBigFile() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{BIG_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForContentOnBigFile - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_NOVECENTOMILA_QUERY, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteATermQueryForContentOnBigFile - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }


    @Test
    public void shouldExecuteAPhraseQueryForTitleOnBigFile() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{BIG_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAPhraseQueryForTitleOnBigFile - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchForPhraseInIndex(BIG_FILE_TITLE, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAPhraseQueryForTitleOnBigFile - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }


    @Test
    public void shouldExecuteAPhraseQueryForContentOnBigFile() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{BIG_FILE};
        indexService.createIndex(fileNames, IndexWriterConfig.OpenMode.CREATE);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAPhraseQueryForContentOnBigFile - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchForPhraseInIndex(BIG_FILE_CONTENT, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAPhraseQueryForContentOnBigFile - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }
}
