/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author atrushin
 */
public class UrlLink {
    public String URL;
    public String Title;    
    public String PageContent;
    
    @Override
    public String toString(){
        return "--------------------\nTitle: " + Title + "\nUrl: " + URL + "\n--------------------\n";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + URL.length();
        result = prime * result + Title.length();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UrlLink other = (UrlLink) obj;
        return Title.equals(other.Title) && URL.equals(other.URL);
    }
    
}
