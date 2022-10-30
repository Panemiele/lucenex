package it.uniroma3.ingdati.service.impl;

import it.uniroma3.ingdati.service.SearchService;
import it.uniroma3.ingdati.service.enums.FieldName;
import it.uniroma3.ingdati.service.vo.DocumentVO;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Value("${ingdati.lucene.index.directory}")
    private String indexDirectory;

    public SearchServiceImpl(){}

    public SearchServiceImpl(String indexDirectory){
        this.indexDirectory = indexDirectory;
    }

    @Override public List<DocumentVO> searchInIndex(String queryString, FieldName fieldName) throws Exception {
        List<DocumentVO> documentVOList;
        Query query = new TermQuery(new Term(fieldName.name(), queryString));
        try (Directory directory = FSDirectory.open(Paths.get(indexDirectory))) {
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            documentVOList = runQuery(searcher, query);
        }
        return documentVOList;
    }

    @Override public List<DocumentVO> searchForPhraseInIndex(List<String> queryTerms, FieldName fieldName) throws Exception {
        List<DocumentVO> documentVOList;
        Query query = new PhraseQuery(fieldName.name(), queryTerms.toArray(new String[0]));
        try (Directory directory = FSDirectory.open(Paths.get(indexDirectory))) {
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);
            documentVOList = runQuery(searcher, query);
        }
        return documentVOList;
    }

    private List<DocumentVO> runQuery(IndexSearcher searcher, Query query) throws IOException {
        TopDocs hits = searcher.search(query, 10);
        List<DocumentVO> documentVOList = new ArrayList<>();
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc scoreDoc = hits.scoreDocs[i];
            Document doc = searcher.doc(scoreDoc.doc);

            DocumentVO documentVo = new DocumentVO();
            documentVo.setId(scoreDoc.doc);
            documentVo.setTitle(doc.get("TITLE"));
            documentVo.setRankWeight(scoreDoc.score);
            documentVOList.add(documentVo);
        }
        return documentVOList;
    }
}
