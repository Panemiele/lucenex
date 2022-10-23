package lucenex;

import it.uniroma3.ingdati.service.IndexService;
import it.uniroma3.ingdati.service.SearchService;
import it.uniroma3.ingdati.service.enums.FieldName;
import it.uniroma3.ingdati.service.impl.IndexServiceImpl;
import it.uniroma3.ingdati.service.impl.SearchServiceImpl;
import it.uniroma3.ingdati.service.vo.DocumentVO;
import org.apache.lucene.store.FSDirectory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Homework2Test {

    private static final String INDEX_DIRECTORY = "./src/test/resources/index";
    private static final String TEST_FILE = "Test_file";
    private static final String COME_DIVENTARE_INGEGNERE_DEI_DATI_FILE = "Come_diventare_un_ingegnere_dei_dati";
    private static final String CURRICULUM_INGEGNERIA_DEI_DATI_FILE = "Curriculum_Ingegneria_dei_Dati";
    private static final String LOWERCASE_FILE_QUERY = "file";
    private static final String CAMELCASE_FILE_QUERY = "File";

    private final IndexService indexService = new IndexServiceImpl(INDEX_DIRECTORY);
    private final SearchService searchService = new SearchServiceImpl(INDEX_DIRECTORY);

    @BeforeEach
    public void clearIndexes() throws IOException {
        File directory = new File(INDEX_DIRECTORY);
        FileUtils.deleteDirectory(directory);
        directory.delete();
    }

    @Test
    public void shouldCreateCustomIndexForASingleDocument() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{TEST_FILE};
        indexService.createIndex(fileNames);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldCreateCustomIndexForASingleDocument - time: " + (endTimeMillis - startTimeMillis));
    }

    @Test
    public void shouldCreateCustomIndexForAMultipleDocuments() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{TEST_FILE, COME_DIVENTARE_INGEGNERE_DEI_DATI_FILE, CURRICULUM_INGEGNERIA_DEI_DATI_FILE};
        indexService.createIndex(fileNames);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldCreateCustomIndexForAMultipleDocuments - time: " + (endTimeMillis - startTimeMillis));
    }

    @Test
    public void shouldExecuteAQueryForTitleOnSingleDocumentIndex() throws Exception {
        Long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{TEST_FILE};
        indexService.createIndex(fileNames);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        Long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAQueryForTitleOnSingleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(CAMELCASE_FILE_QUERY, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAQueryForTitleOnSingleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldExecuteAQueryForContentOnSingleDocumentIndex() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{TEST_FILE};
        indexService.createIndex(fileNames);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAQueryForContentOnSingleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_FILE_QUERY, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAQueryForContentOnSingleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldFailExecutingAQueryForTitleWithLowercaseQuery() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{TEST_FILE};
        indexService.createIndex(fileNames);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAQueryForTitleOnSingleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(LOWERCASE_FILE_QUERY, FieldName.TITLE);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAQueryForTitleOnSingleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }

    @Test
    public void shouldFailExecutingAQueryForContentWithLowercaseQuery() throws Exception {
        long startTimeMillis = System.currentTimeMillis();
        String[] fileNames = new String[]{TEST_FILE};
        indexService.createIndex(fileNames);
        Assert.assertNotNull(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAQueryForTitleOnSingleDocumentIndex - index creation time: " + (endTimeMillis - startTimeMillis));

        startTimeMillis = System.currentTimeMillis();
        List<DocumentVO> documentVOList = searchService.searchInIndex(CAMELCASE_FILE_QUERY, FieldName.CONTENT);
        endTimeMillis = System.currentTimeMillis();
        System.out.println("shouldExecuteAQueryForTitleOnSingleDocumentIndex - query execution time: " + (endTimeMillis - startTimeMillis));

        Assert.assertNotNull(documentVOList);
        Assert.assertEquals(1, documentVOList.size());
    }
}
