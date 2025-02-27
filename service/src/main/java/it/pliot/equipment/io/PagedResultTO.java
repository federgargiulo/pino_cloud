package it.pliot.equipment.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagedResultTO<IO> implements Serializable {

    private List<IO> results = new ArrayList<IO>();;

    private String nextPage  = null;

    public String getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(String prevPage) {
        this.prevPage = prevPage;
    }

    private String prevPage  = null;


    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public List<IO> getResults() {
        return results;
    }

    public void setResults(List<IO> results) {
        this.results = results;
    }

    public void addItem( IO e ){
        results.add( e );
    }
}
