package com.example.demo.feedback_analyzer.lucene;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * The Lucene Indexing Class
 * <p>
 * Used for indexing the message
 */
public class LuceneIndexing {

    public static void main(String[] args) {
        // Path to the index directory
        String indexPath = "../index";
        try {
            // Set up Lucene indexer
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(dir, config);

            String[] message = { "Subject: Unacceptable Service\n" + "\n" + "Dear [Recipient],\n" + "\n"
                    + "I am thoroughly displeased with the service provided by your company "
                    + "The level of incompetence and disregard for customer satisfaction is unacceptable "
                    + "I demand a full refund and prompt action to rectify this situation "
                    + "Failure to do so will result in further escalation of this matter" };

            // Indexing documents
            for (String text : message) {
                Document doc = new Document();
                doc.add(new TextField("message", text, Field.Store.YES));
                writer.addDocument(doc);
            }
            writer.close();

            // Using QueryParser for searching
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            // Create a QueryParser
            QueryParser parser = new QueryParser("message", analyzer);
            Query query = parser.parse("displeased");

            TopDocs results = searcher.search(query, 10);

            // ... Process search results ...
            ScoreDoc[] analysedMessage = results.scoreDocs;
            for (int i = 0; i < analysedMessage.length; ++i) {
                Document doc = searcher.doc(analysedMessage[i].doc);
                System.out.println((i + 1) + ". " + doc.get("message"));
            }

            reader.close();
            dir.close();

        } catch (Exception e) {
            System.err.println("Exception caught: " + e.getMessage());
        }
    }
}